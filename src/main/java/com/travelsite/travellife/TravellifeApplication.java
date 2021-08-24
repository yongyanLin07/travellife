package com.travelsite.travellife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableJpaRepositories("com.travelsite.travellife.dao")
//自动扫描Bean
@ComponentScan("com.travelsite.travellife.*")
public class TravellifeApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(TravellifeApplication.class, args);
    }

}
