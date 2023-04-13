package com.example.yespaysgallery.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/** 
 * OpenAPIConfiguration class
 */
@Configuration
public class OpenAPIConfig {

    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;
  
    @Value("${bezkoder.openapi.prod-url}")
    private String prodUrl;
    
    
    /** 
     * @return OpenAPI
     */
    @Bean
    public OpenAPI myOpenAPI() {
      Server devServer = new Server();
      devServer.setUrl(devUrl);
      devServer.setDescription("Server URL in Development environment");
  
      Server prodServer = new Server();
      prodServer.setUrl(prodUrl);
      prodServer.setDescription("Server URL in Production environment");
  
      Contact contact = new Contact();
      contact.setEmail("haider.rajab.92@gmail.com");
      contact.setName("devHaider");
      contact.setUrl("https://eng-haider92.github.io/myPortfolio/");
  
  
      Info info = new Info()
          .title("image Gallery api")
          .version("1.0")
          .contact(contact)
          .description("This API exposes endpoints to manage image Gallery.")
          ;
  
      return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
