package scadinspect.data.scaddoc.export.format;

import java.util.Collection;
import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.error.FileExportException;

/**
 * Created by til on 30.03.17.
 */
public interface Exporter {

  String getOutput(Collection<Module> modules) throws FileExportException;

}
