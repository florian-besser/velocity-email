package org.springbyexample.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        VelocityEmailSender sender = context.getBean(VelocityEmailSender.class);
        Scanner in = new Scanner(System.in);
        int cycles = 2;
        while (cycles-- > 0) {
            SimpleMailMessage msg = context.getBean(SimpleMailMessage.class);
            System.out.print("To: ");
            msg.setTo(in.nextLine());

            Map<String, Object> props = new HashMap<String, Object>();
            System.out.print("Firstname: ");
            props.put("firstName", in.nextLine());
            System.out.print("Lastname: ");
            props.put("lastName", in.nextLine());

            sender.send(msg, props);
        }
    }

}
