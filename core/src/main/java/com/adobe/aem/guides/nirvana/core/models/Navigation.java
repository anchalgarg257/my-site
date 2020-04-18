package com.adobe.aem.guides.nirvana.core.models;


import com.adobe.aem.guides.nirvana.core.pojo.NavigationPojo;
import com.adobe.aem.guides.nirvana.core.services.PageService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Navigation {

    @ScriptVariable(name = "currentPage")
    Page page;

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    String websiteLink;

    @ValueMapValue
    Boolean getchildrenfromparent;

    @OSGiService
    PageService pageService;

    public String getCurrentPagePath() {
        return page.getPath();
    }

    public List<NavigationPojo> getChildrenPages() {
        String pagePath;

        String parentPath = page.getParent().getPath();
        String currentPagePath = page.getPath();
        String authoredLink = getAuthoredPath();

        if (getChildernFromParent().equals(true)) {
            pagePath = parentPath;
        } else if (authoredLink == null) {
            pagePath = currentPagePath;
        } else {
            pagePath = authoredLink;
        }
        return pageService.getChildrenPages(resourceResolver, pagePath);
    }

    public String getAuthoredPath() {
        return websiteLink;
    }

    public Boolean getChildernFromParent() {
        return getchildrenfromparent;

    }
}
