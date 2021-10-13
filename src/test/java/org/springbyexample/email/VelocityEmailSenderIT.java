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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class VelocityEmailSenderIT {

    final Logger logger = LoggerFactory.getLogger(VelocityEmailSenderIT.class);

    @Autowired
    private final VelocityEmailSender sender = null;

    @Test
    public void testMessage() {
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

}
