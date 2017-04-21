package scadinspect.data.scaddoc.export.format;

import java.io.StringWriter;
import java.util.ArrayList;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.Property;

/**
 * @author eric on 31.03.17.
 */

public class HtmlExporter implements Exporter {

  /**
   * Creates and returns an HTML as a byte array from the given ScadDocuFile according to the
   * specified example
   *
   * @param file ScadDocuFile that the Export shall be exported for
   * @return byte array, which represents the created HTML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  @Override
  public byte[] getOutput(ScadDocuFile file)
      throws ParserConfigurationException, TransformerException {
    Document doc = getDoc();

    Element html = doc.createElement("html");
    Element head = doc.createElement("head");

    Element title = doc.createElement("title");
    title.setNodeValue("Parts Documentation");
    head.appendChild(title);
    html.appendChild(head);

    Element body = doc.createElement("body");

    appendTable(doc, body, file);

    html.appendChild(body);

    doc.appendChild(html);

    return transform(doc).getBytes();
  }

  /**
   * Creates and returns an HTML as a byte array from the given ScadDocuFiles according to the
   * specified example
   *
   * @param files Collection of ScadDocuFiles that the exporter shall convert
   * @return byte array, which represents the created HTML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files)
      throws ParserConfigurationException, TransformerException {
    Document doc = getDoc();

    Element html = doc.createElement("html");
    Element head = doc.createElement("head");

    Element title = doc.createElement("title");
    title.appendChild(doc.createTextNode("Parts Documentation"));
    head.appendChild(title);
    html.appendChild(head);

    Element body = doc.createElement("body");

    for (ScadDocuFile file : files) {
      appendTable(doc, body, file);
    }

    html.appendChild(body);

    doc.appendChild(html);

    return transform(doc).getBytes();
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
   * Creates and append data to table
   *
   * @param doc Document to create the HTML from
   * @param body Element where the table should be appended to
   * @param file File to read all the keys from to create headers for
   */
  private void appendTable(Document doc, Element body, ScadDocuFile file) {
    Element table = doc.createElement("table");

    Element head = doc.createElement("thead");
    Element tableHeaderRow = doc.createElement("tr");

    AppendTableHeaders(tableHeaderRow, doc, file);

    head.appendChild(tableHeaderRow);
    table.appendChild(head);

    Element tableBody = doc.createElement("tbody");

    AppendTableData(tableBody, tableHeaderRow, doc, file);

    table.appendChild(tableBody);

    body.appendChild(table);
  }

  /**
   * Creates and append data to table
   *
   * @param tableHeaderRow Element where the headers should be appended to
   * @param doc Document to create the HTML from
   * @param file File to read all the keys from to create headers for
   */
  private void AppendTableHeaders(Element tableHeaderRow, Document doc, ScadDocuFile file) {
    ArrayList<String> keys = (ArrayList<String>) file.getAllKeys();

    for (int i = 0; i < keys.size(); i++) {
      Element header = doc.createElement("th");

      header.appendChild(doc.createTextNode(keys.get(i)));

      tableHeaderRow.appendChild(header);
    }
  }

  /**
   * Creates and append data to table
   *
   * @param tableBody Element where the tabledata should be appended to
   * @param headerRow Element where the headers are appended to
   * @param doc Document to create the HTML from
   */
  private void AppendTableData(Element tableBody, Element headerRow, Document doc,
      ScadDocuFile file) {
    ArrayList<Module> modules = (ArrayList<Module>) file.getModules();
    NodeList headerList = headerRow.getChildNodes();

    for (int i = 0; i < headerList.getLength(); i++) {
      for (int j = 0; j < modules.size(); j++) {
        Module module = modules.get(j);
        Element tableRow = doc.createElement("tr");
        ArrayList<Property> properties = (ArrayList<Property>) module.getProperties();

        for (int k = 0; k < properties.size(); k++) {
          Property property = properties.get(k);
          Node node = headerList.item(i);

          if (node.getFirstChild().getNodeValue().equals(property.getKey())) {
            Element tableData = doc.createElement("td");
            tableData.appendChild(doc.createTextNode(properties.get(k).getValue().toString()));

            tableRow.appendChild(tableData);
            tableBody.appendChild(tableRow);
          }
        }
      }
    }
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

    //return HTML String + do some **magic**
    return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))", "");
  }

}
