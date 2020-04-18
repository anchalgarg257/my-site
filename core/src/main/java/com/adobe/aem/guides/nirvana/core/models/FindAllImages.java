package com.adobe.aem.guides.nirvana.core.models;

import com.adobe.aem.guides.nirvana.core.pojo.ImagesPojo;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.commons.util.DamUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FindAllImages {

    @ResourcePath(name = "pathfield")
    Resource resourcePath;

    @ValueMapValue
    String title;

    @ValueMapValue
    String description;

    public List<ImagesPojo> getImagesPath() {

        List<ImagesPojo> list = new ArrayList();

        if (resourcePath != null) {
            Iterator<Resource> iterator = resourcePath.listChildren();

            while (iterator.hasNext()) {
                Resource childrenResource = iterator.next();

                Asset asset;

                if (childrenResource != null && (asset = childrenResource.adaptTo(Asset.class)) != null && DamUtil.isImage(asset)) {

                    if ((asset.getMetadataValue(DamConstants.DC_TITLE)) != null && (asset.getMetadataValue(DamConstants.DC_DESCRIPTION)) != null) {
                    String dcTitle = asset.getMetadataValue(DamConstants.DC_TITLE);
                    String dcDescription = asset.getMetadataValue(DamConstants.DC_DESCRIPTION);
                    list.add(new ImagesPojo(dcTitle, dcDescription, asset.getPath()));
                }
                    else {
                        list.add(new ImagesPojo(title, description, asset.getPath()));
                    }

                    }
            }
        }
        return list;
    }
}