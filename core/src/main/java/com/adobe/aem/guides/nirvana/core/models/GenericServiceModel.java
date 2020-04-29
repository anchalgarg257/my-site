package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.services.GenericListService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenericServiceModel {

    @ValueMapValue
    private String pathfield;

    @OSGiService
    private GenericListService genericListService;

    public String getJsonFromGenericList() {
        return genericListService.getGenericListJSON(pathfield).toString();
    }
}

