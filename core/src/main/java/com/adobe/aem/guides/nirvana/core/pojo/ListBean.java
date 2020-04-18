package com.adobe.aem.guides.nirvana.core.pojo;

public class ListBean {
    String pagePath;
    String pageName;
    String openInNewTab;

    public ListBean(String pagePath, String pageName, String openInNewTab) {
        this.pagePath = pagePath;
        this.pageName = pageName;
        this.openInNewTab = openInNewTab;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getOpenInNewTab() {
        return openInNewTab;
    }

    public void setOpenInNewTab(String openInNewTab) {
        this.openInNewTab = openInNewTab;
    }
}