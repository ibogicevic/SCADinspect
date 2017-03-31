package scadinspect.data.scaddoc.export.format;

import scadinspect.data.scaddoc.ScadDocuFile;

/**
 * @author desyon, tom on 30.03.17.
 */
public interface Exporter {

  String getOutput(ScadDocuFile file) throws Exception;
}
