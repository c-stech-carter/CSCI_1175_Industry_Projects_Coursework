/*
Author: Charles Carter
Date: 1/20/2025

Description:  This is the server application for Exercise 33-1.  Both the client and server make up
a loan calculator.   The server application waits for a connection, and then when received
calculates the monthly payment and the total loan payment amount (with interest) and
returns that information to the client.
 */

// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
    // Text area for displaying contents
    private TextArea ta = new TextArea();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        ta.setWrapText(true);

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 400, 200);
        primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                Platform.runLater(() -> ta.appendText("Exercise31_01Server started at " + new Date() + '\n'));

                while (true) {
                    //Listen for a connection request
                    Socket socket = serverSocket.accept();

                    //Create input and output streams for communication with the client
                    DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                    //Read loan data from the client
                    double annualInterestRate = inputFromClient.readDouble();
                    int numberOfYears = inputFromClient.readInt();
                    double loanAmount = inputFromClient.readDouble();

                    //Create a loan object
                    Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);

                    //Calculate monthly and total payment
                    double monthlyPayment = loan.getMonthlyPayment();
                    double totalPayment = loan.getTotalPayment();

                    //Send results back to client
                    outputToClient.writeDouble(monthlyPayment);
                    outputToClient.writeDouble(totalPayment);

                    //Log for TextArea
                    Platform.runLater(() -> {
                       ta.appendText("Connected to client at " + new Date() + '\n');
                       ta.appendText("Annual Interest Rate: " + annualInterestRate + '\n');
                       ta.appendText("Number Of Years: " + numberOfYears + '\n');
                       ta.appendText("Loan Amount: " + loanAmount + '\n');
                       ta.appendText("Monthly Payment: " + monthlyPayment + '\n');
                       ta.appendText("Total Payment: " + totalPayment + '\n');
                    });
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }



    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
