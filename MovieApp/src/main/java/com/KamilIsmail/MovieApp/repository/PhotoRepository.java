package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.PhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql z tabelą photos.
 */
public interface PhotoRepository extends JpaRepository<PhotosEntity, Long> {

    /**
     * Zapytanie zwracające pozycję ze względu na id.
     * @param photoID
     * @return
     */
    PhotosEntity findPhotosEntityByPhotoId(int photoID);
}
