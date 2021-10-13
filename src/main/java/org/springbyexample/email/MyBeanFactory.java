package org.springbyexample.email;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.Properties;

public class MyBeanFactory {
    public SimpleMailMessage getSimpleMailMessage() {
        Properties properties = getMailProperties();

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(properties.getProperty("mail.username"));
        msg.setSubject("Greetings from Spring by Example");
        return msg;
    }

    public VelocityEmailSender getVelocityEmailSender() {
        Properties properties = getMailProperties();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("cup.asia.software.craftsmanship@gmail.com");
        mailSender.setPassword(properties.getProperty("mail.password"));
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.auth", "true");
        mailProps.setProperty("mail.smtp.starttls.enable", "true");
        mailProps.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mailSender.setJavaMailProperties(mailProps);

        Properties velocityProps = new Properties();
        velocityProps.setProperty("resource.loader", "class");
        velocityProps.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(velocityProps);
        return new VelocityEmailSender(velocityEngine, mailSender);
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        try {
            properties.load(MyBeanFactory.class.getResourceAsStream("/mail.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Uh oh!", e);
        }
        return properties;
    }
}
