package scadinspect.data.scaddoc.export.format;

/**
 * @author eric, Desyon, richteto on 24.03.17.
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
   * Creates and returns a XML String from the given ScadDocuFile analog to the specified
   * example
   *
   * @param file ScadDocuFile that the Export shall be exported for
   * @return String object, which represents the created XML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  @Override
  public byte[] getOutput(ScadDocuFile file)
      throws ParserException, ParserConfigurationException, TransformerException {
    // root elements
    Document doc         = getDoc();
    Element  rootElement = doc.createElement(file.getPath().toString());
    doc.appendChild(rootElement);

    for (Module module : file.getModules()) {
      // Create module node for each module present and append it
      rootElement.appendChild(createModuleNode(module, doc));
    }

    return transform(doc).getBytes();
  }

  /**
   * Creates and returns a XML String from the given ScadDocuFiles according to the specified
   * example
   *
   * @param files Collection of ScadDocuFiles that the exporter shall convert
   * @return String object, which represents the created XML
   * @throws ParserConfigurationException Indicates a serious configuration error.
   * @throws TransformerException Specifies an exceptional condition that occurred during the
   * transformation process.
   */
  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files)
      throws ParserConfigurationException, TransformerException {
    // root elements
    Document doc         = getDoc();
    Element  rootElement = doc.createElement("files");
    doc.appendChild(rootElement);

    for (ScadDocuFile file : files) {
      Element fileElement = doc.createElement(file.getPath().toString());
      for (Module module : file.getModules()) {
        // Create module node for each module present and append it
        fileElement.appendChild(createModuleNode(module, doc));
      }
      rootElement.appendChild(fileElement);
    }

    return transform(doc).getBytes();
  }

  /**
   * Creates the XML node for one module
   *
   * @param module Module object, which needs to be recreated as a DOM node
   * @param document Document object, which creates the necessary nodes
   * @return Element object, which will be created from the module parameter
   */
  private Element createModuleNode(Module module, Document document) {
    Element node;
    node = document.createElement("module");

    Collection<Property> properties = module.getProperties();

    for (Property property : properties) {
      Element key = document.createElement(escapeSpecialCharacters(property.getKey()));
      String  value;
      if (property instanceof PairProperty) {
        // Create metric and value keys for PairProperty
        PairProperty temp = (PairProperty) property;

        Element nodeMetric;
        Element nodeValue;

        nodeMetric = document.createElement("metric");
        nodeValue = document.createElement("value");

        nodeMetric.appendChild(document.createTextNode(escapeSpecialCharacters(temp.getValue().getMetric())));
        nodeValue.appendChild(document.createTextNode(escapeSpecialCharacters(temp.getValue().getValue().toString())));

        key.appendChild(nodeMetric);
        key.appendChild(nodeValue);
      } else {
        value = escapeSpecialCharacters(property.getValue().toString());
        key.appendChild(document.createTextNode(value));
      }
      // Append key node to module node, to return whole module
      node.appendChild(key);
    }
    return node;
  }

  /**
   * Creates a empty Document that provides the base for the XML document
   *
   * @return a Document to build the XML File
   * @throws ParserConfigurationException if the document creation failed
   */
  private Document getDoc() throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder        docBuilder = docFactory.newDocumentBuilder();

    // root elements
    return docBuilder.newDocument();
  }

  /**
   * Creates a formatted XML string
   *
   * @param doc Document to create the XML from
   * @return beautified XML String
   * @throws TransformerException if the transformer is configured wrong, or the document is
   * incorrect
   */
  private String transform(Document doc) throws TransformerException {
    // create transformer for beautifying XML
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer        transformer        = transformerFactory.newTransformer();

    //configure the transformer
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

    //create transformer compatible source
    DOMSource source = new DOMSource(doc);

    //write out beautified XML
    StringWriter sw     = new StringWriter();
    StreamResult result = new StreamResult(sw);

    transformer.transform(source, result);

    //return XML String + do some **magic**
    return sw.getBuffer().toString().replaceAll("((?<=>)\\[)|(](?=<))", "");
  }

  /**
   * Escapes all characters not allowed in XML
   *
   * @param string The string to be escaped
   * @return The escaped input
   */
  private static String escapeSpecialCharacters(String string) {
    return string
        .replaceAll("&", "&amp;")
        .replaceAll("\"", "&quot;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll(" ", "&nbsp;")
        .replaceAll("¡", "&iexcl;")
        .replaceAll("¢", "&cent;")
        .replaceAll("£", "&pound;")
        .replaceAll("¤", "&curren;")
        .replaceAll("¥", "&yen;")
        .replaceAll("¦", "&brvbar;")
        .replaceAll("§", "&sect;")
        .replaceAll("¨", "&uml;")
        .replaceAll("©", "&copy;")
        .replaceAll("ª", "&ordf;")
        .replaceAll("«", "&laquo;")
        .replaceAll("»", "&raquo;")
        .replaceAll("¬", "&not;")
        .replaceAll("®", "&reg;")
        .replaceAll("¯", "&macr;")
        .replaceAll("°", "&deg;")
        .replaceAll("±", "&plusmn;")
        .replaceAll("²", "&sub2;")
        .replaceAll("³", "&sub3;")
        .replaceAll("´", "&acute;")
        .replaceAll("µ", "&micro;")
        .replaceAll("¶", "&para;")
        .replaceAll("·", "&middot;")
        .replaceAll("¸", "&cedil;")
        .replaceAll("¹", "&sub1;")
        .replaceAll("º", "&ordm;")
        .replaceAll("¼", "&frac14;")
        .replaceAll("½", "&frac12;")
        .replaceAll("¾", "&frac34;")
        .replaceAll("¿", "&iquest;")
        .replaceAll("À", "&Agrave;")
        .replaceAll("Á", "&Aacute;")
        .replaceAll("Â", "&Acirc;")
        .replaceAll("Ã", "&Atilde;")
        .replaceAll("Ä", "&Auml;")
        .replaceAll("Å", "&Aring;")
        .replaceAll("Æ", "&AElig;")
        .replaceAll("Ç", "&Ccedil;")
        .replaceAll("È", "&Egrave;")
        .replaceAll("É", "&Eacute;")
        .replaceAll("Ê", "&Ecirc;")
        .replaceAll("Ë", "&Euml;")
        .replaceAll("Ì", "&Igrave;")
        .replaceAll("Í", "&Iacute;")
        .replaceAll("Î", "&Icirc;")
        .replaceAll("Ï", "&Iuml;")
        .replaceAll("Ò", "&Ograve;")
        .replaceAll("Ó", "&Oacute;")
        .replaceAll("Ô", "&Ocirc;")
        .replaceAll("Õ", "&Otilde;")
        .replaceAll("Ö", "&Ouml;")
        .replaceAll("Ø", "&Oslash;")
        .replaceAll("Ù", "&Ugrave;")
        .replaceAll("Ú", "&Uacute;")
        .replaceAll("Û", "&Ucirc;")
        .replaceAll("Ü", "&Uuml;")
        .replaceAll("Ð", "&ETH;")
        .replaceAll("Ñ", "&Ntilde;")
        .replaceAll("×", "&times;")
        .replaceAll("Ý", "&Yacute;")
        .replaceAll("Þ", "&THORN;")
        .replaceAll("ß", "&szlig;")
        .replaceAll("à", "&agrave;")
        .replaceAll("á", "&aacute;")
        .replaceAll("â", "&acirc;")
        .replaceAll("ã", "&atilde;")
        .replaceAll("ä", "&auml;")
        .replaceAll("å", "&aring;")
        .replaceAll("æ", "&aelig;")
        .replaceAll("ç", "&ccedil;")
        .replaceAll("è", "&egrave;")
        .replaceAll("é", "&eacute;")
        .replaceAll("ê", "&ecirc;")
        .replaceAll("ë", "&euml;")
        .replaceAll("ì", "&igrave;")
        .replaceAll("í", "&iacute;")
        .replaceAll("î", "&icirc;")
        .replaceAll("ï", "&iuml;")
        .replaceAll("ð", "&eth;")
        .replaceAll("ñ", "&ntilde;")
        .replaceAll("ò", "&ograve;")
        .replaceAll("ó", "&oacute;")
        .replaceAll("ô", "&ocirc;")
        .replaceAll("õ", "&otilde;")
        .replaceAll("ö", "&ouml;")
        .replaceAll("÷", "&divide;")
        .replaceAll("ø", "&oslash;")
        .replaceAll("ù", "&ugrave;")
        .replaceAll("ú", "&uacute;")
        .replaceAll("û", "&ucirc;")
        .replaceAll("ü", "&uuml;")
        .replaceAll("ý", "&yacute;")
        .replaceAll("þ", "&thorn;")
        .replaceAll("ÿ", "&yuml;")
        .replaceAll("Œ", "&#338;")
        .replaceAll("œ", "&#339;")
        .replaceAll("Š", "&#352;")
        .replaceAll("š", "&#353;")
        .replaceAll("Ÿ", "&#376;")
        .replaceAll("ƒ", "&#402;")
        .replaceAll("–", "&#8211;")
        .replaceAll("—", "&#8212;")
        .replaceAll("‘", "&#8216;")
        .replaceAll("’", "&#8217;")
        .replaceAll("‚", "&#8218;")
        .replaceAll("“", "&#8220;")
        .replaceAll("”", "&#8221;")
        .replaceAll("„", "&#8222;")
        .replaceAll("†", "&#8224;")
        .replaceAll("‡", "&#8225;")
        .replaceAll("•", "&#8226;")
        .replaceAll("…", "&#8230;")
        .replaceAll("‰", "&#8240;")
        .replaceAll("€", "&euro;")
        .replaceAll("™", "&#8482;")
        .replaceAll("\\{", "&#123;")
        .replaceAll("\\|", "&#124;")
        .replaceAll("}", "&#125;")
        .replaceAll("~", "&#126;")
        .replaceAll("`", "&#96;")
        .replaceAll("\\[", "&#91;")
        .replaceAll("\\\\", "&#92;")
        .replaceAll("]", "&#93;")
        .replaceAll("\\^", "&#94;")
        .replaceAll("_", "&#95;");
  }
}