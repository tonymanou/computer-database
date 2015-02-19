package com.tonymanou.computerdb.webservice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/endpoint.properties")
public class WebserviceProperties {

  private static final String PROPERTY_DOMAIN = "endpoint.domain";
  private static final String PROPERTY_PORT = "endpoint.port";
  private static final String PROPERTY_NAME = "endpoint.name";

  @Autowired
  private Environment env;

  private String url;

  @PostConstruct
  public void init() {
    String domain = env.getProperty(PROPERTY_DOMAIN);
    String port = env.getProperty(PROPERTY_PORT);
    String name = env.getProperty(PROPERTY_NAME);
    url = "http://" + domain + ":" + port + "/" + name + "/";
  }

  public String getUrl() {
    return url;
  }
}
