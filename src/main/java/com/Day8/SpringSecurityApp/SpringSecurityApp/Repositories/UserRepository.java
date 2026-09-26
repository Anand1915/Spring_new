package com.Day8.SpringSecurityApp.SpringSecurityApp.Repositories;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);
}
