package pe.joedayz.training.springwebfluxmono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class SpringwebfluxmonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringwebfluxmonoApplication.class, args);
	}

}
