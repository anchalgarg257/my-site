package com.adobe.aem.guides.nirvana.core.servlets;

import com.adobe.aem.guides.nirvana.core.constants.ApplicationConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Search Property Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/searchproperty",
})
public class SearchPropertyServlet extends SlingSafeMethodsServlet {

    @Reference
    ResourceResolverFactory resolverFactory;

    private   final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String contentPath = request.getParameter("contentpath");
            String etcPath = request.getParameter("etcpath");

            if (StringUtils.isBlank(contentPath) && StringUtils.isBlank(etcPath)) {
                logger.error("Content Path and etcPath is null");
                return;
            }

          ResourceResolver  resolver = resolverFactory.getServiceResourceResolver(Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "testSystemUserService"));

            JSONObject jsonObject = new JSONObject();


            jsonObject.put(contentPath, getValueFromResource(contentPath, ApplicationConstants.PROPERTY_HELLO, resolver));
            jsonObject.put(etcPath, getValueFromResource(etcPath, "hello", resolver));
            response.getWriter().write(jsonObject.toString());
        }
        catch (JSONException | LoginException e) {
            e.printStackTrace();
        }

    }
   private String getValueFromResource(final String resourcePath, final String propertyName, final ResourceResolver resourceResolver) {

        if (resourcePath == null){
           logger.error("Resource path is null at path {}",resourcePath);
       }
        Resource resource = resourceResolver.getResource(resourcePath);

       if (resource == null) {
           logger.error("Resource is null at path {}", resource);
       }

      return resource.getValueMap().get(propertyName, String.class);
   }
}
