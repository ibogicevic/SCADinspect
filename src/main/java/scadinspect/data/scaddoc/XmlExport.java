package scadinspect.data.scaddoc;

/**
 * Created by eric on 22.03.17.
 */

import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scadinspect.data.scaddoc.properties.PairProperty;
import scadinspect.data.scaddoc.properties.Property;

public class XmlExport {

  public String getXml(List<Module> modules) throws ParserConfigurationException {

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("modules");
    doc.appendChild(rootElement);

    // every single module into one element
    for (Module module : modules
        ) {
      rootElement.appendChild(singleModule(module));
    }

    return doc.toString();

  }

  private Element singleModule(Module module) throws ParserConfigurationException {

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();
    Element moduleNode = doc.createElement("module");

    List<Property> properties = module.getProperties();

    for (Property property : properties) {

      Element key = doc.createElement(property.getKey());

      // if it is PairProperty, create attribute with associated value
      if (property instanceof PairProperty) {
        PairProperty pairProperty;
        pairProperty = (PairProperty) property;

        key.setAttribute("metric", pairProperty.getValue().getMetric());

        Attr attr = doc.createAttribute("metric");
        attr.setValue(pairProperty.getValue().getMetric());

        key.setAttributeNode(attr);
        key.appendChild(doc.createTextNode(pairProperty.getValue().getValue().toString()));

      } else {
        // create text node with value inside and append
        key.appendChild(doc.createTextNode(property.getValue().toString()));
      }

      moduleNode.appendChild(key);
    }
    return moduleNode;
  }
}