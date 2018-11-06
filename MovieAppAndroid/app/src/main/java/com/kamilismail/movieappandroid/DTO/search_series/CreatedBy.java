package com.kamilismail.movieappandroid.DTO.search_series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cast_id")
    @Expose
    private Integer castId;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreatedBy() {
    }

    /**
     *
     * @param id
     * @param profilePath
     * @param castId
     * @param name
     * @param creditId
     */
    public CreatedBy(Integer id, String name, Integer castId, String creditId, String profilePath) {
        super();
        this.id = id;
        this.name = name;
        this.castId = castId;
        this.creditId = creditId;
        this.profilePath = profilePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCastId() {
        return castId;
    }

    public void setCastId(Integer castId) {
        this.castId = castId;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

}