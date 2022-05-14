package biz.lermitage.oga;

import org.junit.Test;

import java.io.IOException;

@SuppressWarnings("NewClassNamingConvention")
public class OgaCheckTaskPluginTestGradle6 extends AbstractOgaCheckTaskPluginTest {

    @Test
    public void testOgaCheckTaskOkSimple() throws IOException {
        internalTestOgaCheckTaskSimpleOk(GRADLE_6_VERSION);
    }

    @Test
    public void testOgaCheckTaskOk() throws IOException {
        internalTestOgaCheckTaskOk(GRADLE_6_VERSION);
    }

    @Test
    public void testOgaCheckTaskKo() throws IOException {
        internalTestOgaCheckTaskKo(GRADLE_6_VERSION);
    }
}
