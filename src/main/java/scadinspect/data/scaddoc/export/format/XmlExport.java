package scadinspect.data.scaddoc.export.format;

/**
 * @author eric on 24.03.17.
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;

public class XmlExport {

  /**
   * @param modules Collection of Module objects, which will be recreated as XML
   * @return String object, which represents the created XML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  public String getXml(Collection<Module> modules)
      throws ParserConfigurationException, TransformerException {

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("modules");
    doc.appendChild(rootElement);

    for (Module module : modules) {
      // Create module node for each module present and append it
      rootElement.appendChild(createModuleNode(module, doc));
    }

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
}