package com.cmpe275.finalProject.cloudEventCenter.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Config {
    @Value("${application.url.frontend:http://54.147.1.156:3000/}")
    private String frontEndURL;

    @Value("${application.url.backend:http://cmpe275cloudeventcenter-env.eba-gvrtiqb8.us-west-1.elasticbeanstalk.com/}")
    private String backEndURL;
}
