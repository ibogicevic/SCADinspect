/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadinspect.data.analysis;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
/**
 *
 * @author balsfull
 */
public class ExampleTest {
    
    @Test
    public void testInstance() {
        assertNotNull(new Issue(false, null, 0, null, null, null));
    }
}
