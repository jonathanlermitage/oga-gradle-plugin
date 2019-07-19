package biz.lermitage.oga.cfg;

/**
 * Definition migration.
 *
 * @author Jonathan Lermitage
 * @version 1
 */
public class DefinitionMigration {
    
    private String old;
    
    @com.google.gson.annotations.SerializedName("new")
    private String newer;
    
    public String getOld() {
        return old;
    }
    
    public void setOld(String old) {
        this.old = old;
    }
    
    public String getNewer() {
        return newer;
    }
    
    public void setNewer(String newer) {
        this.newer = newer;
    }
    
    public String getOldGroupId() {
        return isGroupIdOnly() ? old : old.split(":")[0];
    }
    
    public String getOldArtifactId() {
        return isGroupIdOnly() ? "" : old.split(":")[1];
    }
    
    public String getNewerGroupId() {
        return isGroupIdOnly() ? newer : newer.split(":")[0];
    }
    
    public String getNewerArtifactId() {
        return isGroupIdOnly() ? "" : newer.split(":")[1];
    }
    
    public boolean isGroupIdOnly() {
        return !old.contains(":");
    }
}
