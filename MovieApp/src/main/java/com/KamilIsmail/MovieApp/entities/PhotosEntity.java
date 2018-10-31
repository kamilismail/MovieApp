package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq3", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "photo_id_seq", value = "photo_id_seq"))
@Table(name = "photos", schema = "public", catalog = "d55rc0894f06nc")
public class PhotosEntity {
    private int photoId;
    private String path;
    private Collection<UserEntity> usersByPhotoId;

    @Id
    @GeneratedValue(generator = "seq3")
    @Column(name = "photoid")
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotosEntity that = (PhotosEntity) o;
        return photoId == that.photoId &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(photoId, path);
    }

    public PhotosEntity(String path) {
        this.path = path;
    }

    public PhotosEntity() {
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "photosByPhotoId")
    public Collection<UserEntity> getUsersByPhotoId() {
        return usersByPhotoId;
    }

    public void setUsersByPhotoId(Collection<UserEntity> usersByPhotoId) {
        this.usersByPhotoId = usersByPhotoId;
    }

}
