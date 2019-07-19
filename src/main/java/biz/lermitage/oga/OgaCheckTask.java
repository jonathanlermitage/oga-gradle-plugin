package biz.lermitage.oga;

import biz.lermitage.oga.cfg.Definitions;
import biz.lermitage.oga.util.IOTools;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class OgaCheckTask extends DefaultTask {
    
    private static final String DEFINITIONS_URL = "https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json";
    private static final String GITHUB_ISSUES_URL = "github.com/jonathanlermitage/oga-gradle-plugin";
    private static final Logger LOGGER = LoggerFactory.getLogger(OgaCheckTask.class);
    
    private Project project;
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    @TaskAction
    void doCheck() throws IOException {
        // load black-list
        Definitions definitions = IOTools.readDefinitionsFromUrl(new URL(DEFINITIONS_URL));
        LOGGER.debug("Loaded definitions file version: " + definitions.getVersion() + ", " + definitions.getDate());
        
        // read project's dependencies
        Set<String> dependencies = new HashSet<>();
        LOGGER.info("Old GroupId Alerter - " + GITHUB_ISSUES_URL);
        LOGGER.info("Checking dependencies on project '" + project.getName() + ":" + project.getVersion() + "'");
        for (Configuration config : project.getConfigurations().toArray(new Configuration[0])) {
            project.getConfigurations().getByName(config.getName()).getAllDependencies().forEach(dep -> {
                if (dep.getGroup() != null && !dep.getName().equals("unspecified")) {
                    dependencies.add(dep.getGroup() + ":" + dep.getName());
                }
            });
        }
        
        // compare project dependencies to integrated black-list
        Set<String> deprecatedDependencies = new HashSet<>();
        definitions.getMigration().forEach(mig -> dependencies.forEach(dep -> {
                String[] depSplit = dep.split(":");
                String groupdId = depSplit[0];
                String artifactId = depSplit[1];
                if (mig.isGroupIdOnly()) {
                    if (groupdId.equals(mig.getOldGroupId())) {
                        String msg = "'" + groupdId + "' groupId should be replaced by '" + mig.getNewerGroupId() + "'";
                        LOGGER.error("[ERROR] " + msg);
                        deprecatedDependencies.add(dep);
                    }
                } else {
                    if (groupdId.equals(mig.getOldGroupId()) && artifactId.equals(mig.getOldArtifactId())) {
                        String msg = "'" + groupdId + ":" + artifactId + "' should be replaced by '" + mig.getNewerGroupId() + ":" + mig.getNewerArtifactId() + "'";
                        LOGGER.error("[ERROR] " + msg);
                        deprecatedDependencies.add(dep);
                    }
                }
            })
        );
        if (deprecatedDependencies.isEmpty()) {
            LOGGER.info("No problem detected. Good job! :-)");
            //throw new RuntimeException("Project has old dependencies; see warning/error messages");
        } else {
            LOGGER.error("[ERROR] Project has old dependencies; see warning/error messages");
        }
    }
}
