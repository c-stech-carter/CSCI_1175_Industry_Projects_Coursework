/*
Author: Charles Carter
Date: 1/20/2025

Description:  Exercise 33-9 from Intro to Java
This is the server application to allow chat over a local network.

 */

package carter.stech.exercise33_09;

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

public class Exercise33_09Server extends Application {
    private TextArea taServer = new TextArea();
    private TextArea taClient = new TextArea();
    private DataOutputStream outputToClient;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        taServer.setWrapText(true);
        taClient.setWrapText(true);

        taServer.setEditable(false);  // History not editable

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
        primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Set up socket connection
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                taServer.appendText("Server started on port. " + serverSocket.getLocalPort() + "\n");

                Socket socket = serverSocket.accept();
                taServer.appendText("Client connected...\n");

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                outputToClient = new DataOutputStream(socket.getOutputStream());

                //Thread to receive messages from client
                new Thread(() -> {
                    try {
                        while (true) {
                            String message = inputFromClient.readUTF();
                            taServer.appendText("Client: " + message + "\n");
                        }
                    } catch (IOException ex) {
                        taServer.appendText("Error receiving message from client.\n");
                    }
                }).start();
            }catch (IOException ex){
                taServer.appendText("Error starting server: " + ex.getMessage() + "\n");
            }
        }).start();

        //Send message to client on Enter key press
        // Send message to client on Enter key press
        taClient.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    try {
                        String message = taClient.getText().trim();
                        outputToClient.writeUTF(message);
                        taServer.appendText("Server: " + message + "\n");
                        taClient.clear();
                    } catch (IOException ex) {
                        taServer.appendText("Error sending message to client.\n");
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
