package export.format;

import java.util.Collection;
import data.ScadDocuFile;

public interface Exporter {

	public enum ExportFormat {
		CSV, HTML, MD
	}

	byte[] getOutput(ScadDocuFile file) throws Exception;

	byte[] getOutput(Collection<ScadDocuFile> files) throws Exception;
}
