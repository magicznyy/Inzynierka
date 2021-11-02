package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    User findUserByName(String name);

}
