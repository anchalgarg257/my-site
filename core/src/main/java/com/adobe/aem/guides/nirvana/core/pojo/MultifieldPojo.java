package com.adobe.aem.guides.nirvana.core.pojo;

public class MultifieldPojo {
    String image;
    String link;
    String openInNewTab;

    public MultifieldPojo(String image, String link, String openInNewTab) {
        this.image = image;
        this.link = link;
        this.openInNewTab = openInNewTab;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public void setOpenInNewTab(String openInNewTab) {
        this.openInNewTab = openInNewTab;
    }
}
