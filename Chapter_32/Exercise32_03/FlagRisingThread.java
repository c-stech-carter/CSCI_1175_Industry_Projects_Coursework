/*
Author: Charles Carter
Date: 1/15/2025

Description:  This program rewrites listing 15.13 using a thread to animate a flag being raised.
I've renamed the class to FlagRisingThread

(This program uses JavaFX)
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FlagRisingThread extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Create a pane
        Pane pane = new Pane();

        //Add an image view and add it to the pane
        ImageView imageView = new ImageView("image/us.gif");
        imageView.setX(0);
        imageView.setY(200);
        pane.getChildren().add(imageView);

        //Create a thread for animation
        Thread animationThread = new Thread(() -> {
           try {
               while (imageView.getY() > 0) {
                   Thread.sleep(50);

                   imageView.setY(imageView.getY() - 2);
               }
           }
           catch (InterruptedException e) {
               e.printStackTrace();
           }
        });

        animationThread.start(); //Start the thread

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 250, 200);
        primaryStage.setTitle("Flag Rising");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
