package com.businesstheworld.controller;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.businesstheworld.utils.ResponseUtils;

@Controller
public class ElasticSearchController {
	@SuppressWarnings({ "resource", "unchecked" })
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void sayJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ResponseUtils.renderJson(response, JackJsonUtils.toJson(listObject, true));
		
		Settings settings = Settings.builder()
				.put("cluster.name", "btw-cluster")
				.build();
		
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.19.220"), 9300));    
		
		SearchResponse searchResponse = client.prepareSearch("btw")  
//				.setTypes("cars")
//				.setQuery(queryBuilder)
				.get();
		
		String temp = "start:";
		for(SearchHit searchHit : searchResponse.getHits().getHits()) {
			temp += searchHit.getSourceAsString();
		}
		
		client.close();
		
		
		ResponseUtils.renderJson(response, temp + request.getParameter("parameter"));
	}
}
