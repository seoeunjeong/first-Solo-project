package com.soloproject.community.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProfileImageUploadController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("dirName") String dirName) throws IOException {

        return awsS3Service.upload(file,dirName);
    }
}
