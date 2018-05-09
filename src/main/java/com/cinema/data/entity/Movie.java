package com.cinema.data.entity;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Movie entity.
 */
public class Movie {

    @BsonId
    private ObjectId id;
    private String name;
    private String description;
    private String director;

    public Movie(String name, String description, String director) {
        this.name = name;
        this.description = description;
        this.director = director;
    }

    public Movie() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
