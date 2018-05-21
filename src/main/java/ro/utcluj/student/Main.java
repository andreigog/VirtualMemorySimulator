package ro.utcluj.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static ro.utcluj.student.Utils.createScene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Dead Poets Society");
        primaryStage.setResizable(false);
        primaryStage.setScene(createScene("/layout/VirtualMemorySimulator.fxml"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
