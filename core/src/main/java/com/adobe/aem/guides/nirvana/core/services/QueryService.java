package com.adobe.aem.guides.nirvana.core.services;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.Iterator;
import java.util.Map;

@Component(service = QueryService.class)
public class QueryService {
    @Reference
    QueryBuilder queryBuilder;

    public Iterator<Resource> getResourcesFromQuery(Map<String, String> predicate, Session session) {

        Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);

        Iterator<Resource> iterator = query.getResult().getResources();

        return iterator;
    }

    public int getNumberofResults(Map<String, String> predicate, Session session) {

      Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);
     SearchResult result = query.getResult();
       return result.getHits().size();
    }
}
