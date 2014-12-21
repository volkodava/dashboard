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
    "shortDescription",
    "userId",
    "userName"
})
public class Cause {

    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("userName")
    private String userName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The shortDescription
     */
    @JsonProperty("shortDescription")
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription The shortDescription
     */
    @JsonProperty("shortDescription")
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     *
     * @return The userId
     */
    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId The userId
     */
    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return The userName
     */
    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName The userName
     */
    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
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
