package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction,Long> {


   Reaction deleteByidReaction(Long idReaction);
}
