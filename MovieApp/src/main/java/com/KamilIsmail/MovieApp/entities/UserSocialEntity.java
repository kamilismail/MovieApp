package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq10", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "user_social_id_seq", value = "user_social_id_seq"))
@Table(name = "user_social", schema = "public", catalog = "d55rc0894f06nc")
public class UserSocialEntity {
    private int userSocialId;
    private String username;
    private String mail;
    private String socialID;
    private Collection<UserEntity> usersByUserSocialId;

    public UserSocialEntity() {
    }

    @Id
    @GeneratedValue(generator = "seq10")
    @Column(name = "user_social_id")
    public int getUserSocialId() {
        return userSocialId;
    }

    public void setUserSocialId(int userSocialId) {
        this.userSocialId = userSocialId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "social_id")
    public String getSocialID() {
        return socialID;
    }

    public void setSocialID(String socialID) {
        this.socialID = socialID;
    }

    public UserSocialEntity(String username, String mail, String socialID) {
        this.username = username;
        this.mail = mail;
        this.socialID = socialID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSocialEntity that = (UserSocialEntity) o;
        return userSocialId == that.userSocialId &&
                Objects.equals(username, that.username) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(usersByUserSocialId, that.usersByUserSocialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userSocialId, username, mail, usersByUserSocialId);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userSocialByUserSocialId")
    public Collection<UserEntity> getUsersByUserSocialId() {
        return usersByUserSocialId;
    }

    public void setUsersByUserSocialId(Collection<UserEntity> usersByPhotoId) {
        this.usersByUserSocialId = usersByUserSocialId;
    }
}
