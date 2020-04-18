package com.adobe.aem.guides.nirvana.core.pojo;

public class ImagesPojo {
    String title;
    String description;
    String path;

    public ImagesPojo(String title, String description, String path) {
        this.title = title;
        this.description = description;
        this.path = path;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
