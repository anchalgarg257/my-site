package com.adobe.aem.guides.nirvana.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Tag Configuration", description = "Tag Configuration")
public @interface TagConfiguration {

@AttributeDefinition(name = "Path", description = "Path of Tag")
    String getPath();

@AttributeDefinition(name = "Children", description = "Children of Tag", type = AttributeType.BOOLEAN)
    boolean getChildren();
}
