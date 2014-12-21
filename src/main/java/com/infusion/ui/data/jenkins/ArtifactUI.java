package com.infusion.ui.data.jenkins;

public class ArtifactUI {

    private String version;
    private ServiceStatus status;

    public ArtifactUI() {
    }

    public ArtifactUI(String version, ServiceStatus status) {
        this.version = version;
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArtifactUI{" + "version=" + version + ", status=" + status + '}';
    }
}
