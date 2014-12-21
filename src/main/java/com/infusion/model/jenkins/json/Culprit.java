package com.infusion.model.jenkins.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "absoluteUrl",
    "fullName"
})
public class Culprit {

    @JsonProperty("absoluteUrl")
    private String absoluteUrl;
    @JsonProperty("fullName")
    private String fullName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The absoluteUrl
     */
    @JsonProperty("absoluteUrl")
    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    /**
     *
     * @param absoluteUrl The absoluteUrl
     */
    @JsonProperty("absoluteUrl")
    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    /**
     *
     * @return The fullName
     */
    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName The fullName
     */
    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
