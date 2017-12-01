/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package export.format;

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
import org.w3c.dom.Text;
import data.Module;
import data.ScadDocuFile;
import data.properties.Property;

public class HtmlExporter implements Exporter{

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
  public byte[] getOutput(ScadDocuFile file)
      throws ParserConfigurationException, TransformerException {
    Document doc = getDoc();

    Element html = doc.createElement("html");
    html.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");

    Element head = doc.createElement("head");

    insertCss(doc, head);

    Element title = doc.createElement("title");
    title.appendChild(doc.createTextNode("Parts Documentation"));
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
  public byte[] getOutput(Collection<ScadDocuFile> files)
      throws ParserConfigurationException, TransformerException {
    Document doc = getDoc();

    Element html = doc.createElement("html");
    html.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");

    Element head = doc.createElement("head");

    insertCss(doc, head);

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

    if (keys.isEmpty()) {
      Element header = doc.createElement("th");
      tableHeaderRow.appendChild(header);

      return;
    }

    for (String key : keys) {
      Element header = doc.createElement("th");

      header.appendChild(doc.createTextNode(key));

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

    if (modules.isEmpty()) {
      Element tableRow = doc.createElement("tr");
      Element tableData = doc.createElement("td");
      tableRow.appendChild(tableData);
      tableBody.appendChild(tableRow);
    }

    for (Module module : modules) {
      Element tableRow = doc.createElement("tr");
      for (int i = 0; i < headerList.getLength(); i++) {
        Node header = headerList.item(i);
        boolean foundHeader = false;
        for (Property property : module.getProperties()) {
          if (header.getFirstChild().getNodeValue().equals(property.getKey())) {
            foundHeader = true;

            Element tableData = doc.createElement("td");
            tableData.appendChild(doc.createTextNode(property.getValue().toString()));

            tableRow.appendChild(tableData);
            break;
          }
        }
        if (!foundHeader) {
          tableRow.appendChild(doc.createElement("td"));
        }
      }

      tableBody.appendChild(tableRow);
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
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    DOMSource source = new DOMSource(doc);
    StringWriter sw = new StringWriter();

    sw.write(
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" + System.lineSeparator()
            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + System
            .lineSeparator());

    StreamResult result = new StreamResult(sw);
    transformer.transform(source, result);

    //return HTML String + do some **magic**
    return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))", "");
  }

  /**
   * Inserts Style tag for CSS into the HTML
   *
   * @param doc Document to create the HTML from
   * @param head Head element from HTML, where the style will be appended to
   */
  private void insertCss(Document doc, Element head) {
    Element style = doc.createElement("style");
    style.setAttribute("type", "text/css");
    Text content = doc.createTextNode("");

    content.appendData("\n"
        + "      table {\n"
        + "        font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n"
        + "        border-collapse: collapse;\n"
        + "        margin-bottom: 15px;\n"
        + "      }\n"
        + "\n"
        + "      td, th {\n"
        + "        border: 1px solid #ddd;\n"
        + "        padding: 8px;\n"
        + "      }\n"
        + "\n"
        + "      tr:nth-child(even){background-color: #f2f2f2;}\n"
        + "      tr:hover {background-color: #ddd;}\n"
        + "\n"
        + "      th {\n"
        + "        padding-top: 12px;\n"
        + "        padding-bottom: 12px;\n"
        + "        text-align: center;\n"
        + "        background-color: #4CAF50;\n"
        + "        color: white;\n"
        + "      }"
        + "\n" + "    ");

    style.appendChild(content);
    head.appendChild(style);
  }
}
