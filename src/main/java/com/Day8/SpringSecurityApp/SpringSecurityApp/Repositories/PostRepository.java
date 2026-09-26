package com.Day8.SpringSecurityApp.SpringSecurityApp.Repositories;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.PostEntity;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity ,Long> {
}
