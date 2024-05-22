package com.encryptors.ws.services;

import com.encryptors.ws.entities.NoteEntity;
import com.encryptors.ws.repositories.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final EncryptionService encryptionService;

    public NoteService(NoteRepository noteRepository, EncryptionService encryptionService) {
        this.noteRepository = noteRepository;
        this.encryptionService = encryptionService;
    }

    public ResponseEntity getNoteById(long noteId, String key) {
        NoteEntity note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        return decryptMessage(note.getMessage(), key);

    }

    public ResponseEntity<Map<String, String>> createEncryptedNote(String message) {
        try {
            NoteEntity noteEntity = new NoteEntity();
            String key = encryptionService.generateKey();
            String encryptedMessage = encryptionService.encrypt(message, key);
            String encodedKey = encryptionService.encodeKeyForHttpHeader(key);
            Map<String, String> response = new HashMap<>();
            response.put("encryptedMessage", encryptedMessage);
            response.put("X-Timestamp", encodedKey);

            noteEntity.setMessage(encryptedMessage);
            noteRepository.save(noteEntity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity decryptMessage(String encryptedMessage, String key) {
        try {
            key=encryptionService.decodeKeyForBase64(key);
            System.out.println(key);
            String decryptedMessage = encryptionService.decrypt(encryptedMessage, key);
            Map<String, String> response = new HashMap<>();
            response.put("decryptedMessage", decryptedMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}

