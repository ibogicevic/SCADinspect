package scadinspect.data.scaddoc.error;


import org.junit.jupiter.api.Test;

class FileExportExceptionTest {
  @Test
  void construct(){
    FileExportException exportException = new FileExportException(new Exception());
    assert(FileExportException.class.isInstance(exportException));
  }
}