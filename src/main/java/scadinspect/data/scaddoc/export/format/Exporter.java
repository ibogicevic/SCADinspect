package scadinspect.data.scaddoc.export.format;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import java.util.Collection;
import scadinspect.data.scaddoc.ScadDocuFile;

/**
 * @author desyon, tom on 30.03.17.
 */
public interface Exporter {

  String getOutput(ScadDocuFile file) throws Exception;

  String getOutput(Collection<ScadDocuFile> files) throws Exception;
}
