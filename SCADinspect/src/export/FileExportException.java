package export;

public class FileExportException extends Exception {

  public FileExportException(Exception initCause) {
    this.initCause(initCause);
  }
}
