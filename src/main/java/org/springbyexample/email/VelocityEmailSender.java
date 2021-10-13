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

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class VelocityEmailSender {

    private static final Logger logger = LoggerFactory.getLogger(VelocityEmailSender.class);

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender mailSender;


    /**
     * Sends e-mail using Velocity template for the body and
     * the properties passed in as Velocity variables.
     *
     * @param msg                The e-mail message to be sent, except for the body.
     * @param hTemplateVariables Variables to use when processing the template.
     */
    public void send(final SimpleMailMessage msg, final Map<String, Object> hTemplateVariables) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(msg.getTo());
                message.setFrom(msg.getFrom());
                message.setSubject(msg.getSubject());

                VelocityContext context = new VelocityContext();
                for (Map.Entry<String, Object> stringObjectEntry : hTemplateVariables.entrySet()) {
                    context.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
                }
                StringWriter stringWriter = new StringWriter();
                velocityEngine.mergeTemplate("emailBody.vm", "UTF-8", context, stringWriter);
                String body = stringWriter.getBuffer().toString();

                logger.info("body={}", body);

                message.setText(body, true);
            }
        };

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        ((JavaMailSenderImpl) mailSender).setJavaMailProperties(prop);
        mailSender.send(preparator);

        logger.info("Sent e-mail to '{}'.", msg.getTo());
    }

}
