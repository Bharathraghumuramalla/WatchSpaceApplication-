package com.bharath.watchSpace.ServicePackage;


import com.bharath.watchSpace.DTO.GetTitlesDTO;
import com.bharath.watchSpace.DTO.PostTitlesDTO;
import com.bharath.watchSpace.Entities.Title;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
import com.bharath.watchSpace.RepositoryPackage.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService
{
    @Autowired
    TitleRepository titleRepository;

    public void createTitle(PostTitlesDTO dto) {

        Title title = new Title();

        title.setName(dto.getName());
        title.setDescription(dto.getDescription());
        title.setGenre(dto.getGenre());
        title.setDurationInMinutes(dto.getDurationInMinutes());

        titleRepository.save(title);
    }

    public List<GetTitlesDTO> getTitle()
    {
        List<GetTitlesDTO> result = new ArrayList<>();

        List<Title> title = titleRepository.findAll();
        for(Title t : title)
        {
            result.add(convertToDTO(t));
        }
        return result;
    }

    public GetTitlesDTO getTitleById(int id)
    {
        Title t = titleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Title not found"));
        return convertToDTO(t);
    }

    private GetTitlesDTO convertToDTO(Title t) {

        GetTitlesDTO dto = new GetTitlesDTO();

        dto.setTitleId(t.getTitleId());
        dto.setName(t.getName());
        dto.setGenre(t.getGenre());
        dto.setDurationInMinutes(t.getDurationInMinutes());
        dto.setDescription(t.getDescription());

        return dto;
    }
}
