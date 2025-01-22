/*
Author: Charles Carter
Date: 1/22/2025

Description:  Exercise 31_20 from Intro to Java.
The program is a modification of TabPaneDemo.java from listing 31.12 in the textbook.
It displays various shapes in selectable tabs in the main window.   I've added radio
buttons at the bottom that change the alignment of the TabPane.
 */


import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Exercise31_20 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Line");
        StackPane pane1 = new StackPane();
        pane1.getChildren().add(new Line(10, 10, 80, 80));
        tab1.setContent(pane1);
        Tab tab2 = new Tab("Rectangle");
        tab2.setContent(new Rectangle(10, 10, 200, 200));
        Tab tab3 = new Tab("Circle");
        tab3.setContent(new Circle(50, 50, 20));
        Tab tab4 = new Tab("Ellipse");
        tab4.setContent(new Ellipse(10, 10, 100, 80));
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);


        //Radio buttons for tab placement
        RadioButton rbTop = new RadioButton("Top");
        RadioButton rbBottom = new RadioButton("Bottom");
        RadioButton rbLeft = new RadioButton("Left");
        RadioButton rbRight = new RadioButton("Right");

        ToggleGroup group = new ToggleGroup();
        rbTop.setToggleGroup(group);
        rbBottom.setToggleGroup(group);
        rbLeft.setToggleGroup(group);
        rbRight.setToggleGroup(group);

        rbTop.setSelected(true);

        //Event handlers for tab placement
        rbTop.setOnAction(e -> tabPane.setSide(Side.TOP));
        rbBottom.setOnAction(e -> tabPane.setSide(Side.BOTTOM));
        rbLeft.setOnAction(e -> tabPane.setSide(Side.LEFT));
        rbRight.setOnAction(e -> tabPane.setSide(Side.RIGHT));

        //Add radio buttons to VBox
        HBox radioBox = new HBox(10);
        radioBox.getChildren().addAll(rbTop, rbBottom, rbLeft, rbRight);

        //Combine TabPane and RadioButtons in a BorderPane
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(tabPane);
        mainPane.setBottom(radioBox);

        Scene scene = new Scene(mainPane, 300, 250); //Uses mainPane instead of tabPane
        primaryStage.setTitle("Tab Pane Demo with Radio Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     * line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
