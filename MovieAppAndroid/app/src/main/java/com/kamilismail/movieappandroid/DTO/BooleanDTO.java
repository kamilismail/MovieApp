package com.kamilismail.movieappandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BooleanDTO {

    @SerializedName("result")
    @Expose
    private Boolean result;

    /**
     * No args constructor for use in serialization
     */
    public BooleanDTO() {
    }

    /**
     * @param result
     */
    public BooleanDTO(Boolean result) {
        super();
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
