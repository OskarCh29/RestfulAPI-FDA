package pl.projectFDA.restfulAPI;

import org.springframework.boot.SpringApplication;

public class TestRestfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(RestfulApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
