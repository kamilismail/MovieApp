package com.kamilismail.movieappandroid.DTO.search_series;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSeriesDTO {

    @SerializedName("results")
    @Expose
    private ArrayList<SeriesResult> results = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchSeriesDTO() {
    }

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public SearchSeriesDTO(ArrayList<SeriesResult> results, Integer page, Integer totalPages, Integer totalResults) {
        super();
        this.results = results;
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public ArrayList<SeriesResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SeriesResult> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}