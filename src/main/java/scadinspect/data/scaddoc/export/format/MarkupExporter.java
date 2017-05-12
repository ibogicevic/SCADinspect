package scadinspect.data.scaddoc.export.format;

/**
 * @author by Desyon, Eric on 12.05.2017.
 */
public abstract class MarkupExporter implements Exporter {

  /**
   * Escapes all characters not allowed in XML
   *
   * @param string The string to be escaped
   * @return The escaped input
   */
  static String escapeSpecialCharacters(String string) {
    return string
        .replaceAll("[ÄÀÁÂÃÅ]", "A")
        .replaceAll("[àáâãä]", "a")
        .replaceAll("[ÈÉÊË]", "E")
        .replaceAll("[èéêë]", "e")
        .replaceAll("[ÌÍÎÏ]", "I")
        .replaceAll("[ìíîï]", "i")
        .replaceAll("[ÒÓÔÕÖØ]", "O")
        .replaceAll("[ðòóôõöø]", "o")
        .replaceAll("[ÙÚÛÜ]", "U")
        .replaceAll("[ùúûü]", "u")
        .replaceAll("ß", "ss")
        .replaceAll("&|\\[|<|>|]|\\|\"|\\||!|\"|\'|§|$|%|\\(|\\)|;|\\?|\\^|°|#|\\\\|/", "");
  }
}
