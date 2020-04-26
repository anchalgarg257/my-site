package com.adobe.aem.guides.nirvana.core.models;


import com.adobe.aem.guides.nirvana.core.pojo.MultifieldPojo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables= Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Multifield {
    @Inject
   private Resource socialFooter;

    public List<MultifieldPojo> getMultifield() {
        List<MultifieldPojo> list = new ArrayList();
       Iterator<Resource> iterator = socialFooter.listChildren();
       while (iterator.hasNext()) {
          Resource childResource = iterator.next();
         ValueMap valueMap = childResource.getValueMap();
        String image = valueMap.get("image", String.class);
        String link = valueMap.get("link", String.class);
        String openInNewTab = valueMap.get("openInNewTab", String.class);

        MultifieldPojo multifieldPojo = new MultifieldPojo(image, link, openInNewTab);
        list.add(multifieldPojo);
       }
       return list;
    }
}
