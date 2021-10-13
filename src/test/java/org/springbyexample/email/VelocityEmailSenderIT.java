/*
 * Copyright 2007-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springbyexample.email;

import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VelocityEmailSenderIT {

    @Test
    public void testMessage() {
        VelocityEmailSender sender = getVelocityEmailSender();
        SimpleMailMessage msg = getSimpleMailMessage();

        assertNotNull(sender, "VelocityEmailSender is null.");
        assertNotNull(msg, "SimpleMailMessage is null.");

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("firstName", "Joe");
        props.put("lastName", "Smith");

        sender.send(msg, props);
    }

    private SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("cup.asia.software.craftsmanship@gmail.com");
        msg.setTo("florian.besser@zuehlke.com");
        msg.setSubject("Greetings from Spring by Example");
        return msg;
    }

    private VelocityEmailSender getVelocityEmailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("cup.asia.software.craftsmanship@gmail.com");
        mailSender.setPassword("Censored"); // TODO: Change me!
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

}
