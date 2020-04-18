package com.adobe.aem.guides.nirvana.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "My Service Configuration", description = "Service Configuration")
public @interface MyServiceConfiguration {

    @AttributeDefinition(name = "user.name", description = "User Name")
    String getUserName() default "";

    @AttributeDefinition(name = "userpassword", description = "Password of the user account", type = AttributeType.PASSWORD)
    String getPassword() default "";

    @AttributeDefinition(name = "Config Value", description = "Configuration value")
    String configValue();

    @AttributeDefinition(name = "MultipleValues", description = "Multi Configuration values")
    String[] getStringValues();

    @AttributeDefinition(name = "NumberValue", description = "Number values", type= AttributeType.INTEGER)
    int getNumberValue();

    @AttributeDefinition(name = "openInNewWindow", description = "Open In New Window", type= AttributeType.BOOLEAN)
    boolean getopenInNewWindow();

    @AttributeDefinition(
            name = "Gender",
            description = "Gender",
            options = {
                    @Option(label = "Male", value = "male"), @Option(label = "Female", value = "female") })
     String getGender();

}
