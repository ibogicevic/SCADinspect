package scadinspect.data.scaddoc.error;

/**
 * @author desyon on 30.03.17.
 */
public class FileExportException extends Exception {

  public FileExportException(Exception initCause) {
    this.initCause(initCause);
  }
}
