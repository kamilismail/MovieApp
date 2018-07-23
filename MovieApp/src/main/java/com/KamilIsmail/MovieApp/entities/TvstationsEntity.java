package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq6", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "tvstation_id_seq", value = "tvstation_id_seq"))
@Table(name = "stations", schema = "public", catalog = "d55rc0894f06nc")
public class TvstationsEntity {
    private int tvstationId;
    private String name;
    private String logoPath;
    private Collection<RemindersEntity> remindersByTvstationId;

    @Id
    @GeneratedValue(generator = "seq6")
    @Column(name = "tvstationid")
    public int getTvstationId() {
        return tvstationId;
    }

    public void setTvstationId(int tvstationId) {
        this.tvstationId = tvstationId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "logo_path")
    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TvstationsEntity that = (TvstationsEntity) o;
        return tvstationId == that.tvstationId &&
                Objects.equals(name, that.name) &&
                Objects.equals(logoPath, that.logoPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tvstationId, name, logoPath);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tvstationsByTvstationId")
    public Collection<RemindersEntity> getRemindersByTvstationId() {
        return remindersByTvstationId;
    }

    public void setRemindersByTvstationId(Collection<RemindersEntity> remindersByTvstationId) {
        this.remindersByTvstationId = remindersByTvstationId;
    }
}
