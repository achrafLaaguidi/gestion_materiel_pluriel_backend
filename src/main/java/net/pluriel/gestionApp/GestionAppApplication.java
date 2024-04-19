package net.pluriel.gestionApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GestionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionAppApplication.class, args);
	}

}
