package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface UserRepository extends JpaRepository<User, String> {

   User findUserByLogin(String name);



}
