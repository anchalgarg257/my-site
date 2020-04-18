package com.adobe.aem.guides.nirvana.core.servlets;


import com.adobe.aem.guides.nirvana.core.services.QueryService;
import com.day.cq.dam.api.Asset;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/searchdamasset",
})

public class SearchDamAsset extends SlingSafeMethodsServlet {

    @Reference
    QueryService queryService;

    private Session session;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String path = request.getParameter("path");
            String numberofPages = request.getParameter("NumberofPages");

            if (name != null && path != null) {

                ResourceResolver resourceResolver = request.getResourceResolver();
                Session session = resourceResolver.adaptTo(Session.class);

                Map<String, String> predicate = new HashMap<>();

                predicate.put("path", path);
                predicate.put("type", "dam:Asset");
                predicate.put("fulltext", name);
                predicate.put("p.limit", numberofPages);

//                Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);
//
//                Iterator<Resource> iterator = query.getResult().getResources();

             Iterator<Resource> iterator =  queryService.getResourcesFromQuery(predicate, session);

            int number = queryService.getNumberofResults(predicate, session);

                JSONArray jsonArray = new JSONArray();
                JSONObject sizeJsonObject = new JSONObject();

                while (iterator.hasNext()) {
                    Resource resource = iterator.next();
                    Asset asset = resource.adaptTo(Asset.class);

                    JSONObject jsonObject = new JSONObject();

                    String assetName = asset.getName();
                    String assetPath = asset.getPath();
                    jsonObject.put("Name", assetName);
                    jsonObject.put("Path", assetPath);
                    jsonArray.put(jsonObject);
                }
                sizeJsonObject.put("size", number);

                jsonArray.put(sizeJsonObject);

                response.getWriter().write(jsonArray.toString());
            } else {
                response.getWriter().write("Write Parameters");
            }
        } catch (Exception e) {

        }
    }
}
