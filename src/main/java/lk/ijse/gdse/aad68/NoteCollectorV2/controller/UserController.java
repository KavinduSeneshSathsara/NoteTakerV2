package lk.ijse.gdse.aad68.NoteCollectorV2.controller;

import lk.ijse.gdse.aad68.NoteCollectorV2.customObj.UserResponse;
import lk.ijse.gdse.aad68.NoteCollectorV2.dto.impl.UserDTO;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.DataPersistFailedException;
import lk.ijse.gdse.aad68.NoteCollectorV2.exception.UserNotFoundException;
import lk.ijse.gdse.aad68.NoteCollectorV2.service.UserService;
import lk.ijse.gdse.aad68.NoteCollectorV2.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    //Save User
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveUser(@RequestPart("firstName") String firstName,
                                         @RequestPart ("lastName") String lastName,
                                         @RequestPart ("email") String email,
                                         @RequestPart ("password") String password,
                                         @RequestPart ("profilePic") String profilePic) {
        try {
            // Convert the profilePic String (assuming it's a file path or base64 string) to byte[]
            byte[] imageByteCollection = profilePic.getBytes(); // Modify this if you get the image differently

            // Convert byte array to Base64 String
            String base64ProfilePic = Base64.getEncoder().encodeToString(imageByteCollection);

            var buildUserDto = new UserDTO();
            buildUserDto.setFirstName(firstName);
            buildUserDto.setLastName(lastName);
            buildUserDto.setEmail(email);
            buildUserDto.setPassword(password);
            buildUserDto.setProfilePic(base64ProfilePic);

            //send to the service layer
            userService.saveUser(buildUserDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistFailedException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("id") String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getSelectedUser(@PathVariable ("id") String userId){
        return userService.getSelectedUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(
             @PathVariable ("id") String id,
             @RequestPart("updateFirstName") String updateFirstName,
             @RequestPart ("updateLastName") String updateLastName,
             @RequestPart ("updateEmail") String updateEmail,
             @RequestPart ("updatePassword") String updatePassword,
             @RequestPart ("updateProfilePic") String updateProfilePic
    ){
        try {
            byte[] imageByteCollection = updateProfilePic.getBytes();

            String base64ProfilePic = Base64.getEncoder().encodeToString(imageByteCollection);

            var updateUser = new UserDTO();
            updateUser.setUserId(id);
            updateUser.setEmail(updateEmail);
            updateUser.setFirstName(updateFirstName);
            updateUser.setLastName(updateLastName);
            updateUser.setPassword(updatePassword);
            updateUser.setProfilePic(base64ProfilePic);

            userService.updateUser(updateUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
