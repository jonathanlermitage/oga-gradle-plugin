package biz.lermitage.oga;

import org.apache.commons.io.IOUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractOgaCheckTaskPluginTest {
    
    protected static final String GRADLE_4_VERSION = "4.10.3";
    protected static final String GRADLE_5_VERSION = "5.5.1";
    
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    
    private File buildFile;
    
    @Before
    public void setup() throws IOException {
        if (!testProjectDir.getRoot().exists()) {
            assertTrue(testProjectDir.getRoot().mkdirs());
        }
        buildFile = testProjectDir.newFile("build.gradle");
    }
    
    protected void internalTestOgaCheckTaskSimpleOk(String gradleVersion) throws IOException {
        String buildFileContent = "plugins { id 'biz.lermitage.oga' }";
        
        writeFile(buildFile, buildFileContent);
        
        BuildResult result = GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withArguments("biz-lermitage-oga-gradle-check", "--info", "--stacktrace")
            .withPluginClasspath(getPluginClasspathFiles())
            .withGradleVersion(gradleVersion)
            .build();
        
        assertTrue(result.getOutput().contains("No problem detected. Good job! :-)"));
        
        BuildTask task = result.task(":biz-lermitage-oga-gradle-check");
        assertNotNull(task);
        assertEquals(TaskOutcome.SUCCESS, task.getOutcome());
    }
    
    protected void internalTestOgaCheckTaskOk(String gradleVersion) throws IOException {
        String buildFileContent = "buildscript {\n" +
            "    repositories {\n" +
            "        mavenCentral()\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "plugins {\n" +
            "    id 'java'\n" +
            "    id 'biz.lermitage.oga'\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    compile 'org.bouncycastle:bcprov-jdk15:140'\n" +
            "    compile 'com.graphql-java-kickstart:graphql-spring-boot-starter:5.0.2'\n" +
            "    testCompile 'junit:junit:4.12'\n" +
            "}\n" +
            "\n" +
            "repositories {\n" +
            "    mavenCentral()\n" +
            "}\n";
        
        writeFile(buildFile, buildFileContent);
        
        BuildResult result = GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withArguments("biz-lermitage-oga-gradle-check", "--info", "--stacktrace")
            .withPluginClasspath(getPluginClasspathFiles())
            .withGradleVersion(gradleVersion)
            .build();
        
        assertTrue(result.getOutput().contains("No problem detected. Good job! :-)"));
        
        BuildTask task = result.task(":biz-lermitage-oga-gradle-check");
        assertNotNull(task);
        assertEquals(TaskOutcome.SUCCESS, task.getOutcome());
    }
    
    protected void internalTestOgaCheckTaskKo(String gradleVersion) throws IOException {
        String buildFileContent = "buildscript {\n" +
            "    repositories {\n" +
            "        mavenCentral()\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "plugins {\n" +
            "    id 'java'\n" +
            "    id 'biz.lermitage.oga'\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    compile 'bouncycastle:bcprov-jdk15:140'\n" +
            "    compile 'com.graphql-java:graphql-spring-boot-starter:5.0.2'\n" +
            "    testCompile 'junit:junit:4.12'\n" +
            "}\n" +
            "\n" +
            "repositories {\n" +
            "    mavenCentral()\n" +
            "}\n";
        
        writeFile(buildFile, buildFileContent);
        
        BuildResult result = GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withArguments("biz-lermitage-oga-gradle-check", "--info", "--stacktrace")
            .withPluginClasspath(getPluginClasspathFiles())
            .withGradleVersion(gradleVersion)
            .build();
        
        assertTrue(result.getOutput().contains("'bouncycastle' groupId should be replaced by 'org.bouncycastle'"));
        assertTrue(result.getOutput().contains("'com.graphql-java:graphql-spring-boot-starter' should be replaced by 'com.graphql-java-kickstart:graphql-spring-boot-starter'"));
        
        BuildTask task = result.task(":biz-lermitage-oga-gradle-check");
        assertNotNull(task);
        assertEquals(TaskOutcome.SUCCESS, task.getOutcome());
    }
    
    private void writeFile(File destination, String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
            output.write(content);
        }
    }
    
    private List<File> getPluginClasspathFiles() throws IOException {
        URL pluginClasspathResource = getClass().getClassLoader().getResource("plugin-classpath.txt");
        assertNotNull("did not find plugin classpath resource, run `testClasses` build task", pluginClasspathResource);
        
        String pluginClasspathStr = IOUtils.toString(pluginClasspathResource, "utf-8");
        return Arrays.stream(pluginClasspathStr.split("\n")).map(File::new).collect(Collectors.toList());
    }
}
