package scadinspect.data.scaddoc.error;

public class FileExportException extends Exception {

  public FileExportException(Exception initCause) {
    this.initCause(initCause);
  }
}
