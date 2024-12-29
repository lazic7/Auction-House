package com.example.auctionhousesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionHouseSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionHouseSecurityApplication.class, args);
        System.setProperty("org.jooq.no-logo", "true");
    }

}
