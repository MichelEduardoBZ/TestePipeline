package br.com.michel.lixo;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootTest
class LixoApplicationTests {

	@Autowired
	private Environment env;

	@Test
	void contextLoads() {
	}

	@PostConstruct
	public void init() {
		System.out.println("Security configuration initialized for profile: " +
				Arrays.toString(env.getActiveProfiles()));
	}
}
