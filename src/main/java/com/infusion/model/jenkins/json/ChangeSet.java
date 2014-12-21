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
    "items",
    "kind"
})
public class ChangeSet {

    @JsonProperty("items")
    private List<Object> items = new ArrayList<Object>();
    @JsonProperty("kind")
    private String kind;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The items
     */
    @JsonProperty("items")
    public List<Object> getItems() {
        return items;
    }

    /**
     *
     * @param items The items
     */
    @JsonProperty("items")
    public void setItems(List<Object> items) {
        this.items = items;
    }

    /**
     *
     * @return The kind
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind The kind
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
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
