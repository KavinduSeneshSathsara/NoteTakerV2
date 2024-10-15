package lk.ijse.gdse.aad68.NoteCollectorV2.service;

import jakarta.transaction.Transactional;
import lk.ijse.gdse.aad68.NoteCollectorV2.customObj.NoteErrorResponse;
import lk.ijse.gdse.aad68.NoteCollectorV2.customObj.NoteResponse;
import lk.ijse.gdse.aad68.NoteCollectorV2.dao.NoteDao;
import lk.ijse.gdse.aad68.NoteCollectorV2.dto.impl.NoteDTO;
import lk.ijse.gdse.aad68.NoteCollectorV2.entity.NoteEntity;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.DataPersistFailedException;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.NoteNotFound;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.NoteNotFoundException;
import lk.ijse.gdse.aad68.NoteCollectorV2.util.AppUtil;
import lk.ijse.gdse.aad68.NoteCollectorV2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public  class NoteServiceIMPL implements NoteService {
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.createNoteId());
        NoteEntity savedNote = noteDao.save(mapping.convertToEntity(noteDTO));
        if (savedNote == null && savedNote.getNoteId() == null){
            throw new DataPersistFailedException("Can't save the note!");
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO incomeNoteDTO) {
        Optional<NoteEntity> tmpNoteEntity= noteDao.findById(noteId);
        if(!tmpNoteEntity.isPresent()){
            throw new NoteNotFound("Note not found");
        }else {
            tmpNoteEntity.get().setNoteDesc(incomeNoteDTO.getNoteDesc());
            tmpNoteEntity.get().setNoteTitle(incomeNoteDTO.getNoteTitle());
            tmpNoteEntity.get().setCreateDate(incomeNoteDTO.getCreateDate());
            tmpNoteEntity.get().setPriorityLevel(incomeNoteDTO.getPriorityLevel());
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<NoteEntity> selectedNote = noteDao.findById(noteId);
         if(!selectedNote.isPresent()){
             throw new NoteNotFoundException("Note not found");
         }else {
             noteDao.deleteById(noteId);
         }
    }

    @Override
    public NoteDTO getSelectedNote(String noteId) {
        NoteEntity noteEntity = noteDao.getNoteEntitiesByNoteId(noteId);
        if (noteEntity != null) {
            // Assuming mapping.convertToDTO returns NoteDTO
            return (NoteDTO) mapping.convertToDTO(noteEntity);
        } else {
            // If the note is not found, handle it appropriately.
            return null; // Or throw an exception, or return an error response if needed.
        }
    }



    @Override
    public List<NoteDTO> getAllNotes() {
        return mapping.convertToDTO(noteDao.findAll());

    }
}
