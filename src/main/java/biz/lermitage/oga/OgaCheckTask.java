package biz.lermitage.oga;

import biz.lermitage.oga.cfg.Definitions;
import biz.lermitage.oga.util.IOTools;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
@DisableCachingByDefault(because = "Checks dependencies against a remote definitions file")
public abstract class OgaCheckTask extends DefaultTask {

    private static final String DEFINITIONS_URL = "https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json";
    private static final String PRJ_URL = "github.com/jonathanlermitage/oga-gradle-plugin";

    @Input
    public abstract Property<String> getProjectName();

    @Input
    public abstract Property<String> getProjectVersion();

    @Input
    public abstract SetProperty<String> getDependencies();

    @TaskAction
    void doCheck() throws IOException {
        // load black-list
        Definitions definitions = IOTools.readDefinitionsFromUrl(URI.create(DEFINITIONS_URL));
        getLogger().debug("Loaded definitions file version: " + definitions.getVersion() + ", " + definitions.getDate());

        // read project's dependencies
        Set<String> dependencies = getDependencies().get();
        getLogger().info("Old GroupId Alerter - " + PRJ_URL);
        getLogger().info("Checking dependencies on project '" + getProjectName().get() + ":" + getProjectVersion().get() + "'");

        // compare project dependencies to integrated black-list
        Set<String> deprecatedDependencies = new HashSet<>();
        definitions.getMigration().forEach(mig -> dependencies.forEach(dep -> {
                String[] depSplit = dep.split(":");
                String groupdId = depSplit[0];
                String artifactId = depSplit[1];
                if (mig.isGroupIdOnly()) {
                    if (groupdId.equals(mig.getOldGroupId())) {
                        String msg = "'" + groupdId + "' groupId should be replaced by '" + mig.getNewerGroupId() + "'";
                        getLogger().error("[ERROR] " + msg);
                        deprecatedDependencies.add(dep);
                    }
                } else {
                    if (groupdId.equals(mig.getOldGroupId()) && artifactId.equals(mig.getOldArtifactId())) {
                        String msg = "'" + groupdId + ":" + artifactId + "' should be replaced by '" + mig.getNewerGroupId() + ":" + mig.getNewerArtifactId() + "'";
                        getLogger().error("[ERROR] " + msg);
                        deprecatedDependencies.add(dep);
                    }
                }
            })
        );
        if (deprecatedDependencies.isEmpty()) {
            getLogger().info("No problem detected. Good job! :-)");
        } else {
            getLogger().error("[ERROR] Project has old dependencies; see warning/error messages");
            throw new IllegalStateException("Project has old dependencies; see warning/error messages");
        }
    }
}
