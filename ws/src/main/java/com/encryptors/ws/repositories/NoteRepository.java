package com.encryptors.ws.repositories;

import com.encryptors.ws.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
