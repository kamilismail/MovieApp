package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.PhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotosEntity, Long> {

}
