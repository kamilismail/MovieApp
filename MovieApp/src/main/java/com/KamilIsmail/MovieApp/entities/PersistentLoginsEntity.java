package com.KamilIsmail.MovieApp.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "persistent_logins", schema = "public", catalog = "d55rc0894f06nc")
public class PersistentLoginsEntity {
    private String series;
    private Timestamp lastUsed;
    private String token;
    private String username;

    @Id
    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Basic
    @Column(name = "last_used")
    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentLoginsEntity that = (PersistentLoginsEntity) o;
        return Objects.equals(series, that.series) &&
                Objects.equals(lastUsed, that.lastUsed) &&
                Objects.equals(token, that.token) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(series, lastUsed, token, username);
    }
}
