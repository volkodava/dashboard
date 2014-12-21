package com.infusion.model;

import com.infusion.model.jenkins.json.JenkinsBuildStatus;
import com.infusion.model.jenkins.json.JenkinsProject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JenkinsAggregate {

    private JenkinsProject project;
    private List<JenkinsBuildStatus> buildStatuses = new ArrayList<JenkinsBuildStatus>();
    private String trendMapContent;

    public JenkinsProject getProject() {
        return project;
    }

    public void setProject(JenkinsProject project) {
        this.project = project;
    }

    public void add(JenkinsBuildStatus buildStatus) {
        this.buildStatuses.add(buildStatus);
    }

    public List<JenkinsBuildStatus> getBuildStatuses() {
        return buildStatuses;
    }

    public void setBuildStatuses(List<JenkinsBuildStatus> buildStatuses) {
        this.buildStatuses = buildStatuses;
    }

    public String getTrendMapContent() {
        return trendMapContent;
    }

    public void setTrendMapContent(String trendMapContent) {
        this.trendMapContent = trendMapContent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.project);
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
        final JenkinsAggregate other = (JenkinsAggregate) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        return true;
    }

}
