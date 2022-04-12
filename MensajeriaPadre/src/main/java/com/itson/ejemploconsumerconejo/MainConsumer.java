/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itson.ejemploconsumerconejo;

import Data.ServicioConexion;
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

public class MainConsumer {

    private final static String QUEUE_NAME = "Maestro";

    public static void main(String[] args) {
        Scanner tec = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        ServicioConexion sc = new ServicioConexion();
        System.out.println(sc.getJson(String.class));

        while (true) {
            try {
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    Gson gson = new Gson();
                    Message msg = gson.fromJson(new String(delivery.getBody(), "UTF-8"), Message.class);
                    System.out.println("--- De: " + msg.getSender() + " --- Para: Ti ---");
                    System.out.println(msg.getText());
                    System.out.println("--- A: " + msg.getDate() + " ---");

                    channel.queueDeclare("padre", false, false, false, null);
                    System.out.println("Mensaje: ");
                    String pregunta = tec.nextLine();
                    Message msj = new Message("Maestro", "Padre", pregunta);

                    channel.basicPublish("", "Padre", null, msj.getJson().getBytes("UTF-8"));
                    System.out.println("Mensaje enviado");
                };

                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                });

            } catch (IOException ex) {
                Logger.getLogger(MainConsumer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(MainConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
