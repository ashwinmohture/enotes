package com.enotes.enotes_api.repository;

import com.enotes.enotes_api.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    Boolean existsByTitle(String title);
}
