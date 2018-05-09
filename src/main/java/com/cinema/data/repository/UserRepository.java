package com.cinema.data.repository;

import com.cinema.data.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * UserRepository.
 */
public interface UserRepository extends CrudRepository<User, ObjectId> {

    Optional<User> findByEmailAndPassword(String email, String password);

}
