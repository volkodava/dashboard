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
    "parameterDefinitions"
})
public class Action {

    @JsonProperty("parameterDefinitions")
    private List<ParameterDefinition> parameterDefinitions = new ArrayList<ParameterDefinition>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The parameterDefinitions
     */
    @JsonProperty("parameterDefinitions")
    public List<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }

    /**
     *
     * @param parameterDefinitions The parameterDefinitions
     */
    @JsonProperty("parameterDefinitions")
    public void setParameterDefinitions(List<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
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
