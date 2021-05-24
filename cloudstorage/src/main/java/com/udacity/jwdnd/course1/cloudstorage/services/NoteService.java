package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Notes uploadNote(String noteTitle, String noteDescription, Integer userId) throws IOException {
        Notes newNote = new Notes(
            noteTitle,
            noteDescription,
            userId
        );
        try {
            noteMapper.save(newNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newNote;
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription, Integer userId) throws IOException {
        Notes updateNote = new Notes(
            noteId,
            noteTitle,
            noteDescription,
            userId
        );
        try {
            noteMapper.update(updateNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notes> getAllNotes(Integer userId) {
        return noteMapper.findNotesByUserId(userId);
    }

    public void deleteNote(Integer noteId) throws IOException {
        try {
            noteMapper.deleteById(noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
