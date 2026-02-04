package com.example.cloudmediauploader.repository;

import com.example.cloudmediauploader.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    List<ProfileImage> findAllByOrderByUploadedAtDesc();
}
