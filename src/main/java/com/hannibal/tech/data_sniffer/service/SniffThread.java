package com.hannibal.tech.data_sniffer.service;


import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@Scope("prototype")
public class SniffThread implements Runnable {

    @Autowired
    SniffSrvice sniffSrvice;
    private static final Logger LOGGER = LoggerFactory.getLogger(SniffThread.class);

    String url;
    @Override
    public void run() {
        try {
            sniffSrvice.saveData(url);
        } catch (ParseException e) {
            LOGGER.error("ParseException while extracting data "+e.getMessage() +"url "+url);
        }
        catch (Exception e) {
            LOGGER.error("Exception while extracting data "+e.getMessage() +"url "+url);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
