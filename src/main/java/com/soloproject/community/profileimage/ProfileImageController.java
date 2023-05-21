package com.soloproject.community.profileimage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/profile-image")
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageService profileImageService;

    @PostMapping("/upload/{member-id}")
    public String uploadFile(@PathVariable("member-id") long memberId,
                             @RequestParam("image") MultipartFile profileImage) throws IOException {

        return profileImageService.upload(profileImage, memberId, "profile-image");
    }




    //수정,삭제...
}
