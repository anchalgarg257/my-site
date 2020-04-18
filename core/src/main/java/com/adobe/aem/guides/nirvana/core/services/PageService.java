package com.adobe.aem.guides.nirvana.core.services;

import com.adobe.aem.guides.nirvana.core.pojo.NavigationPojo;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(service = PageService.class, immediate = true)
public class PageService {

    public List<NavigationPojo> getChildrenPages(ResourceResolver resourceResolver, String pagePath) {
        Resource pageResource = resourceResolver.getResource(pagePath);
        Page currentPage = pageResource.adaptTo(Page.class);
        Iterator<Page> pageIterator = currentPage.listChildren();

        List<NavigationPojo> list = new ArrayList();
        while (pageIterator.hasNext()) {
            Page childPage = pageIterator.next();

            String path = childPage.getPath();
            String name = childPage.getName();

            NavigationPojo navigationPojo = new NavigationPojo(name, path);

            list.add(navigationPojo);
        }
        return list;
    }

    public List<NavigationPojo> getChildrenPages(ResourceResolver resourceResolver, Page page) {
      return   getChildrenPages(resourceResolver, page.getPath());
    }
    public List<NavigationPojo> getChildrenPages(ResourceResolver resourceResolver, Resource resource) {
       // getChildrenPages(resourceResolver, resource.getPath())
       return getChildrenPages(resourceResolver, resource.adaptTo(Page.class));
    }

    public List<NavigationPojo> getChildrenPages(ResourceResolver resourceResolver, Node node) {
        Resource pageResource = null;
        try {
           String nodePath = node.getPath();
             pageResource = resourceResolver.getResource(nodePath);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

       return getChildrenPages(resourceResolver, pageResource);
    }
}


