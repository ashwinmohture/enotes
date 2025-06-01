package com.enotes.enotes_api.serviceIMPL;

import com.enotes.enotes_api.dto.NotesDto;
import com.enotes.enotes_api.entity.Notes;
import com.enotes.enotes_api.exception.ExistDataException;
import com.enotes.enotes_api.exception.ResourceNotFoundException;
import com.enotes.enotes_api.repository.CategoryRepository;
import com.enotes.enotes_api.repository.NotesRepository;
import com.enotes.enotes_api.service.NotesService;
import com.enotes.enotes_api.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception{
        // validation exists
        validation.NotesValidation(notesDto);

        // check exist title validation
         Boolean existTitle = notesRepo.existsByTitle(notesDto.getTitle().trim());

         if (existTitle){
             throw new ExistDataException("Title Already Exists");
         }

         // check category
        checkCategoryExist(notesDto.getCategory());

        Notes notes = mapper.map(notesDto,Notes.class);
        Notes saveNotes = notesRepo.save(notes);
        if (!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }

        return false;
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
}
