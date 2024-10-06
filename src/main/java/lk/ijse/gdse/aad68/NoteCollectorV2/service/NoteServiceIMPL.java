package lk.ijse.gdse.aad68.NoteCollectorV2.service;

import jakarta.transaction.Transactional;
import lk.ijse.gdse.aad68.notetaker.dao.NoteDao;
import lk.ijse.gdse.aad68.notetaker.dto.impl.NoteDTO;
import lk.ijse.gdse.aad68.notetaker.entity.NoteEntity;
import lk.ijse.gdse.aad68.notetaker.exception.NoteNotFound;
import lk.ijse.gdse.aad68.notetaker.util.AppUtil;
import lk.ijse.gdse.aad68.notetaker.util.Mapping;
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
    public String saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.createNoteId());
        var noteEntity = mapping.convertToEntity(noteDTO);
        noteDao.save(noteEntity);
        return "Saved successfully in Service layer";
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
    public boolean deleteNote(String noteId) {
//        noteDao.deleteById(noteId);
         if(noteDao.existsById(noteId)){
             noteDao.deleteById(noteId);
             return true;
         }else {
             return false;
         }
    }

    @Override
    public NoteDTO getSelectedNote(String noteId) {
       return mapping.convertToDTO(noteDao.getReferenceById(noteId));
    }

    @Override
    public List<NoteDTO> getAllNotes() {
//        List<NoteEntity> getAllNotes = noteDao.findAll();
//        List<NoteDTO> noteDTOS = mapping.convertToDTO(getAllNotes);
//        return noteDTOS;
        return mapping.convertToDTO(noteDao.findAll());

    }
}
