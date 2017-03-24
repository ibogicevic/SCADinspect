import time
import sys
import getpass
import hashlib
import os.path
from scrapy import signals
from scrapy.item import Item, Field
from scrapy.spiders import Spider
from scrapy.http import Request, FormRequest
from scrapy.crawler import CrawlerProcess
from scrapy.settings import Settings
from scrapy.pipelines.files import FilesPipeline
from scrapy.utils.python import to_bytes

class TestCorpusItem(Item):
    file_urls = Field()

class TestCorpusSpider(Spider):
    name = "testcorpusspider"
    start_urls = ["https://github.com/login"]
    handle_httpstatus_list = [429] # abuse detection
    num_of_items = 0
    items_scraped = 0

    @classmethod
    def from_crawler(cls, crawler, *args, **kwargs):
        spider = super(TestCorpusSpider, cls).from_crawler(crawler, *args, **kwargs)
        crawler.signals.connect(spider.spider_closed, signals.spider_closed)
        return spider

    def spider_closed(self, spider):
        item_scraped_count = spider.crawler.stats.get_value('item_scraped_count')
        if item_scraped_count is None:
            item_scraped_count = 0
        item_downloaded_count = spider.crawler.stats.get_value('file_status_count/downloaded')
        if item_downloaded_count is None:
            item_downloaded_count = 0
        item_uptodate_count = spider.crawler.stats.get_value('file_status_count/uptodate')
        if item_uptodate_count is None:
            item_uptodate_count = 0
        print("%d files were found. %d files were downloaded, %d files were already existing." % (item_scraped_count, item_downloaded_count, item_uptodate_count))

    def parse(self, response):
        """login to github and call the real parser afterwards"""
        print("Please enter your github login credentials.")
        username = raw_input("GitHub username or email: ")
        password = pswd = getpass.getpass("GitHub password: ")
        yield FormRequest.from_response(
            response,
            url='https://github.com/session',
            method="POST",
            formdata={
                'login': username,
                'password': password
            },
            callback=self.after_login)

    def after_login(self, response):
        """Check if the login was successful and redirect."""
        if "Incorrect username or password." in response.body:
            print("Incorrect username or password. Please try again.")
            return Request(url="https://github.com/login", callback=self.parse, dont_filter=True)
        else:
            # specify number of files to download
            def set_num_of_items():
                print("How many SCAD files do you want to download? (1000 max)")
                num_of_items = raw_input("Enter a number: ")
                try:
                    self.num_of_items = int(num_of_items)
                except ValueError:
                    print("Input not valid. Please try again.")
                    set_num_of_items()
            set_num_of_items()
            print("The files are collected and downloaded. Please wait.")
            return Request(url="https://github.com/search?l=&o=desc&q=function+extension%3Ascad&s=indexed&type=Code",
                callback=self.parse_files, dont_filter=True)

    def parse_files(self, response):
        item = TestCorpusItem()
        file_url_xpath = ".//*[@id='code_search_results']/div[1]/div/p/a[2]/@href"

        if "You have triggered an abuse detection mechanism." in response.body:
            print("The crawler was too fast for GitHub (abuse detection).")
            for i in xrange(60,0,-1):
                time.sleep(1)
                sys.stdout.write('\rPlease wait %2d seconds.' % i)
                sys.stdout.flush()
            yield Request(url=response.url, callback=self.parse_files, dont_filter=True)
        else:
            for file_url in response.xpath(file_url_xpath).extract():
                if self.items_scraped == self.num_of_items:
                    return
                else:
                    raw_file_url = 'https://raw.githubusercontent.com' + file_url.replace("/blob/", "/")
                    item['file_urls'] = [raw_file_url]
                    self.items_scraped += 1
                    yield item

            next_page = response.css('a.next_page::attr("href")').extract_first()
            if next_page is not None:
                next_page = response.urljoin(next_page)
                yield Request(next_page, callback=self.parse_files)

class TestCorpusFilesPipeline(FilesPipeline):
    def file_path(self, request, response=None, info=None):
        url = request.url
        media_guid = hashlib.sha1(to_bytes(url)).hexdigest()
        media_ext = os.path.splitext(url)[1]
        return '%s%s' % (media_guid, media_ext)


def main():
    """Run the web crawler."""

    # configure settings for crawler
    settings = Settings()
    settings.set('LOG_ENABLED', False)
    settings.set('LOG_LEVEL', 'DEBUG')
    settings.set('DEFAULT_REQUEST_HEADERS', {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.104 Safari/537.36'
    })
    settings.set('ITEM_PIPELINES', {'__main__.TestCorpusFilesPipeline': 1})
    settings.set('FILES_STORE', './Files')

    # start the crawler
    process = CrawlerProcess(settings)
    process.crawl(TestCorpusSpider())
    process.start()

if __name__ == '__main__':
    main()
