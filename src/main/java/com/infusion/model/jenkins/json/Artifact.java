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
    "displayPath",
    "fileName",
    "relativePath"
})
public class Artifact {

    @JsonProperty("displayPath")
    private String displayPath;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("relativePath")
    private String relativePath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The displayPath
     */
    @JsonProperty("displayPath")
    public String getDisplayPath() {
        return displayPath;
    }

    /**
     *
     * @param displayPath The displayPath
     */
    @JsonProperty("displayPath")
    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    /**
     *
     * @return The fileName
     */
    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName The fileName
     */
    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return The relativePath
     */
    @JsonProperty("relativePath")
    public String getRelativePath() {
        return relativePath;
    }

    /**
     *
     * @param relativePath The relativePath
     */
    @JsonProperty("relativePath")
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
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
