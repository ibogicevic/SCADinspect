package scadinspect.data.scaddoc.properties;

/**
 * Created by eric on 24.03.17.
 */

import java.io.File;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scadinspect.data.scaddoc.Module;

public class XmlExport {

  public void getXml(Collection<Module> modules)
      throws ParserConfigurationException, TransformerException {

    try {

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      // root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("modules");
      doc.appendChild(rootElement);

      for (Module module : modules) {
        //TODO Stuff
        rootElement.appendChild(createModuleNode(module, doc));
      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("C:\\file.xml"));

      // Output to console for testing
      // StreamResult result = new StreamResult(System.out);

      transformer.transform(source, result);

    } catch (ParserConfigurationException e) {
      // TODO log parse failure
      throw e;
    } catch (TransformerException e) {
      // TODO log transformer exception
      throw e;
    }
  }

  private Element createModuleNode(Module module, Document document) {
    Element node;
    node = document.createElement("module");

    Collection<Property> properties = module.getProperties();

    for (Property property : properties) {
      Element key = document.createElement(property.getKey());
      String value = "";
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