package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq7", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "org.hibernate.id.enhanced.SequenceStyleGenerator", value = "user_id_seq"))
@Table(name = "users", schema = "public", catalog = "d55rc0894f06nc")
public class UserEntity {
    private int userId;
    private String username;
    private String password;
    private String notificationId;
    private int photoId;
    private String role;
    private PhotosEntity photosByPhotoId;
    private Integer userSocialId;
    private UserSocialEntity userSocialByUserSocialId;
    private Collection<FavouritesEntity> favouritesByUserId;
    private Collection<RatingsEntity> ratingsByUserId;
    private Collection<RemindersEntity> remindersByUserId;
    private Collection<WanttowatchEntity> wanttowatchesByUserId;

    public UserEntity() {
    }

    @Id
    @GeneratedValue(generator = "seq7")
    @Column(name = "userid")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "notificationid")
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @Basic
    @Column(name = "user_social_id", insertable = false, updatable = false)
    public Integer getUserSocialId() {
        return userSocialId;
    }

    public void setUserSocialId(Integer userSocialId) {
        this.userSocialId = userSocialId;
    }

    public UserEntity(String username, String password, String role, PhotosEntity photosByPhotoId, Integer userSocialId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.photosByPhotoId = photosByPhotoId;
        this.userSocialId = userSocialId;
    }

    public UserEntity(String username, String password, String role, PhotosEntity photosByPhotoId, Integer userSocialId,
                      UserSocialEntity userSocialEntity) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.photosByPhotoId = photosByPhotoId;
        this.userSocialId = userSocialId;
        this.userSocialByUserSocialId = userSocialEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role) &&
                Objects.equals(notificationId, that.notificationId) &&
                Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, username, password, photoId, notificationId);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserId")
    public Collection<FavouritesEntity> getFavouritesByUserId() {
        return favouritesByUserId;
    }

    public void setFavouritesByUserId(Collection<FavouritesEntity> favouritesByUserId) {
        this.favouritesByUserId = favouritesByUserId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserId")
    public Collection<RatingsEntity> getRatingsByUserId() {
        return ratingsByUserId;
    }

    public void setRatingsByUserId(Collection<RatingsEntity> ratingsByUserId) {
        this.ratingsByUserId = ratingsByUserId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserId")
    public Collection<RemindersEntity> getRemindersByUserId() {
        return remindersByUserId;
    }

    public void setRemindersByUserId(Collection<RemindersEntity> remindersByUserId) {
        this.remindersByUserId = remindersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "photoid", referencedColumnName = "photoid")
    public PhotosEntity getPhotosByPhotoId() {
        return photosByPhotoId;
    }

    public void setPhotosByPhotoId(PhotosEntity photosByPhotoId) {
        this.photosByPhotoId = photosByPhotoId;
    }

    @ManyToOne
    @JoinColumn(name = "user_social_id", referencedColumnName = "user_social_id")
    public UserSocialEntity getUserSocialByUserSocialId() {
        return userSocialByUserSocialId;
    }

    public void setUserSocialByUserSocialId(UserSocialEntity userSocialByUserSocialId) {
        this.userSocialByUserSocialId = userSocialByUserSocialId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userByUserId")
    public Collection<WanttowatchEntity> getWanttowatchesByUserId() {
        return wanttowatchesByUserId;
    }

    public void setWanttowatchesByUserId(Collection<WanttowatchEntity> wanttowatchesByUserId) {
        this.wanttowatchesByUserId = wanttowatchesByUserId;
    }
}
