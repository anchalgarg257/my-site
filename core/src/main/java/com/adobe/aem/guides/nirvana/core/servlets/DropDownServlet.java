package com.aem.project.core.servlets;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.wcm.api.Page;
import com.day.crx.JcrConstants;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "= Dynamic Drop Down",
                "sling.servlet.resourceTypes=" + "/apps/dropDownLIsting"
        })
public class DropDownServlet extends SlingSafeMethodsServlet {

    private static Logger LOGGER = LoggerFactory.getLogger(DropDownServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response) {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();


            //////////////////////////////////////////////////
            List<KeyValue> dropDownList = new ArrayList<>();
            Resource pathResource = request.getResource();
/*            String rootPath = pathResource.getChild("datasource").getValueMap().get("rootPath",String.class);
            Resource resource = request.getResourceResolver().getResource(rootPath);
            Iterator<Resource> iterator = resource.listChildren();
            List<Resource> list = new ArrayList<>();*/
            Resource resource =   resourceResolver.getResource("/content/we-retail/language-masters/en");
            Page page = resource.adaptTo(Page.class);
            Iterator<Page> iterator =  page.listChildren();
            List<Page> list = new ArrayList<>();
            iterator.forEachRemaining(list::add);
            list.forEach(res -> {

                String title = res.getName();
                dropDownList.add(new KeyValue(title, title));
            });
            @SuppressWarnings("unchecked")
            DataSource ds =
                    new SimpleDataSource(
                            new TransformIterator(
                                    dropDownList.iterator(),
                                    input -> {
                                        KeyValue keyValue = (KeyValue) input;
                                        ValueMap vm = new ValueMapDecorator(new HashMap<>());
                                        vm.put("value", keyValue.key);
                                        vm.put("text", keyValue.value);
                                        return new ValueMapResource(
                                                resourceResolver, new ResourceMetadata(),
                                                JcrConstants.NT_UNSTRUCTURED, vm);
                                    }));
            request.setAttribute(DataSource.class.getName(), ds);

        } catch (Exception e) {
            LOGGER.error("Error in Get Drop Down Values", e);
        }
    }

    private class KeyValue {

        /**
         * key property.
         */
        private String key;
        /**
         * value property.
         */
        private String value;

        /**
         * constructor instance intance.
         *
         * @param newKey   -
         * @param newValue -
         */
        private KeyValue(final String newKey, final String newValue) {
            this.key = newKey;
            this.value = newValue;
        }
    }
}
