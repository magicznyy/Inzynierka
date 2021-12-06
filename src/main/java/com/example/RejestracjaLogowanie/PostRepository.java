package com.example.RejestracjaLogowanie;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long>  {


    Post findPostByidPost(Long id);

    @Override
    List<Post> findAll();
}
