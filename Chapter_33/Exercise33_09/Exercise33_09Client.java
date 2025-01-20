/*
Author: Charles Carter
Date: 1/20/2025

Description:  Exercise 33-9 from Intro to Java
This a client application for chatting over a local network.
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Exercise33_09Client extends Application {
    private TextArea taServer = new TextArea();
    private TextArea taClient = new TextArea();
    private DataOutputStream outputToServer;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        taServer.setWrapText(true);
        taClient.setWrapText(true);

        taServer.setEditable(false); // History not editable

        BorderPane pane1 = new BorderPane();
        pane1.setTop(new Label("History"));
        pane1.setCenter(new ScrollPane(taServer));
        BorderPane pane2 = new BorderPane();
        pane2.setTop(new Label("New Message"));
        pane2.setCenter(new ScrollPane(taClient));

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(pane1, pane2);

        // Create a scene and place it in the stage
        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        //Set up socket connection
        new Thread(() -> {
            try (Socket socket = new Socket("localhost", 8000)) {
                DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
                outputToServer = new DataOutputStream(socket.getOutputStream());

                //Thread to receive messages from server
                new Thread(() -> {
                    try {
                        while (true) {
                            String message = inputFromServer.readUTF();
                            taServer.appendText("Server: " + message + "\n");
                        }

                    } catch (IOException ex) {
                        taServer.appendText("Error receiving message from server. \n");
                    }
                }).start();
            }catch (IOException ex) {
                taServer.appendText("Error connecting to server: " + ex.getMessage() + "\n");
            }
        }).start();

        // Send message to server on Enter key press
        taClient.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    try {
                        String message = taClient.getText().trim();
                        outputToServer.writeUTF(message);
                        taServer.appendText("Client: " + message + "\n");
                        taClient.clear();
                    } catch (IOException ex) {
                        taServer.appendText("Error sending message to server.\n");
                    }
                }
            }
        });

    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
