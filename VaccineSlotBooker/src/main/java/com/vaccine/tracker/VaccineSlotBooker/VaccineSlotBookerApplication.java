package com.vaccine.tracker.VaccineSlotBooker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@SpringBootApplication
public class VaccineSlotBookerApplication {

	public static void main(String[] args) {

		SpringApplication.run(VaccineSlotBookerApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	@Bean
	public SimpleMailMessage emailTemplate()
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("rahul7651933@gmail.com","ayusaxena2000@gmail.com","saxenayash1503@gmail.com","tjava83@gmail.com","saxenajayanti@gmail.com");
		message.setFrom("tjava83@gmail.com");
		message.setSubject("Important email Slot Available");
		message.setText("FATAL - Application crash. Save your job !!");
		return message;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("tjava83@gmail.com");
		mailSender.setPassword("test@java123");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
