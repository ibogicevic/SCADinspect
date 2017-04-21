package scadinspect.data.scaddoc.export.format;

import java.io.StringWriter;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scadinspect.data.scaddoc.ScadDocuFile;

/**
 * Created by eric on 31.03.17.
 */
public class HtmlExporter implements Exporter {

  @Override
  public String getOutput(ScadDocuFile file) throws Exception {
    String title = "Parts Documentation";
    Collection<String> keys = file.getAllKeys();

    Document doc = getDoc();

    Element table = getTableNode(doc);
    table.appendChild(getTableHead(doc));

    return null;
  }

  @Override
  public String getOutput(Collection<ScadDocuFile> files) throws Exception {
    return null;
  }

  /**
   * Creates a empty Document that provides the base for the HTML document
   *
   * @return a Document to build the HTML File
   * @throws ParserConfigurationException if the document creation failed
   */
  private Document getDoc() throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    return docBuilder.newDocument();
  }

  /**
   * Creates a formatted HTML string
   *
   * @param doc Document to create the HTML from
   * @return beautified HTML String
   * @throws TransformerException if the transformer is configured wrong, or the document is
   * incorrect
   */
  private String transform(Document doc) throws TransformerException {
    // write the content into xml format
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.METHOD, "html");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    DOMSource source = new DOMSource(doc);
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    transformer.transform(source, result);

    //return XML String + do some **magic**
    return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))", "");
  }

  private Element getTableNode(Document doc) {
    return doc.createElement("table");
  }

  private Element getTableRow(Document doc) {
    return doc.createElement("tr");
  }

  private Element getTableHead(Document doc) {
    return doc.createElement("th");
  }

  private Element getTableBody(Document doc) {
    return doc.createElement("tbody");
  }

  private Element getTableh(Document doc) {
    return doc.createElement("th");
  }

  private Element getTabled(Document doc) {
    return doc.createElement("td");
  }
}
