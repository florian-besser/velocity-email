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

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VelocityEmailSenderIT {

    @Test
    public void testMessage() {
        MyBeanFactory factory = new MyBeanFactory();
        VelocityEmailSender sender = factory.getVelocityEmailSender();
        SimpleMailMessage msg = factory.getSimpleMailMessage();
        msg.setTo("florian.besser@zuhlke.com");

        assertNotNull(sender, "VelocityEmailSender is null.");
        assertNotNull(msg, "SimpleMailMessage is null.");

        Map<String, Object> props = new HashMap<>();
        props.put("firstName", "Joe");
        props.put("lastName", "Smith");

        sender.send(msg, props);
    }

}
