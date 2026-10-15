package com.bharath.watchSpace.Entities;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.List;

@Entity
public class Title
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer titleId;
    String name;
    String description;
    String genre;
    Integer durationInMinutes;

    @OneToMany(mappedBy = "title")
    List<WatchSpace> watchSpaces;

    public Title()
    {

    }


    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<WatchSpace> getWatchSpaces() {
        return watchSpaces;
    }

    public void setWatchSpaces(List<WatchSpace> watchSpaces) {
        this.watchSpaces = watchSpaces;
    }

    public Title(Integer titleId, String description, String name, String genre, Integer durationInMinutes, List<WatchSpace> watchSpaces) {
        this.titleId = titleId;
        this.description = description;
        this.name = name;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
        this.watchSpaces = watchSpaces;
    }
}
