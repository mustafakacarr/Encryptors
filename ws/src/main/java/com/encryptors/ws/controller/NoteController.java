package com.encryptors.ws.controller;

import com.encryptors.ws.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createEncryptedNote(@RequestParam String message) {
        return noteService.createEncryptedNote(message);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity getNoteById(@PathVariable long noteId, @RequestParam String key) {
        return noteService.getNoteById(noteId, key);
    }
}
