package com.enotes.enotes_api.service;

import com.enotes.enotes_api.dto.NotesDto;
import com.enotes.enotes_api.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
    public List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;
}
