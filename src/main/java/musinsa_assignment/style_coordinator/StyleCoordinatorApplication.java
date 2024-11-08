package musinsa_assignment.style_coordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class StyleCoordinatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(StyleCoordinatorApplication.class, args);
	}

}
