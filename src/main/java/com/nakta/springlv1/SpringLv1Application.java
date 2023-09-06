package com.nakta.springlv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring security 인증 기능 제외
public class SpringLv1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLv1Application.class, args);
    }

}
