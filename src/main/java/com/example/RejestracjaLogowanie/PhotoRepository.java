package com.example.RejestracjaLogowanie;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository  extends JpaRepository<Photo, Long> {


    @Override
    List<Photo> findAll();
}
