package com.adobe.aem.guides.nirvana.core.services.impl;

import com.adobe.aem.guides.nirvana.core.services.MySimpleService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = MySimpleService.class,configurationPolicy= ConfigurationPolicy.REQUIRE)
@Designate(ocd = MyServiceConfiguration.class)
public class MySimpleServiceImpl implements MySimpleService {

    @Activate
    private MyServiceConfiguration config;

    @Override
    public boolean getopenInNewWindow() {
        return config.getopenInNewWindow();
    }

    @Override
    public String getGender() {
        return config.getGender();
    }

    @Override
    public String configValue() {
        return config.configValue();
    }

    @Override
    public String[] getStringValues() {
        return config.getStringValues();
    }

    @Override
    public int getNumberValue() {
        return config.getNumberValue();
    }

    @Override
    public String getUserName() {
        return config.getUserName();
    }

    @Override
    public String getPassword() {
        return config.getPassword();
    }
}