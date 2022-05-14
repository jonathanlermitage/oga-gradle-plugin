package biz.lermitage.oga;

import org.junit.Test;

import java.io.IOException;

@SuppressWarnings("NewClassNamingConvention")
public class OgaCheckTaskPluginTestGradle4 extends AbstractOgaCheckTaskPluginTest {

    @Test
    public void testOgaCheckTaskOkSimple() throws IOException {
        internalTestOgaCheckTaskSimpleOk(GRADLE_4_VERSION);
    }

    @Test
    public void testOgaCheckTaskOk() throws IOException {
        internalTestOgaCheckTaskOk(GRADLE_4_VERSION);
    }

    @Test
    public void testOgaCheckTaskKo() throws IOException {
        internalTestOgaCheckTaskKo(GRADLE_4_VERSION);
    }
}
