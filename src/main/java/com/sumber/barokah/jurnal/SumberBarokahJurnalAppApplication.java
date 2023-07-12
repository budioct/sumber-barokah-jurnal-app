package com.sumber.barokah.jurnal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // mengaktifkannya spring data jpa Auditing
@SpringBootApplication
public class SumberBarokahJurnalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumberBarokahJurnalAppApplication.class, args);
	}

}
