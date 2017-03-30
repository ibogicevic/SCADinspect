package scadinspect.data.scaddoc.error;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author til on 30.03.17.
 */
class FileExportExceptionTest {
  @Test
  void construct(){
    FileExportException exportException = new FileExportException(new Exception());
    assert(FileExportException.class.isInstance(exportException));
  }
}