package scadinspect.data.scaddoc.export.format;

import java.util.Collection;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.error.FileExportException;

/**
 * @author desyon, tom on 30.03.17.
 */
public interface Exporter {

  String getOutput(Collection<Module> modules) throws FileExportException;

}
