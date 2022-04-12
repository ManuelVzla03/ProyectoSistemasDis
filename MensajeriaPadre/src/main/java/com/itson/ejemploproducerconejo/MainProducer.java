/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itson.ejemploproducerconejo;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainProducer {

    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        System.out.println("Nombre del maestro: ");
        String maestro = tec.nextLine();

        System.out.println("Mensaje: ");
        while (true) {
            try {
                String pregunta = tec.nextLine();

                Message msj = new Message("Padre", maestro, pregunta);

                Connection connection = (Connection) factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(maestro, false, false, false, null);

                channel.basicPublish("", maestro, null, msj.getJson().getBytes("UTF-8"));
                System.out.println("Mensaje enviado");

                channel.queueDeclare("Padre", false, false, false, null);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    Gson gson = new Gson();
                    Message msg = gson.fromJson(new String(delivery.getBody(), "UTF-8"), Message.class);
                    System.out.println("--- De: " + msg.getSender() + " --- Para: Ti ---");
                    System.out.println(msg.getText());
                    System.out.println("--- A: " + msg.getDate() + " ---");
                    System.out.println("Mensaje: ");
                };
                channel.basicConsume("Padre", true, deliverCallback, consumerTag -> {
                });
            } catch (IOException ex) {
                Logger.getLogger(MainProducer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(MainProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
