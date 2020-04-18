package com.adobe.aem.guides.nirvana.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables= Resource.class)
public class Multifield {
    public String getName(){
        return "shu";
    }
}
