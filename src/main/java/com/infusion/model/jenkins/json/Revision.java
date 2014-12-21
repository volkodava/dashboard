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
    "SHA1",
    "branch"
})
public class Revision {

    @JsonProperty("SHA1")
    private String SHA1;
    @JsonProperty("branch")
    private List<Branch> branch = new ArrayList<Branch>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The SHA1
     */
    @JsonProperty("SHA1")
    public String getSHA1() {
        return SHA1;
    }

    /**
     *
     * @param SHA1 The SHA1
     */
    @JsonProperty("SHA1")
    public void setSHA1(String SHA1) {
        this.SHA1 = SHA1;
    }

    /**
     *
     * @return The branch
     */
    @JsonProperty("branch")
    public List<Branch> getBranch() {
        return branch;
    }

    /**
     *
     * @param branch The branch
     */
    @JsonProperty("branch")
    public void setBranch(List<Branch> branch) {
        this.branch = branch;
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
