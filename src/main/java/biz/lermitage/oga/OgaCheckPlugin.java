package biz.lermitage.oga;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class OgaCheckPlugin implements Plugin<Project> {

    public void apply(Project project) {
        project.getTasks().register("biz-lermitage-oga-gradle-check", OgaCheckTask.class, task -> {
            task.getProjectName().set(project.getName());
            task.getProjectVersion().set(project.provider(() -> project.getVersion().toString()));
            task.getDependencies().set(project.provider(() -> {
                Set<String> dependencies = new HashSet<>();
                for (Configuration config : project.getConfigurations()) {
                    try {
                        config.getAllDependencies().forEach(dep -> {
                            if (dep.getGroup() != null && !dep.getName().equals("unspecified")) {
                                dependencies.add(dep.getGroup() + ":" + dep.getName());
                            }
                        });
                    } catch (Exception e) {
                        project.getLogger().warn("Error while retrieving dependencies for config: {} - Skipping. The error message was: {}",
                            config.getName(), e.getMessage());
                    }
                }
                return dependencies;
            }));
        });
    }
}
