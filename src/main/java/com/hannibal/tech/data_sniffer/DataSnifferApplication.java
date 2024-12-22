package com.hannibal.tech.data_sniffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class DataSnifferApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSnifferApplication.class, args);
	}

}
