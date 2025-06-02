package com.enotes.enotes_api.controller;

import com.enotes.enotes_api.dto.NotesDto;
import com.enotes.enotes_api.entity.FileDetails;
import com.enotes.enotes_api.service.NotesService;
import com.enotes.enotes_api.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

//    @PostMapping("/") // before file uploading concept
//    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception{
//       Boolean saveNotes =  notesService.saveNotes(notesDto);
//       if (saveNotes){
//           return CommonUtil.createBuildResponseMessage("Notes Save Successfully", HttpStatus.CREATED);
//       }
//       return CommonUtil.createErrorResponseMessage("Notes Not saved",HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @PostMapping("/") // before file uploading concept
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception{
        Boolean saveNotes =  notesService.saveNotes(notes,file);
        if (saveNotes){
            return CommonUtil.createBuildResponseMessage("Notes Save Successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes Not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] data = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes =  notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes,HttpStatus.OK);
    }


}
