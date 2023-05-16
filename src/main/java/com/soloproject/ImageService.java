package com.soloproject;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void store(MultipartFile profileImage);

}


