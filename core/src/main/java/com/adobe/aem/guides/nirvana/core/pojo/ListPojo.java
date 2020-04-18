package com.adobe.aem.guides.nirvana.core.pojo;

public class ListPojo {
    String title;
    String path;
    Boolean hasIcon;
    String className;


    public ListPojo(String title, String path, Boolean hasIcon, String className) {
        this.title = title;
        this.path = path;
        this.hasIcon = hasIcon;
        this.className = className;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHasIcon() {
        return hasIcon;
    }

    public void setHasTitle(Boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
