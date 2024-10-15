package lk.ijse.gdse.aad68.NoteCollectorV2.dao;

import lk.ijse.gdse.aad68.NoteCollectorV2.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao extends JpaRepository<NoteEntity, String> {
    // The method name should work without @Query if you follow the naming convention.
    NoteEntity getNoteEntitiesByNoteId(String noteId);
}

 