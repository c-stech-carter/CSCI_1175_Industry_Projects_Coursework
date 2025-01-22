/*
Author: Charles Carter
Date: 1/22/2025

Description: This program calculates the future value of an investment
at a given interest rate for a specified number of years.  The formula for
the calculation is as follows: futureValue = investmentAmount × (1 + monthlyInterestRate)^years×12

(Requires JavaFX libraries and controls.)
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class Exercise31_17 extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Labels
        Label lblInvestmentAmount = new Label("Investment Amount:");
        Label lblNumberOfYears = new Label("Number of Years:");
        Label lblAnnualInterestRate = new Label("Annual Interest Rate:");
        Label lblFutureValue = new Label("Future Value:");

        //TextFields
        TextField tfInvestmentAmount = new TextField();
        TextField tfNumberOfYears = new TextField();
        TextField tfAnnualInterestRate = new TextField();
        TextField tfFutureValue = new TextField();
        tfFutureValue.setEditable(false); //Output field needs to be read-only

        //Button
        Button btnCalculate = new Button("Calculate");

        //Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(lblInvestmentAmount, 0, 0);
        gridPane.add(tfInvestmentAmount, 1, 0);
        gridPane.add(lblNumberOfYears, 0, 1);
        gridPane.add(tfNumberOfYears, 1, 1);
        gridPane.add(lblAnnualInterestRate, 0, 2);
        gridPane.add(tfAnnualInterestRate, 1, 2);
        gridPane.add(lblFutureValue, 0, 3);
        gridPane.add(tfFutureValue, 1, 3);
        gridPane.add(btnCalculate, 1, 4);

        //MenuBar
        MenuBar menuBar = new MenuBar();
        Menu menuOperation = new Menu("Operation");
        MenuItem miCalculate = new MenuItem("Calculate");
        MenuItem miExit = new MenuItem("Exit");
        menuOperation.getItems().addAll(miCalculate, miExit);
        menuBar.getMenus().addAll(menuOperation);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(gridPane);

        //Event Handling
        btnCalculate.setOnAction(e -> calculateFutureValue(tfInvestmentAmount, tfNumberOfYears,
                tfAnnualInterestRate, tfFutureValue));
        miCalculate.setOnAction(e -> calculateFutureValue(tfInvestmentAmount, tfNumberOfYears,
                tfAnnualInterestRate, tfFutureValue));
        miExit.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(borderPane, 320, 250);
        primaryStage.setTitle("Exercise31_17");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateFutureValue(TextField tfInvestmentAmount, TextField tfNumberOfYears,
                                      TextField tfAnnualInterestRate, TextField tfFutureValue) {
        try {
            double investmentAmount = Double.parseDouble(tfInvestmentAmount.getText());
            double numberOfYears = Double.parseDouble(tfNumberOfYears.getText());
            double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());

            double monthlyInterestRate = annualInterestRate / 1200;

            //Calculate future value
            double futureValue = investmentAmount * Math.pow(1 + monthlyInterestRate, numberOfYears * 12);

            DecimalFormat df = new DecimalFormat("$###,###.00");
            tfFutureValue.setText(df.format(futureValue));
        } catch (NumberFormatException e) {
            tfFutureValue.setText("Invalid input");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
