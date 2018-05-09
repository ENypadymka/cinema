package com.cinema.data.repository;

import com.cinema.data.entity.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

/**
 * MovieRepository.
 */
public interface MovieRepository extends CrudRepository<Movie, ObjectId> {


}
