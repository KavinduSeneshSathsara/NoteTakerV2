package lk.ijse.gdse.aad68.NoteCollectorV2.controller;

import lk.ijse.gdse.aad68.NoteCollectorV2.dto.impl.NoteDTO;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.NoteNotFound;
import lk.ijse.gdse.aad68.NoteCollectorV2.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private final NoteService noteService;
    @GetMapping("health")
    public String healthCheck() {
        return "Note taker is running";
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNote(@RequestBody NoteDTO note) {
        var saveData = noteService.saveNote(note);
        return ResponseEntity.ok(saveData);
    }
    @GetMapping(value = "allnotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes();
    }
    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteDTO getNote(@PathVariable ("noteId") String noteId)  {
        return noteService.getSelectedNote(noteId);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{noteId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateNote(@PathVariable ("noteId") String noteId, @RequestBody NoteDTO note) {
        try {
            noteService.updateNote(noteId, note);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFound e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value ="/{noteId}" )
    public ResponseEntity<String> deleteNote(@PathVariable ("noteId") String noteId) {
       return noteService.deleteNote(noteId) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
