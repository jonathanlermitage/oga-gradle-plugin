package biz.lermitage.oga;

import org.junit.Test;

import java.io.IOException;

@SuppressWarnings("NewClassNamingConvention")
public class OgaCheckTaskPluginTestGradle9_5 extends AbstractOgaCheckTaskPluginTest {

    @Test
    public void testOgaCheckTaskOkSimple() throws IOException {
        internalTestOgaCheckTaskSimpleOk(GRADLE_9_5_VERSION);
    }

    @Test
    public void testOgaCheckTaskOk() throws IOException {
        internalTestOgaCheckTaskOk(GRADLE_9_5_VERSION);
    }

    @Test
    public void testOgaCheckTaskKo() throws IOException {
        internalTestOgaCheckTaskKo(GRADLE_9_5_VERSION);
    }
}
