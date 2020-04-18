package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.pojo.ListBean;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewListModel {
    @SlingObject
    ResourceResolver resourceResolver;
    @Inject
    private Resource fixedList;

    public List<ListBean> getList() {

        List<ListBean> list = new ArrayList<>();

        Iterator<Resource> iterator = fixedList.listChildren();
        while (iterator.hasNext()) {
            Resource childNode = iterator.next();
            ValueMap valueMap = childNode.getValueMap();

            String pagePath = valueMap.get("pagePath", String.class);
            if (pagePath != null) {
               Resource resource = resourceResolver.getResource(pagePath);
               if (resource != null && resource.isResourceType("cq:Page")) {
                   pagePath = pagePath + ".html";
               }

            }
            String pageName = valueMap.get("pageName", String.class);
           String openInNewTab = valueMap.get("openInNewTab", String.class);

            ListBean listBean = new ListBean(pagePath, pageName, openInNewTab);

            list.add(listBean);
        }
        return list;
    }
}
