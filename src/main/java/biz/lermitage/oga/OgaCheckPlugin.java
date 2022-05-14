package biz.lermitage.oga;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

@SuppressWarnings("unused")
public class OgaCheckPlugin implements Plugin<Project> {

    public void apply(Project project) {
        project
            .getTasks()
            .create("biz-lermitage-oga-gradle-check", OgaCheckTask.class, (task) -> task.setProject(project));
    }
}
