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
    "parameters",
    "causes",
    "buildsByBranchName",
    "lastBuiltRevision",
    "remoteUrls",
    "scmName",
    "failCount",
    "skipCount",
    "totalCount",
    "urlName"
})
public class BuildStatusAction {

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonProperty("parameters")
    private List<Parameter> parameters = new ArrayList<Parameter>();
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonProperty("causes")
    private List<Cause> causes = new ArrayList<Cause>();
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonProperty("buildsByBranchName")
    private Map<String, Object> buildsByBranchName;
    @JsonProperty("lastBuiltRevision")
    private LastBuiltRevision lastBuiltRevision;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonProperty("remoteUrls")
    private List<String> remoteUrls = new ArrayList<String>();
    @JsonProperty("scmName")
    private String scmName;
    @JsonProperty("failCount")
    private Integer failCount;
    @JsonProperty("skipCount")
    private Integer skipCount;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("urlName")
    private String urlName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The parameters
     */
    @JsonProperty("parameters")
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameters The parameters
     */
    @JsonProperty("parameters")
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     *
     * @return The causes
     */
    @JsonProperty("causes")
    public List<Cause> getCauses() {
        return causes;
    }

    /**
     *
     * @param causes The causes
     */
    @JsonProperty("causes")
    public void setCauses(List<Cause> causes) {
        this.causes = causes;
    }

    /**
     *
     * @return The buildsByBranchName
     */
    @JsonProperty("buildsByBranchName")
    public Map<String, Object> getBuildsByBranchName() {
        return buildsByBranchName;
    }

    /**
     *
     * @param buildsByBranchName The buildsByBranchName
     */
    @JsonProperty("buildsByBranchName")
    public void setBuildsByBranchName(Map<String, Object> buildsByBranchName) {
        this.buildsByBranchName = buildsByBranchName;
    }

    /**
     *
     * @return The lastBuiltRevision
     */
    @JsonProperty("lastBuiltRevision")
    public LastBuiltRevision getLastBuiltRevision() {
        return lastBuiltRevision;
    }

    /**
     *
     * @param lastBuiltRevision The lastBuiltRevision
     */
    @JsonProperty("lastBuiltRevision")
    public void setLastBuiltRevision(LastBuiltRevision lastBuiltRevision) {
        this.lastBuiltRevision = lastBuiltRevision;
    }

    /**
     *
     * @return The remoteUrls
     */
    @JsonProperty("remoteUrls")
    public List<String> getRemoteUrls() {
        return remoteUrls;
    }

    /**
     *
     * @param remoteUrls The remoteUrls
     */
    @JsonProperty("remoteUrls")
    public void setRemoteUrls(List<String> remoteUrls) {
        this.remoteUrls = remoteUrls;
    }

    /**
     *
     * @return The scmName
     */
    @JsonProperty("scmName")
    public String getScmName() {
        return scmName;
    }

    /**
     *
     * @param scmName The scmName
     */
    @JsonProperty("scmName")
    public void setScmName(String scmName) {
        this.scmName = scmName;
    }

    /**
     *
     * @return The failCount
     */
    @JsonProperty("failCount")
    public Integer getFailCount() {
        return failCount;
    }

    /**
     *
     * @param failCount The failCount
     */
    @JsonProperty("failCount")
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     *
     * @return The skipCount
     */
    @JsonProperty("skipCount")
    public Integer getSkipCount() {
        return skipCount;
    }

    /**
     *
     * @param skipCount The skipCount
     */
    @JsonProperty("skipCount")
    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    /**
     *
     * @return The totalCount
     */
    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param totalCount The totalCount
     */
    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return The urlName
     */
    @JsonProperty("urlName")
    public String getUrlName() {
        return urlName;
    }

    /**
     *
     * @param urlName The urlName
     */
    @JsonProperty("urlName")
    public void setUrlName(String urlName) {
        this.urlName = urlName;
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
