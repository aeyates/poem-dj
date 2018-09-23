package org.amy.poemdj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "org.amy.poemdj"})
@EntityScan(basePackages = { "org.amy.poemdj" })
@EnableJpaRepositories(basePackages = { "org.amy.poemdj" })
public class PoemDjApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoemDjApplication.class, args);
	}
}
