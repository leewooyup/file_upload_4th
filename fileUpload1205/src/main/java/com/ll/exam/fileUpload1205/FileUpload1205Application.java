package com.ll.exam.fileUpload1205;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FileUpload1205Application {
	public static void main(String[] args) {
		SpringApplication.run(FileUpload1205Application.class, args);
	}

}
