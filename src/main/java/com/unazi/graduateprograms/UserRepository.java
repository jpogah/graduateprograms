package com.unazi.graduateprograms;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {



    Optional<User> findByUserName( String username);

    @Query("SELECT u from User u where LOWER(u.userName) = LOWER(:username)")
    public Optional<User> searchByUserName(@Param("username") String searchTerm);

}
