package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Credentials uploadCredential(String url,
                                        String username,
                                        String password,
                                        Integer userId) throws IOException {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService encryptionService = new EncryptionService();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        Credentials newCredential = new Credentials(
            url,
            username,
            encryptedPassword,
            userId,
            encodedKey
        );
        try {
            credentialMapper.save(newCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCredential;
    }

    public Credentials updateCredential(Integer credentialId,
                                        String url,
                                        String username,
                                        String password,
                                        Integer userId) throws IOException {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService encryptionService = new EncryptionService();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        Credentials newCredential = new Credentials(
            credentialId,
            url,
            username,
            encryptedPassword,
            userId,
            encodedKey
        );
        try {
            credentialMapper.update(newCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCredential;
    }

    public List<Credentials> getAllCredentials(Integer userId) {
        return credentialMapper.findCredentialsByUserId(userId);
    }

    public void deleteCredential(Integer credentialId) throws IOException {
        try {
            credentialMapper.deleteById(credentialId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Credentials decodePassword(Integer credentialId){
        return credentialMapper.findByCredentialId(credentialId);
    }
}
