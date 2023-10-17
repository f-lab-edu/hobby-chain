package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.ImageDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FileServiceImplTest {
    private final FileService fileService;
    private List<MultipartFile> files;

    @Autowired
    public FileServiceImplTest(FileService fileService) {
        this.fileService = fileService;
    }

    @BeforeEach
    void imgSetUp() throws Exception {
        MultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream("/Users/mac/Downloads/test.png"));
        files = new ArrayList<>();
        files.add(file);
    }

    @Test
    void 파일_업로드_테스트(){
        //given
        long postId = 1L;

        //when
        List<ImageDTO> imageDTOS = fileService.uploadFiles(files, postId);

        //then
        Assertions.assertThat(imageDTOS.size()).isEqualTo(1);
    }

}