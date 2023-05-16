package com.soloproject.community.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileSystemImageService implements ImageService {

    private final Path Location = Paths.get("/Users/eunjeong/Desktop/profileimage");;
    @Override
    public void store(MultipartFile profileImage) {
        try {
            if(profileImage.isEmpty()){
                throw new ImageException("Failed to store empty file.");
            }
            Path destinationFile = this.Location.resolve(
                    Paths.get(profileImage.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.Location.toAbsolutePath())) {
                // This is a security check
                throw new ImageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = profileImage.getInputStream()) {
                log.info("# store coffee image!");
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new ImageException("Failed to store file.", e);
        }
    }
}