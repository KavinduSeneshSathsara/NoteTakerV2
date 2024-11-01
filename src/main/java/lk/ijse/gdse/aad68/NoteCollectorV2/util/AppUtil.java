package lk.ijse.gdse.aad68.NoteCollectorV2.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    public static String createNoteId(){
        return "NOTE-"+UUID.randomUUID();
    }
    public static String createUserId(){
        return "USER-"+UUID.randomUUID();
    }
    public static String toBase64ProfilePic(MultipartFile profilePic){
        byte[] profilePicBytes = null;
        try {
            profilePicBytes = profilePic.getBytes();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(profilePicBytes);
    }
}
