package com.hobby.chain.post.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class FileServiceImplTest {
    private final FileService fileService;
    private List<MultipartFile> files;

    @Autowired
    public FileServiceImplTest(FileService fileService) {
        this.fileService = fileService;
    }

    @Test
    void 파일_업로드_정상_테스트() throws Exception{
        //given
        files = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            MultipartFile file = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/mac/Downloads/test.png"));
            files.add(file);
        }
        long postId = 1L;

        //when
        fileService.uploadFiles(files, postId);
    }

}