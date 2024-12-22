package com.hannibal.tech.data_sniffer.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TaskSheudeler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSheudeler.class);

    @Autowired
    SniffSrvice sniffSrvice;
    @Autowired
    SniffThread sniffThread;
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${url.list}")
    String [] urlList;

    @Value("${url.markettrace.list}")
    String [] urlMarketList;
    @Autowired
    private TaskExecutor taskExecutor;



    @Scheduled(cron="0 0 22 * * *")
    public void retrieveSaveData() {
        LOGGER.info("----Start extracting Data---");
        retrieveDataFromUrlList(urlList);
    }

    @Scheduled(cron="0 0 14 19 * *")
    public void retrieveSaveDataFromMarket() {
        LOGGER.info("----Start extracting Data from marketstack---");
        retrieveDataFromUrlList(urlMarketList);
    }
    private void retrieveDataFromUrlList(String [] urlList){
        LOGGER.info("----Start extracting Data---");
        Arrays.stream(urlList).forEach(u->{
            SniffThread sniffThread = (SniffThread) applicationContext.getBean("sniffThread");
            sniffThread.setUrl(u);
            taskExecutor.execute(sniffThread);

        });
    }
}
