package com.adobe.aem.guides.nirvana.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ChildrenPages {
    @ValueMapValue
    String homePagePath;

    @ValueMapValue
    String choosecategory;

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable(name = "currentPage")
    Page page;

    public String getHomePagePath() {
        return homePagePath;
    }

    public String getHomePageName() {
        Resource resource = resourceResolver.getResource(homePagePath);
        Page page = resource.adaptTo(Page.class);
        String title = page.getTitle() != null ? page.getTitle() : page.getName();
        return title;
    }

    public List<Page> getNavigationList() {
        List<Page> list = new ArrayList();
        if (choosecategory.equals("usingcurrentpageandchildrenpages")) {
            list.add(page);
            Iterator<Page> iterator = page.listChildren();
            while (iterator.hasNext()) {
                Page childrenPages = iterator.next();
                list.add(childrenPages);
            }
        }
        else {
          Resource resource =  resourceResolver.getResource(homePagePath);
         Page pageAdapter = resource.adaptTo(Page.class);
           Iterator<Page> iterator = pageAdapter.listChildren();
           while (iterator.hasNext()) {
            Page homeChildrenPages = iterator.next();
            list.add(homeChildrenPages);
           }
        }
        return list;
    }
}
