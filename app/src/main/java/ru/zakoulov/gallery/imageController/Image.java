package ru.zakoulov.gallery.imageController;

import java.util.Date;

/**
 * Created by Илья on 25.04.2018.
 */
public class Image {
    private String path;
    private String fullPath;
    private String name;
    private Date date;

    public void setPath(String imagePath) {
        this.path = imagePath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
