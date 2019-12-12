package mflix.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractSession {
    @JsonProperty("_id")
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
