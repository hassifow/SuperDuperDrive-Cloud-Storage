package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public void uploadFile(MultipartFile multipartFile, Integer userId) throws Exception {
        Files newFile = new Files(
            multipartFile.getOriginalFilename(),
            multipartFile.getContentType(),
            multipartFile.getSize(),
            multipartFile.getBytes(),
            userId
        );

        try {

            fileMapper.save(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Files> getAllFiles(Integer userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    public Files getFileById(Integer fileId) {
        return fileMapper.findById(fileId);
    }

    public void deleteFile(Integer fileId) throws IOException {
        try {
            fileMapper.deleteById(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFileNameAvailable(MultipartFile multipartFile, Integer userId) {
        Boolean isFileNameAvailable = true;
        List <Files> files = fileMapper.findFilesByUserId(userId);
        for (int i = 0; i < files.size(); i++){
            Files currFile = files.get(i);
            if (currFile.getFileName().equals(multipartFile.getOriginalFilename())) {
                isFileNameAvailable = false;
                break;
            }
        }
        return isFileNameAvailable;
    }
}
