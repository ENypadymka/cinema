package com.cinema.api.model.movie;

/**
 * MovieResponse.
 */
public class MovieResponse {

    private String id;
    private String name;
    private String description;
    private String director;

    public MovieResponse(String id, String name, String description, String director) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.director = director;
    }

    public MovieResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
