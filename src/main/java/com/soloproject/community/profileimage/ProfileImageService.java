package com.soloproject.community.profileimage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.soloproject.community.member.entity.Member;
import com.soloproject.community.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageService {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final ImageRepository imageRepository;
    private final MemberService memberService;

    public String upload(MultipartFile profileImage, long memberId, String dirName) throws IOException {
//        validateFileExists(profileImage);
//        파일 이름 다듬기
//        파일 사이즈 최적화
        File uploadFile = convert(profileImage)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return s3upload(uploadFile, dirName, memberId);
    }

    private String s3upload(File uploadFile, String dirName, long memberId) {

        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        Member findMember = memberService.findMember(memberId);
        findMember.setProfileImageURL(uploadImageUrl);
        ProfileImage image = new ProfileImage();
        image.setFileUrl(uploadImageUrl);
        image.setFileName(fileName);
        image.setMember(findMember);
        imageRepository.save(image);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    //multipartfile을 서버에 저장하는코드
    private Optional<File> convert(MultipartFile profileImage) throws IOException {
        File convertFile = new File(profileImage.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(profileImage.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    //로컬에 저장된 파일 삭제하는 메소드
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

   /* private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ImageException("이미지 파일이 없습니다.");
        }
    }*/

    public Optional<ProfileImage> findImage(long memberId){
        return imageRepository.findById(memberId);
    }
}


