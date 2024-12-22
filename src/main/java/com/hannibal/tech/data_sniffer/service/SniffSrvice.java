package com.hannibal.tech.data_sniffer.service;


import com.mongodb.BasicDBObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class SniffSrvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SniffThread.class);
    @Autowired
    MongoTemplate mongoTemplate;
    @Value("${mongo.collection.name}")
    String collectionName;
    static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    public void saveData(String u) throws ParseException {
        RestTemplate rt = new RestTemplate();
        LOGGER.info("----Start sniffing from " + u);
        final String response = rt.getForObject(u, String.class);
        JSONParser parser = new JSONParser();
        JSONObject document = null;
        JSONArray documentArray = null;
        try {
            document = (JSONObject) parser.parse(response);
        } catch (ClassCastException e) {
            document = new JSONObject();
            documentArray = (JSONArray) parser.parse(response);
        }
        if (documentArray != null) {

            document.put("values", documentArray);
        }
        BasicDBObject timeNow = new BasicDBObject("date", new Date());
        document.put("insertion_date", timeNow);
        document.put("SYSTEM", "DATA_SNIFFER");
        mongoTemplate.insert(document, collectionName);
        LOGGER.info("----data inserted with success "+u);}
}
