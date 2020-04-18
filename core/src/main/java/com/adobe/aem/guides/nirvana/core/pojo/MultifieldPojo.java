package com.adobe.aem.guides.nirvana.core.pojo;

public class MultifieldPojo {
    String link;
    String text;
    String openInNewTab;

    public MultifieldPojo(String link, String text, String openInNewTab) {
        this.link = link;
        this.text = text;
        this.openInNewTab = openInNewTab;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public void setOpenInNewTab(String openInNewTab) {
        this.openInNewTab = openInNewTab;
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
