package br.com.avaliacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("br.com.avaliacao")
public class AvaliacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoApplication.class, args);
	}

}
