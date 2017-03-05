package com.lmonkiewicz.example.springSwaggerDoc;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(onlyNotes())
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Example Notes API")
                .description("Example API showing usege of Swagger for documenting Sprinv MVC RestControllers")
                .contact(new Contact("Łukasz Monkiewicz", "https://lmonkiewicz.com", "lmonkiewicz@gmail.com"))
                .version("1.0")
                .build();
    }

    /**
     * Include all paths except those stargin with /error
     */
    private Predicate<String> allExceptErrors(){
        return not(
                regex("/error.*")
        );
    }

    /**
     * Include only paths that start with /example
     */
    private Predicate<String> onlyNotes() {
        return ant("/notes/*");
    }

    /**
     * Include all paths
     */
    private Predicate<String> allPaths(){
        return PathSelectors.any();
    }
}