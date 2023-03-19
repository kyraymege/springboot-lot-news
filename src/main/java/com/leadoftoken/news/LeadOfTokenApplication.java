package com.leadoftoken.news;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Lead Of Token News | REST API DOCS",
				description = "Lead Of Token News Rest APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "kyraymege",
						email = "egekayra.business@gmail.com",
						url = "https://www.leadoftoken.com/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.leadoftoken.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Lead Of Token News API documentation",
				url = "https://github.com/kyraymege/springboot-lot-news"
		)
)
public class LeadOfTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadOfTokenApplication.class, args);
	}

}
