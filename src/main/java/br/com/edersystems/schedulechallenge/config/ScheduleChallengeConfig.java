/*
Project .....................: schedule-challenge
Creation Date ...............: 23/08/2020 14:51:01
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.config;

import br.com.edersystems.schedulechallenge.util.data.LocalDateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleChallengeConfig {

    @Bean
    public LocalDateUtil getLocalDateUtil() {
        return new LocalDateUtil();
    }
}
