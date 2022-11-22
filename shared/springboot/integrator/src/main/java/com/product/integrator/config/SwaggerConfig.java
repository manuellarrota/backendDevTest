
package com.product.integrator.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


@Configuration
@Slf4j
public class SwaggerConfig {

    // load values from properties file
    @Value("${swagger.url.pom}")
    private String swaggerUrlPom;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.product.integrator"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo metaData() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        try {
            model = reader.read(new FileReader(swaggerUrlPom));
            return new ApiInfo(model.getArtifactId(), model.getDescription(), model.getParent().getVersion(), "",
                    new Contact("zara", "http://zara.com", ""), "", "", new ArrayList<>());
        } catch (IOException | XmlPullParserException e) {
            log.error(e.getMessage());
            return new ApiInfo("integrator", "Api to get product similar ids integrated", "0.0.1-SNAPSHOT", "",
                    new Contact("zara", "http://zara.com", ""), "", "", new ArrayList<>());
        }
    }
}

