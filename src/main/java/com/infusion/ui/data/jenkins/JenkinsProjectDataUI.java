package com.infusion.ui.data.jenkins;

import java.util.Objects;

public class JenkinsProjectDataUI {

    private String name;
    private String buildStability;
    private Integer buildStabilityScore;
    private String testReport;
    private Integer testReportScore;
    private String projectUrl;
    private String trendUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildStability() {
        return buildStability;
    }

    public void setBuildStability(String buildStability) {
        this.buildStability = buildStability;
    }

    public Integer getBuildStabilityScore() {
        return buildStabilityScore;
    }

    public void setBuildStabilityScore(Integer buildStabilityScore) {
        this.buildStabilityScore = buildStabilityScore;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public Integer getTestReportScore() {
        return testReportScore;
    }

    public void setTestReportScore(Integer testReportScore) {
        this.testReportScore = testReportScore;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getTrendUrl() {
        return trendUrl;
    }

    public void setTrendUrl(String trendUrl) {
        this.trendUrl = trendUrl;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.projectUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JenkinsProjectDataUI other = (JenkinsProjectDataUI) obj;
        if (!Objects.equals(this.projectUrl, other.projectUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JenkinsProjectDataUI{" + "name=" + name + ", buildStability=" + buildStability + ", buildStabilityScore=" + buildStabilityScore + ", testReport=" + testReport + ", testReportScore=" + testReportScore + ", projectUrl=" + projectUrl + ", trendUrl=" + trendUrl + '}';
    }
}
