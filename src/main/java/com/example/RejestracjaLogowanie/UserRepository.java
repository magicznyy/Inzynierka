package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

   User findUserByLogin(String name);

   @Override
   List<User> findAll();

   User getById(Integer id);
}
