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
    "description",
    "iconClassName",
    "iconUrl",
    "score"
})
public class HealthReport {

    @JsonProperty("description")
    private String description;
    @JsonProperty("iconClassName")
    private String iconClassName;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("score")
    private Integer score;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * @return The iconClassName
     */
    @JsonProperty("iconClassName")
    public String getIconClassName() {
        return iconClassName;
    }

    /**
     *
     * @param iconClassName The iconClassName
     */
    @JsonProperty("iconClassName")
    public void setIconClassName(String iconClassName) {
        this.iconClassName = iconClassName;
    }

    /**
     *
     * @return The iconUrl
     */
    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     *
     * @param iconUrl The iconUrl
     */
    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     *
     * @return The score
     */
    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    /**
     *
     * @param score The score
     */
    @JsonProperty("score")
    public void setScore(Integer score) {
        this.score = score;
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
