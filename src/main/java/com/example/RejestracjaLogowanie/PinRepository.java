package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {

    List<Pin> findPinsByUser(User user);
}
