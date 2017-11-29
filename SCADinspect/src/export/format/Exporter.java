package export.format;

import java.util.Collection;
import data.ScadDocuFile;

public interface Exporter {

  byte[] getOutput(ScadDocuFile file) throws Exception;

  byte[] getOutput(Collection<ScadDocuFile> files) throws Exception;
}
