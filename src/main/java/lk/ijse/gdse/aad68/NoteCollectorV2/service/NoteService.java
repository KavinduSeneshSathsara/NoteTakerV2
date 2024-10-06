package lk.ijse.gdse.aad68.NoteCollectorV2.service;

import lk.ijse.gdse.aad68.notetaker.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    String saveNote(NoteDTO noteDTO);
    void updateNote(String noteId,NoteDTO noteDTO);
    boolean deleteNote(String noteId);
    NoteDTO getSelectedNote(String noteId);
    List<NoteDTO> getAllNotes();
}
