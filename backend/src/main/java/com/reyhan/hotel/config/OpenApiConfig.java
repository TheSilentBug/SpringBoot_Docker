package com.reyhan.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI hotelReservationOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Hotel Reservation API")
				.description("سامانه رزرواسیون هتل - API")
				.version("v1.0.0")
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
			.externalDocs(new ExternalDocumentation()
				.description("Documentation")
				.url("https://example.com/docs"));
	}
}


