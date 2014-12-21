package com.infusion.ui.data.jenkins;

public class ServiceUI {

    private Integer buildNumber;
    private String selectedEnvironment;
    private ServiceStatus status;

    public ServiceUI() {
    }

    public ServiceUI(Integer buildNumber, String selectedEnvironment, ServiceStatus status) {
        this.buildNumber = buildNumber;
        this.selectedEnvironment = selectedEnvironment;
        this.status = status;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getSelectedEnvironment() {
        return selectedEnvironment;
    }

    public void setSelectedEnvironment(String selectedEnvironment) {
        this.selectedEnvironment = selectedEnvironment;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceUI{" + "buildNumber=" + buildNumber + ", selectedEnvironment=" + selectedEnvironment + ", status=" + status + '}';
    }

}
