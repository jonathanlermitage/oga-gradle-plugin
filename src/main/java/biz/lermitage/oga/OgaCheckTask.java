package biz.lermitage.oga;

import biz.lermitage.oga.cfg.Definitions;
import biz.lermitage.oga.util.IOTools;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class OgaCheckTask extends DefaultTask {

    private static final String DEFINITIONS_URL = "https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json";
    private static final String PRJ_URL = "github.com/jonathanlermitage/oga-gradle-plugin";

    private Project project;

    public void setProject(Project project) {
        this.project = project;
    }

    @TaskAction
    void doCheck() throws IOException {
        // load black-list
        Definitions definitions = IOTools.readDefinitionsFromUrl(new URL(DEFINITIONS_URL));
        debug("Loaded definitions file version: " + definitions.getVersion() + ", " + definitions.getDate());

        // read project's dependencies
        Set<String> dependencies = new HashSet<>();
        info("Old GroupId Alerter - " + PRJ_URL);
        info("Checking dependencies on project '" + project.getName() + ":" + project.getVersion() + "'");
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
                        error("" + msg);
                        deprecatedDependencies.add(dep);
                    }
                } else {
                    if (groupdId.equals(mig.getOldGroupId()) && artifactId.equals(mig.getOldArtifactId())) {
                        String msg = "'" + groupdId + ":" + artifactId + "' should be replaced by '" + mig.getNewerGroupId() + ":" + mig.getNewerArtifactId() + "'";
                        error(msg);
                        deprecatedDependencies.add(dep);
                    }
                }
            })
        );
        if (deprecatedDependencies.isEmpty()) {
            info("No problem detected. Good job! :-)");
        } else {
            error("Project has old dependencies; see warning/error messages");
            throw new IllegalStateException("Project has old dependencies; see warning/error messages");
        }
    }

    private void debug(String msg) {
        project.getLogger().debug(msg);
    }

    private void info(String msg) {
        project.getLogger().info(msg);
    }

    private void error(String msg) {
        project.getLogger().error("[ERROR] " + msg);
    }
}
