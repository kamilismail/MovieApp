package com.kamilismail.movieappandroid.DTO.search_person;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonResult {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("personType")
    @Expose
    private String personType;
    @SerializedName("department")
    @Expose
    private Object department;
    @SerializedName("job")
    @Expose
    private Object job;
    @SerializedName("character")
    @Expose
    private Object character;
    @SerializedName("order")
    @Expose
    private Integer order;
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
    private Object creditId;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("also_known_as")
    @Expose
    private List<Object> alsoKnownAs = null;
    @SerializedName("biography")
    @Expose
    private Object biography;
    @SerializedName("birthday")
    @Expose
    private Object birthday;
    @SerializedName("deathday")
    @Expose
    private Object deathday;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("place_of_birth")
    @Expose
    private Object placeOfBirth;
    @SerializedName("imdb_id")
    @Expose
    private Object imdbId;
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * No args constructor for use in serialization
     */
    public PersonResult() {
    }

    /**
     * @param birthday
     * @param profilePath
     * @param placeOfBirth
     * @param alsoKnownAs
     * @param department
     * @param job
     * @param adult
     * @param creditId
     * @param imdbId
     * @param biography
     * @param homepage
     * @param mediaType
     * @param character
     * @param id
     * @param order
     * @param castId
     * @param deathday
     * @param name
     * @param personType
     * @param popularity
     */
    public PersonResult(String mediaType, String personType, Object department, Object job, Object character, Integer order, Integer id, String name, Integer castId, Object creditId, String profilePath, Boolean adult, List<Object> alsoKnownAs, Object biography, Object birthday, Object deathday, Object homepage, Object placeOfBirth, Object imdbId, Double popularity) {
        super();
        this.mediaType = mediaType;
        this.personType = personType;
        this.department = department;
        this.job = job;
        this.character = character;
        this.order = order;
        this.id = id;
        this.name = name;
        this.castId = castId;
        this.creditId = creditId;
        this.profilePath = profilePath;
        this.adult = adult;
        this.alsoKnownAs = alsoKnownAs;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.homepage = homepage;
        this.placeOfBirth = placeOfBirth;
        this.imdbId = imdbId;
        this.popularity = popularity;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public Object getJob() {
        return job;
    }

    public void setJob(Object job) {
        this.job = job;
    }

    public Object getCharacter() {
        return character;
    }

    public void setCharacter(Object character) {
        this.character = character;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Object getCreditId() {
        return creditId;
    }

    public void setCreditId(Object creditId) {
        this.creditId = creditId;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public List<Object> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<Object> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public Object getBiography() {
        return biography;
    }

    public void setBiography(Object biography) {
        this.biography = biography;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Object getDeathday() {
        return deathday;
    }

    public void setDeathday(Object deathday) {
        this.deathday = deathday;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    public Object getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(Object placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Object getImdbId() {
        return imdbId;
    }

    public void setImdbId(Object imdbId) {
        this.imdbId = imdbId;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
}