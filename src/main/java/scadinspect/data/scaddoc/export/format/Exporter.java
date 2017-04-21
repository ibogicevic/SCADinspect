package scadinspect.data.scaddoc.export.format;

import java.util.Collection;
import scadinspect.data.scaddoc.ScadDocuFile;

/**
 * @author desyon, richteto on 30.03.17.
 */
public interface Exporter {

  String getOutput(ScadDocuFile file) throws Exception;

  String getOutput(Collection<ScadDocuFile> files) throws Exception;
}
