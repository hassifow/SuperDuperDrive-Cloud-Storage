package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialController {
    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;

    public CredentialController(NoteService noteService, UserService userService,
                                FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential-upload")
    public String uploadCredential(@RequestParam("credentialId") Integer credentialId,
                                   @RequestParam("url") String credentialUrl,
                                   @RequestParam("username") String credentialUsername,
                                   @RequestParam("password") String credentialPassword,
                                   Authentication auth,
                                   Model model) throws IOException {
        String credentialEditError = null;
        String credentialUploadError = null;
        User user = this.userService.getUser(auth.getName());
        if (credentialId == null) {
            try {
                this.credentialService.uploadCredential(credentialUrl,
                                                        credentialUsername,
                                                        credentialPassword,
                                                        user.getUserid());
                model.addAttribute("credentialUploadSuccess", "Credential successfully uploaded.");
            } catch(Exception e) {
                credentialUploadError = e.toString();
                model.addAttribute("credentialError", credentialUploadError);
            }
        } else {
            try {
                this.credentialService.updateCredential(credentialId,
                                                        credentialUrl,
                                                        credentialUsername,
                                                        credentialPassword,
                                                        user.getUserid());
                model.addAttribute("credentialEditSuccess", "Credential successfully updated.");
            } catch(Exception e) {
                credentialEditError = e.toString();
                model.addAttribute("credentialError", credentialEditError);
            }

        }
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserid()));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }

    @GetMapping(value = "/decode-password")
    @ResponseBody
    public Map<String, String> decodePassword(@RequestParam Integer credentialId){
        Credentials credential = credentialService.decodePassword(credentialId);
        String encryptedPassword = credential.getPassword();
        String encodedKey = credential.getKey();
        EncryptionService encryptionService = new EncryptionService();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        Map<String, String> response = new HashMap<>();
        response.put("decryptedPassword", decryptedPassword);
        return response;
    }

    @RequestMapping("/credential-delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId,
                                   Authentication auth,
                                   Model model) throws IOException {
        String credentialDeleteError = null;
        try {
            this.credentialService.deleteCredential(credentialId);
            model.addAttribute("credentialDeleteSuccess", "Credential successfully deleted.");
        } catch (Exception e) {
            credentialDeleteError = e.toString();
            model.addAttribute("credentialError", credentialDeleteError);
        }
        User user = this.userService.getUser(auth.getName());

        model.addAttribute("files", this.fileService.getAllFiles(user.getUserid()));
        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserid()));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }
}
