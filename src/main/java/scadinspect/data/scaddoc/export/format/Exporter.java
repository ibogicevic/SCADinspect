package scadinspect.data.scaddoc.export.format;

import java.util.Collection;
import scadinspect.data.scaddoc.ScadDocuFile;

public interface Exporter {

  byte[] getOutput(ScadDocuFile file) throws Exception;

  byte[] getOutput(Collection<ScadDocuFile> files) throws Exception;
}
