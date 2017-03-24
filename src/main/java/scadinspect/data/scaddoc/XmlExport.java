package scadinspect.data.scaddoc;

/**
 * Created by eric on 24.03.17.
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
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;

public class XmlExport {

  public String getXml(Collection<Module> modules)
      throws ParserConfigurationException, TransformerException {

    try {

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      // root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("modules");
      doc.appendChild(rootElement);

      for (Module module : modules) {
        rootElement.appendChild(createModuleNode(module, doc));
      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(doc);
      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);

      // Output to console for testing
      // StreamResult result = new StreamResult(System.out);

      transformer.transform(source, result);

      //return XML String + do some **magic**
      return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))","");

    } catch (Exception e) {
      // TODO log parse failure
      throw e;
    }
  }

  private Element createModuleNode(Module module, Document document) {
    Element node;
    node = document.createElement("module");

    Collection<Property> properties = module.getProperties();

    for (Property property : properties) {
      Element key = document.createElement(property.getKey());
      String value;
      if (property instanceof PairProperty) {
        PairProperty temp = (PairProperty) property;
        value = temp.getValue().getValue() + temp.getValue().getMetric();
      } else {
        value = property.getValue().toString();
      }
      key.appendChild(document.createTextNode(value));
      node.appendChild(key);
    }
    return node;
  }
}