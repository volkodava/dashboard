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
    "defaultParameterValue",
    "description",
    "name",
    "type",
    "choices"
})
public class ParameterDefinition {

    @JsonProperty("defaultParameterValue")
    private DefaultParameterValue defaultParameterValue;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("choices")
    private List<String> choices = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The defaultParameterValue
     */
    @JsonProperty("defaultParameterValue")
    public DefaultParameterValue getDefaultParameterValue() {
        return defaultParameterValue;
    }

    /**
     *
     * @param defaultParameterValue The defaultParameterValue
     */
    @JsonProperty("defaultParameterValue")
    public void setDefaultParameterValue(DefaultParameterValue defaultParameterValue) {
        this.defaultParameterValue = defaultParameterValue;
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
     * @return The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return The choices
     */
    @JsonProperty("choices")
    public List<String> getChoices() {
        return choices;
    }

    /**
     *
     * @param choices The choices
     */
    @JsonProperty("choices")
    public void setChoices(List<String> choices) {
        this.choices = choices;
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
