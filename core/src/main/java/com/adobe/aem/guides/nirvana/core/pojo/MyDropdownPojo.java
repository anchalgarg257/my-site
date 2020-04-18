package com.adobe.aem.guides.nirvana.core.pojo;

public class MyDropdownPojo {
String link;
String text;

    public MyDropdownPojo(String link, String text) {
        this.link = link;
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
