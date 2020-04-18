package com.adobe.aem.guides.nirvana.core.services.impl;


import com.adobe.aem.guides.nirvana.core.services.TagService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = TagService.class,configurationPolicy= ConfigurationPolicy.REQUIRE)
@Designate(ocd = TagConfiguration.class)
public class TagImpl implements TagService{

    @Activate
    private TagConfiguration tagConfiguration;


    @Override
    public String getPath() {
        return tagConfiguration.getPath();
    }

    @Override
    public Boolean getChildren() {
        return tagConfiguration.getChildren();
    }
}
