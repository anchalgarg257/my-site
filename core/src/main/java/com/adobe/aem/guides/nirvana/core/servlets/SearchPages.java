package com.adobe.aem.guides.nirvana.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;
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
        "sling.servlet.paths=" + "/bin/searchpages",
})

public class SearchPages extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder builder;

    private Session session;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {

            String xyz = request.getParameter("Path");
            String mno = request.getParameter("RootPath");
            String abc = request.getParameter("NumberofPages");
            if (abc == null) {
            abc = "-1";
            }
                ResourceResolver resourceResolver = request.getResourceResolver();
                Session session = resourceResolver.adaptTo(Session.class);

                Map<String, String> predicate = new HashMap<>();

                predicate.put("path", mno);
                predicate.put("type", "cq:Page");
                predicate.put("fulltext", xyz);
                predicate.put("p.limit", abc);

                Query query = builder.createQuery(PredicateGroup.create(predicate), session);

                JSONArray jsonArray = new JSONArray();

                Iterator<Resource> iterator = query.getResult().getResources();
                JSONObject jsonObject = new JSONObject();
                while (iterator.hasNext()) {
                    Resource resource = iterator.next();
                    Page page = resource.adaptTo(Page.class);
                    String pagePath = page.getPath();
                    String name = page.getName();

                    jsonObject.put(pagePath, name);
                }
                jsonArray.put(jsonObject);

//            int size = searchResult.getHits().size();

                response.getWriter().write(jsonArray.toString());
//
//            for (Hit hit : searchResult.getHits()) {
//
//                String path1 = hit.getPath();
//
//                response.getWriter().println(path1);
//            }

        }catch (Exception e) {

        } finally {

            if (session != null) {

                session.logout();
            }
        }
    }
}
