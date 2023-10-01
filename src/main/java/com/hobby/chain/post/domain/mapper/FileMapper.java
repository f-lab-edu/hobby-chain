package com.hobby.chain.post.domain.mapper;

import com.hobby.chain.post.dto.ImageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    public void uploadImage(ImageDTO imageDTO);
    public void uploadImages(List<ImageDTO> imageDTOS);
}
