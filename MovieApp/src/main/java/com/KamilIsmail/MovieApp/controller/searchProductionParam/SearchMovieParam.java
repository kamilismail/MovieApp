package com.KamilIsmail.MovieApp.controller.searchProductionParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SearchMovieParam {
    @NotNull
    @NotEmpty
    private String production;

    public SearchMovieParam() {
    }

    @JsonCreator
    public SearchMovieParam(@JsonProperty("production") String production) {
        this.production = production;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }
}
