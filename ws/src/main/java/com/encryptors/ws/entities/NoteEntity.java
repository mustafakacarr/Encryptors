package com.encryptors.ws.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "notes")
@Entity
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;

}
