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
public class MultifieldModel {

        @Inject
        private Resource imagepath;

        public List<MultifieldPojo> getMultifield() {
            List<MultifieldPojo> list = new ArrayList<>();
            try{
                Iterator<Resource> iterator = imagepath.listChildren();
                while (iterator.hasNext()){
                    Resource childNode = iterator.next();
                    ValueMap valueMap = childNode.getValueMap();
                    String link = valueMap.get("link",String.class);
                    String text = valueMap.get("text",String.class);
                    String openInNewTab = valueMap.get("openInNewTab", String.class);
                    MultifieldPojo pojo = new MultifieldPojo(link, text, openInNewTab);
                    list.add(pojo);
                }
            }
            catch (Exception e){}
            return list;
        }
//    }
//
//
//    public String getName(){
//        return "name";
//    }
}
