package com.infusion.data.jenkins;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JenkinsProjectData {

    private String name;
    private String buildStability;
    private Integer buildStabilityScore;
    private String testReport;
    private Integer testReportScore;
    private String projectUrl;
    private String trendUrl;
    private String trendMapContent;
    private List<JenkinsBuildData> builds = new ArrayList<JenkinsBuildData>();

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

    public String getTrendMapContent() {
        return trendMapContent;
    }

    public void setTrendMapContent(String trendMapContent) {
        this.trendMapContent = trendMapContent;
    }

    public void addJenkinsBuildDto(JenkinsBuildData dataObj) {
        this.builds.add(dataObj);
    }

    public List<JenkinsBuildData> getBuilds() {
        return builds;
    }

    public void setBuilds(List<JenkinsBuildData> builds) {
        this.builds = builds;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.name);
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
        final JenkinsProjectData other = (JenkinsProjectData) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
