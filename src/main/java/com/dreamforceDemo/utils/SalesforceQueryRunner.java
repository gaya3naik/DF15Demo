package com.dreamforceDemo.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vivek on 12/8/15.
 */
public class SalesforceQueryRunner {

    public static Map query(String query, String queryUrl, String sessionId) throws Exception {
        JerseyClient client = JerseyClientBuilder.createClient();
        client.register(JacksonFeature.class);

        JerseyWebTarget target = client.target(queryUrl);
        target = target.queryParam("q", query);

        JerseyInvocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        builder.header("Authorization", "Bearer " + sessionId);
        Response response;
        try {
            response = builder.accept(MediaType.APPLICATION_JSON_TYPE).get();
            ;
        } catch (Exception e) {
            throw e;
        }

        String str = response.readEntity(String.class);
//        System.debug('str is '+str);
        return readMap(str, Object.class, Object.class);
    }

    public static <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) throws IOException {
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        MapType mapType = typeFactory.constructMapType(HashMap.class, keyClass, valueClass);
        try {
            return new ObjectMapper().readValue(json, mapType);
        } catch (IOException e) {
            throw e;
        }
    }

}
