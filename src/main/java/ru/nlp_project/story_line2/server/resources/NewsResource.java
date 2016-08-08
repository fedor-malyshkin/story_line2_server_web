package ru.nlp_project.story_line2.server.resources;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("/news")
@Produces(MediaType.APPLICATION_JSON)
public class NewsResource {

  private MongoClient mongoClient;
  private TransportClient elasticClient;

  public NewsResource() {}

  @SuppressWarnings("unchecked")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addNews(String value) {
    MongoClient clientMongo = getMongoClient();
    MongoDatabase database = clientMongo.getDatabase("crawler");
    MongoCollection<Document> collection = database.getCollection("news");
    ObjectMapper mapper = new ObjectMapper();
    try {
      Map<String, String> map = mapper.readValue(value, Map.class);

      Document doc = new Document("domain", map.get("domain")).append("url", map.get("url"))
          .append("title", map.get("title")).append("date", map.get("date"))
          .append("content", map.get("content"));

      collection.insertOne(doc);
      System.out.println("Write to db: " + map.get("url"));


      // elasticsearch
      Client clientElastic = getElasticClient();
      IndexResponse response = clientElastic.prepareIndex("news", "post").setSource(value).get();
    } catch (IOException e) {
      e.printStackTrace();
      throw new WebApplicationException(e);
    }


  }

  @GET
  @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public String getNews(@QueryParam("key") String key) {
    try {
      Client clientElastic = getElasticClient();
      SearchResponse response = clientElastic.prepareSearch("news")
          .setSearchType(SearchType.DEFAULT).setQuery(QueryBuilders.termQuery("content", key)) // Query
          .setFrom(0).setSize(60).setExplain(true).execute().actionGet();
      return response.toString();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new WebApplicationException(e);
    }
  }

  protected Client getElasticClient() throws UnknownHostException {
    if (this.elasticClient == null)
      this.elasticClient = TransportClient.builder().build().addTransportAddress(
          new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    return elasticClient;
  }

  private MongoClient getMongoClient() {
    if (this.mongoClient == null) {
      MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
      this.mongoClient = new MongoClient(connectionString);
    }
    return this.mongoClient;
  }

}
