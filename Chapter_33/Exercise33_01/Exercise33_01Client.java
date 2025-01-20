/*
Author: Charles Carter
Date: 1/20/2025

Description:  This is the client application for a loan calculator. The program lets a user enter data then
submits that information when the 'Submit' button is pressed to the server application.
The server application handles the necessary calculations then returns that information back to the client.
 */

// Exercise31_01Client.java: The client sends the input to the server and receives
// result back from the server
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Exercise33_01Client extends Application {
  // Text field for receiving radius
  private TextField tfAnnualInterestRate = new TextField();
  private TextField tfNumOfYears = new TextField();
  private TextField tfLoanAmount = new TextField();
  private Button btSubmit= new Button("Submit");

  // Text area to display contents
  private TextArea ta = new TextArea();
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    GridPane gridPane = new GridPane();
    gridPane.add(new Label("Annual Interest Rate"), 0, 0);
    gridPane.add(new Label("Number Of Years"), 0, 1);
    gridPane.add(new Label("Loan Amount"), 0, 2);
    gridPane.add(tfAnnualInterestRate, 1, 0);
    gridPane.add(tfNumOfYears, 1, 1);
    gridPane.add(tfLoanAmount, 1, 2);
    gridPane.add(btSubmit, 2, 1);
    
    tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
    tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
    tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);
    
    tfLoanAmount.setPrefColumnCount(5);
    tfNumOfYears.setPrefColumnCount(5);
    tfLoanAmount.setPrefColumnCount(5);
            
    BorderPane pane = new BorderPane();
    pane.setCenter(new ScrollPane(ta));
    pane.setTop(gridPane);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 400, 250);
    primaryStage.setTitle("Exercise31_01Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    //Handle Submit button click
    btSubmit.setOnAction(e -> {
      try (Socket socket = new Socket("localhost", 8000);) {
        //Create input and output streams for communication with the server
        DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());

        //Collect user input
        double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText().trim());
        int numberOfYears = Integer.parseInt(tfNumOfYears.getText().trim());
        double loanAmount = Double.parseDouble(tfLoanAmount.getText().trim());

        //Send loan data to server
        outputToServer.writeDouble(annualInterestRate);
        outputToServer.writeInt(numberOfYears);
        outputToServer.writeDouble(loanAmount);

        //Receive results from the server
        double monthlyPayment = inputFromServer.readDouble();
        double totalPayment = inputFromServer.readDouble();

        //Display results in TextArea
        ta.appendText("Annual Interest Rate: " + annualInterestRate + '\n');
        ta.appendText("Number Of Years: " + numberOfYears + '\n');
        ta.appendText("Loan Amount: " + loanAmount + '\n');
        ta.appendText("Monthly Payment: " + monthlyPayment + '\n');
        ta.appendText("Total Payment: " + totalPayment + '\n');

      } catch (IOException ex) {
        ex.printStackTrace();
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
