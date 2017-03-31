package scadinspect.data.scaddoc.export.format;

/**
 * @author eric, desyon, tom on 24.03.17.
 */

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
import jdk.nashorn.internal.runtime.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;

public class XmlExporter implements Exporter {

  /**
   * @param file ScadDocuFile that the Export shall be exported for
   * @return String object, which represents the created XML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  @Override
  public String getOutput(ScadDocuFile file)
      throws ParserException, ParserConfigurationException, TransformerException {
    // root elements
    Document doc = getDoc();
    Element rootElement = doc.createElement(file.getPath().toString());
    doc.appendChild(rootElement);

    for (Module module : file.getModules()) {
      // Create module node for each module present and append it
      rootElement.appendChild(createModuleNode(module, doc));
    }

    return transform(doc);
  }

  @Override
  public String getOutput(Collection<ScadDocuFile> files) throws Exception {
    // root elements
    Document doc = getDoc();
    Element rootElement = doc.createElement("files");
    doc.appendChild(rootElement);

    for (ScadDocuFile file : files) {
      Element fileElement = doc.createElement(file.getPath().toString());
      for (Module module : file.getModules()) {
        // Create module node for each module present and append it
        fileElement.appendChild(createModuleNode(module, doc));
      }
      rootElement.appendChild(fileElement);
    }

    return transform(doc);
  }

  /**
   * @param module Module object, which needs to be recreated as a DOM node
   * @param document Document object, which creates the necessary nodes
   * @return Element object, which will be created from the module parameter
   */
  private Element createModuleNode(Module module, Document document) {
    Element node;
    node = document.createElement("module");

    Collection<Property> properties = module.getProperties();

    for (Property property : properties) {
      Element key = document.createElement(property.getKey());
      String value;
      if (property instanceof PairProperty) {
        // Create metric and value keys for PairProperty
        PairProperty temp = (PairProperty) property;

        Element nodeMetric;
        Element nodeValue;

        nodeMetric = document.createElement("metric");
        nodeValue = document.createElement("value");

        nodeMetric.appendChild(document.createTextNode(temp.getValue().getMetric()));
        nodeValue.appendChild(document.createTextNode(temp.getValue().getValue().toString()));

        key.appendChild(nodeMetric);
        key.appendChild(nodeValue);
      } else {
        value = property.getValue().toString();
        key.appendChild(document.createTextNode(value));
      }
      // Append key node to module node, to return whole module
      node.appendChild(key);
    }
    return node;
  }

  private Document getDoc() throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    return docBuilder.newDocument();
  }

  private String transform(Document doc) throws TransformerException {
    // write the content into xml format
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    DOMSource source = new DOMSource(doc);
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult(sw);
    transformer.transform(source, result);

    //return XML String + do some **magic**
    return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))", "");
  }
}