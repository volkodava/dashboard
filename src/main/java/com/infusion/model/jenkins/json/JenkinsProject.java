package com.infusion.model.jenkins.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "actions",
    "description",
    "displayName",
    "displayNameOrNull",
    "name",
    "url",
    "buildable",
    "builds",
    "color",
    "firstBuild",
    "healthReport",
    "inQueue",
    "keepDependencies",
    "lastBuild",
    "lastCompletedBuild",
    "lastFailedBuild",
    "lastStableBuild",
    "lastSuccessfulBuild",
    "lastUnstableBuild",
    "lastUnsuccessfulBuild",
    "nextBuildNumber",
    "property",
    "queueItem",
    "concurrentBuild",
    "downstreamProjects",
    "scm",
    "upstreamProjects"
})
public class JenkinsProject {

    @JsonProperty("actions")
    private List<Action> actions = new ArrayList<Action>();
    @JsonProperty("description")
    private String description;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("displayNameOrNull")
    private Object displayNameOrNull;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("buildable")
    private Boolean buildable;
    @JsonProperty("builds")
    private List<Build> builds = new ArrayList<Build>();
    @JsonProperty("color")
    private String color;
    @JsonProperty("firstBuild")
    private Build firstBuild;
    @JsonProperty("healthReport")
    private List<HealthReport> healthReport = new ArrayList<HealthReport>();
    @JsonProperty("inQueue")
    private Boolean inQueue;
    @JsonProperty("keepDependencies")
    private Boolean keepDependencies;
    @JsonProperty("lastBuild")
    private Build lastBuild;
    @JsonProperty("lastCompletedBuild")
    private Build lastCompletedBuild;
    @JsonProperty("lastFailedBuild")
    private Build lastFailedBuild;
    @JsonProperty("lastStableBuild")
    private Object lastStableBuild;
    @JsonProperty("lastSuccessfulBuild")
    private Object lastSuccessfulBuild;
    @JsonProperty("lastUnstableBuild")
    private Object lastUnstableBuild;
    @JsonProperty("lastUnsuccessfulBuild")
    private Build lastUnsuccessfulBuild;
    @JsonProperty("nextBuildNumber")
    private Integer nextBuildNumber;
    @JsonProperty("property")
    private List<Property> property = new ArrayList<Property>();
    @JsonProperty("queueItem")
    private Object queueItem;
    @JsonProperty("concurrentBuild")
    private Boolean concurrentBuild;
    @JsonProperty("downstreamProjects")
    private List<Object> downstreamProjects = new ArrayList<Object>();
    @JsonProperty("scm")
    private Scm scm;
    @JsonProperty("upstreamProjects")
    private List<Object> upstreamProjects = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The actions
     */
    @JsonProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     *
     * @param actions The actions
     */
    @JsonProperty("actions")
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     *
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return The displayName
     */
    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName The displayName
     */
    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return The displayNameOrNull
     */
    @JsonProperty("displayNameOrNull")
    public Object getDisplayNameOrNull() {
        return displayNameOrNull;
    }

    /**
     *
     * @param displayNameOrNull The displayNameOrNull
     */
    @JsonProperty("displayNameOrNull")
    public void setDisplayNameOrNull(Object displayNameOrNull) {
        this.displayNameOrNull = displayNameOrNull;
    }

    /**
     *
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return The buildable
     */
    @JsonProperty("buildable")
    public Boolean isBuildable() {
        return buildable;
    }

    /**
     *
     * @param buildable The buildable
     */
    @JsonProperty("buildable")
    public void setBuildable(Boolean buildable) {
        this.buildable = buildable;
    }

    /**
     *
     * @return The builds
     */
    @JsonProperty("builds")
    public List<Build> getBuilds() {
        return builds;
    }

    /**
     *
     * @param builds The builds
     */
    @JsonProperty("builds")
    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    /**
     *
     * @return The color
     */
    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color The color
     */
    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return The firstBuild
     */
    @JsonProperty("firstBuild")
    public Build getFirstBuild() {
        return firstBuild;
    }

    /**
     *
     * @param firstBuild The firstBuild
     */
    @JsonProperty("firstBuild")
    public void setFirstBuild(Build firstBuild) {
        this.firstBuild = firstBuild;
    }

    /**
     *
     * @return The healthReport
     */
    @JsonProperty("healthReport")
    public List<HealthReport> getHealthReport() {
        return healthReport;
    }

    /**
     *
     * @param healthReport The healthReport
     */
    @JsonProperty("healthReport")
    public void setHealthReport(List<HealthReport> healthReport) {
        this.healthReport = healthReport;
    }

    /**
     *
     * @return The inQueue
     */
    @JsonProperty("inQueue")
    public Boolean isInQueue() {
        return inQueue;
    }

    /**
     *
     * @param inQueue The inQueue
     */
    @JsonProperty("inQueue")
    public void setInQueue(Boolean inQueue) {
        this.inQueue = inQueue;
    }

    /**
     *
     * @return The keepDependencies
     */
    @JsonProperty("keepDependencies")
    public Boolean isKeepDependencies() {
        return keepDependencies;
    }

    /**
     *
     * @param keepDependencies The keepDependencies
     */
    @JsonProperty("keepDependencies")
    public void setKeepDependencies(Boolean keepDependencies) {
        this.keepDependencies = keepDependencies;
    }

    /**
     *
     * @return The lastBuild
     */
    @JsonProperty("lastBuild")
    public Build getLastBuild() {
        return lastBuild;
    }

    /**
     *
     * @param lastBuild The lastBuild
     */
    @JsonProperty("lastBuild")
    public void setLastBuild(Build lastBuild) {
        this.lastBuild = lastBuild;
    }

    /**
     *
     * @return The lastCompletedBuild
     */
    @JsonProperty("lastCompletedBuild")
    public Build getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    /**
     *
     * @param lastCompletedBuild The lastCompletedBuild
     */
    @JsonProperty("lastCompletedBuild")
    public void setLastCompletedBuild(Build lastCompletedBuild) {
        this.lastCompletedBuild = lastCompletedBuild;
    }

    /**
     *
     * @return The lastFailedBuild
     */
    @JsonProperty("lastFailedBuild")
    public Build getLastFailedBuild() {
        return lastFailedBuild;
    }

    /**
     *
     * @param lastFailedBuild The lastFailedBuild
     */
    @JsonProperty("lastFailedBuild")
    public void setLastFailedBuild(Build lastFailedBuild) {
        this.lastFailedBuild = lastFailedBuild;
    }

    /**
     *
     * @return The lastStableBuild
     */
    @JsonProperty("lastStableBuild")
    public Object getLastStableBuild() {
        return lastStableBuild;
    }

    /**
     *
     * @param lastStableBuild The lastStableBuild
     */
    @JsonProperty("lastStableBuild")
    public void setLastStableBuild(Object lastStableBuild) {
        this.lastStableBuild = lastStableBuild;
    }

    /**
     *
     * @return The lastSuccessfulBuild
     */
    @JsonProperty("lastSuccessfulBuild")
    public Object getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    /**
     *
     * @param lastSuccessfulBuild The lastSuccessfulBuild
     */
    @JsonProperty("lastSuccessfulBuild")
    public void setLastSuccessfulBuild(Object lastSuccessfulBuild) {
        this.lastSuccessfulBuild = lastSuccessfulBuild;
    }

    /**
     *
     * @return The lastUnstableBuild
     */
    @JsonProperty("lastUnstableBuild")
    public Object getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    /**
     *
     * @param lastUnstableBuild The lastUnstableBuild
     */
    @JsonProperty("lastUnstableBuild")
    public void setLastUnstableBuild(Object lastUnstableBuild) {
        this.lastUnstableBuild = lastUnstableBuild;
    }

    /**
     *
     * @return The lastUnsuccessfulBuild
     */
    @JsonProperty("lastUnsuccessfulBuild")
    public Build getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    /**
     *
     * @param lastUnsuccessfulBuild The lastUnsuccessfulBuild
     */
    @JsonProperty("lastUnsuccessfulBuild")
    public void setLastUnsuccessfulBuild(Build lastUnsuccessfulBuild) {
        this.lastUnsuccessfulBuild = lastUnsuccessfulBuild;
    }

    /**
     *
     * @return The nextBuildNumber
     */
    @JsonProperty("nextBuildNumber")
    public Integer getNextBuildNumber() {
        return nextBuildNumber;
    }

    /**
     *
     * @param nextBuildNumber The nextBuildNumber
     */
    @JsonProperty("nextBuildNumber")
    public void setNextBuildNumber(Integer nextBuildNumber) {
        this.nextBuildNumber = nextBuildNumber;
    }

    /**
     *
     * @return The property
     */
    @JsonProperty("property")
    public List<Property> getProperty() {
        return property;
    }

    /**
     *
     * @param property The property
     */
    @JsonProperty("property")
    public void setProperty(List<Property> property) {
        this.property = property;
    }

    /**
     *
     * @return The queueItem
     */
    @JsonProperty("queueItem")
    public Object getQueueItem() {
        return queueItem;
    }

    /**
     *
     * @param queueItem The queueItem
     */
    @JsonProperty("queueItem")
    public void setQueueItem(Object queueItem) {
        this.queueItem = queueItem;
    }

    /**
     *
     * @return The concurrentBuild
     */
    @JsonProperty("concurrentBuild")
    public Boolean isConcurrentBuild() {
        return concurrentBuild;
    }

    /**
     *
     * @param concurrentBuild The concurrentBuild
     */
    @JsonProperty("concurrentBuild")
    public void setConcurrentBuild(Boolean concurrentBuild) {
        this.concurrentBuild = concurrentBuild;
    }

    /**
     *
     * @return The downstreamProjects
     */
    @JsonProperty("downstreamProjects")
    public List<Object> getDownstreamProjects() {
        return downstreamProjects;
    }

    /**
     *
     * @param downstreamProjects The downstreamProjects
     */
    @JsonProperty("downstreamProjects")
    public void setDownstreamProjects(List<Object> downstreamProjects) {
        this.downstreamProjects = downstreamProjects;
    }

    /**
     *
     * @return The scm
     */
    @JsonProperty("scm")
    public Scm getScm() {
        return scm;
    }

    /**
     *
     * @param scm The scm
     */
    @JsonProperty("scm")
    public void setScm(Scm scm) {
        this.scm = scm;
    }

    /**
     *
     * @return The upstreamProjects
     */
    @JsonProperty("upstreamProjects")
    public List<Object> getUpstreamProjects() {
        return upstreamProjects;
    }

    /**
     *
     * @param upstreamProjects The upstreamProjects
     */
    @JsonProperty("upstreamProjects")
    public void setUpstreamProjects(List<Object> upstreamProjects) {
        this.upstreamProjects = upstreamProjects;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        Integer hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final JenkinsProject other = (JenkinsProject) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
