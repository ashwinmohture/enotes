package com.enotes.enotes_api.serviceIMPL;

import com.enotes.enotes_api.dto.NotesDto;
import com.enotes.enotes_api.entity.FileDetails;
import com.enotes.enotes_api.entity.Notes;
import com.enotes.enotes_api.exception.ExistDataException;
import com.enotes.enotes_api.exception.ResourceNotFoundException;
import com.enotes.enotes_api.repository.CategoryRepository;
import com.enotes.enotes_api.repository.FileRepository;
import com.enotes.enotes_api.repository.NotesRepository;
import com.enotes.enotes_api.service.NotesService;
import com.enotes.enotes_api.util.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Validation validation;

    @Autowired
    private CategoryRepository categoryRepo;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Autowired
    private FileRepository fileRepo;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception{

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes,NotesDto.class);

        // validation exists
        validation.NotesValidation(notesDto);

        // check exist title validation
         Boolean existTitle = notesRepo.existsByTitle(notesDto.getTitle().trim());

         if (existTitle){
             throw new ExistDataException("Title Already Exists");
         }

         // check category
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto,Notes.class);
        FileDetails fileDtls = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDtls)){
            notesMap.setFileDetails(fileDtls);
        }else{
            notesMap.setFileDetails(null);
        }
        Notes saveNotes = notesRepo.save(notesMap);
        if (!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }

        return false;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file)  && !file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);
//            List<String> extensionAllow = Arrays.asList(".pdf", ".png", ".xlsx" , ".csv");
//            if (!extensionAllow.contains(extension)){
//                throw new IllegalArgumentException("Invalid file format ! upload valid file pdf, .png, .xlsx");
//            }



            String rndString = UUID.randomUUID().toString();

            String uploadfileName = rndString+"."+extension;



            File saveFile = new File(uploadpath);
            if (!saveFile.exists()){
                saveFile.mkdir();
            }

            // path = enotes_api/notes/java.pdf
            String storePath = uploadpath.concat(uploadfileName);

            // upload file
            long uplaod = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (uplaod != 0){
                FileDetails fileDtls = new FileDetails();
                FileDetails saveFileDtls =  fileRepo.save(fileDtls);
                fileDtls.setOriginalFileName(originalFileName);
                fileDtls.setDisplayFileName(getDisplayName(originalFileName));
                fileDtls.setUploadFileName(uploadfileName);
                fileDtls.setFileSize(file.getSize());
                fileDtls.setPath(storePath);

                return saveFileDtls;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        //java_programing_tutorial.pdf
        //java_prog.pdf
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);
        if (fileName.length() > 10){
            fileName = fileName.substring(0,9);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category is invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepo.findAll().stream()
                .map(notes -> mapper.map(notes,NotesDto.class))
                .toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {

        InputStream io = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(io);
    }

    // downloads files details
    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        FileDetails fileDetails = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
        InputStream io = new FileInputStream(fileDetails.getPath());
        return fileDetails;
    }
}
