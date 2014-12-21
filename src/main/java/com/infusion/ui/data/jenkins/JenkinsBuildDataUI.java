package com.infusion.ui.data.jenkins;

import java.util.Objects;

public class JenkinsBuildDataUI {

    private Integer buildNumber;
    private String selectedEnvironment;
    private String status;
    private Integer failCount;
    private Integer skipCount;
    private Integer totalCount;
    private Long duration;
    private Long timestamp;
    private String reportUrl;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.timestamp);
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
        final JenkinsBuildDataUI other = (JenkinsBuildDataUI) obj;
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JenkinsBuildDataUI{" + "buildNumber=" + buildNumber + ", selectedEnvironment=" + selectedEnvironment + ", status=" + status + ", failCount=" + failCount + ", skipCount=" + skipCount + ", totalCount=" + totalCount + ", duration=" + duration + ", timestamp=" + timestamp + ", reportUrl=" + reportUrl + '}';
    }

}
