package biz.lermitage.oga;

import org.junit.Test;

import java.io.IOException;

public class OgaCheckTaskPluginTest_Gradle5 extends AbstractOgaCheckTaskPluginTest {
    
    @Test
    public void testOgaCheckTaskOkSimple() throws IOException {
        internalTestOgaCheckTaskSimpleOk(GRADLE_5_VERSION);
    }
    
    @Test
    public void testOgaCheckTaskOk() throws IOException {
        internalTestOgaCheckTaskOk(GRADLE_5_VERSION);
    }
    
    @Test
    public void testOgaCheckTaskKo() throws IOException {
        internalTestOgaCheckTaskKo(GRADLE_5_VERSION);
    }
}
