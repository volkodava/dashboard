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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "actions",
    "artifacts",
    "building",
    "description",
    "duration",
    "estimatedDuration",
    "executor",
    "fullDisplayName",
    "id",
    "keepLog",
    "number",
    "result",
    "timestamp",
    "url",
    "builtOn",
    "changeSet",
    "culprits"
})
public class JenkinsBuildStatus {

    @JsonProperty("actions")
    private List<BuildStatusAction> actions = new ArrayList<BuildStatusAction>();
    @JsonProperty("artifacts")
    private List<Artifact> artifacts = new ArrayList<Artifact>();
    @JsonProperty("building")
    private Boolean building;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("duration")
    private Long duration;
    @JsonProperty("estimatedDuration")
    private Integer estimatedDuration;
    @JsonProperty("executor")
    private Object executor;
    @JsonProperty("fullDisplayName")
    private String fullDisplayName;
    @JsonProperty("id")
    private String id;
    @JsonProperty("keepLog")
    private Boolean keepLog;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("result")
    private String result;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("url")
    private String url;
    @JsonProperty("builtOn")
    private String builtOn;
    @JsonProperty("changeSet")
    private ChangeSet changeSet;
    @JsonProperty("culprits")
    private List<Culprit> culprits = new ArrayList<Culprit>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The actions
     */
    @JsonProperty("actions")
    public List<BuildStatusAction> getActions() {
        return actions;
    }

    /**
     *
     * @param actions The actions
     */
    @JsonProperty("actions")
    public void setActions(List<BuildStatusAction> actions) {
        this.actions = actions;
    }

    /**
     *
     * @return The artifacts
     */
    @JsonProperty("artifacts")
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    /**
     *
     * @param artifacts The artifacts
     */
    @JsonProperty("artifacts")
    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    /**
     *
     * @return The building
     */
    @JsonProperty("building")
    public Boolean isBuilding() {
        return building;
    }

    /**
     *
     * @param building The building
     */
    @JsonProperty("building")
    public void setBuilding(Boolean building) {
        this.building = building;
    }

    /**
     *
     * @return The description
     */
    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    /**
     *
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    /**
     *
     * @return The duration
     */
    @JsonProperty("duration")
    public Long getDuration() {
        return duration;
    }

    /**
     *
     * @param duration The duration
     */
    @JsonProperty("duration")
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     *
     * @return The estimatedDuration
     */
    @JsonProperty("estimatedDuration")
    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    /**
     *
     * @param estimatedDuration The estimatedDuration
     */
    @JsonProperty("estimatedDuration")
    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    /**
     *
     * @return The executor
     */
    @JsonProperty("executor")
    public Object getExecutor() {
        return executor;
    }

    /**
     *
     * @param executor The executor
     */
    @JsonProperty("executor")
    public void setExecutor(Object executor) {
        this.executor = executor;
    }

    /**
     *
     * @return The fullDisplayName
     */
    @JsonProperty("fullDisplayName")
    public String getFullDisplayName() {
        return fullDisplayName;
    }

    /**
     *
     * @param fullDisplayName The fullDisplayName
     */
    @JsonProperty("fullDisplayName")
    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    /**
     *
     * @return The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return The keepLog
     */
    @JsonProperty("keepLog")
    public Boolean isKeepLog() {
        return keepLog;
    }

    /**
     *
     * @param keepLog The keepLog
     */
    @JsonProperty("keepLog")
    public void setKeepLog(Boolean keepLog) {
        this.keepLog = keepLog;
    }

    /**
     *
     * @return The number
     */
    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    /**
     *
     * @param number The number
     */
    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     *
     * @return The result
     */
    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result The result
     */
    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return The timestamp
     */
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
     * @return The builtOn
     */
    @JsonProperty("builtOn")
    public String getBuiltOn() {
        return builtOn;
    }

    /**
     *
     * @param builtOn The builtOn
     */
    @JsonProperty("builtOn")
    public void setBuiltOn(String builtOn) {
        this.builtOn = builtOn;
    }

    /**
     *
     * @return The changeSet
     */
    @JsonProperty("changeSet")
    public ChangeSet getChangeSet() {
        return changeSet;
    }

    /**
     *
     * @param changeSet The changeSet
     */
    @JsonProperty("changeSet")
    public void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    /**
     *
     * @return The culprits
     */
    @JsonProperty("culprits")
    public List<Culprit> getCulprits() {
        return culprits;
    }

    /**
     *
     * @param culprits The culprits
     */
    @JsonProperty("culprits")
    public void setCulprits(List<Culprit> culprits) {
        this.culprits = culprits;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
