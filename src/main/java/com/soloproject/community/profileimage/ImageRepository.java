package com.soloproject.community.profileimage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProfileImage,Long> {
}
