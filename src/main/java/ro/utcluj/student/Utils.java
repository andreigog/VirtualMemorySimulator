package ro.utcluj.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Random;

public class Utils {

    public static Scene createScene(String sceneLocation) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(sceneLocation));
        Parent loaderParent = (Parent) loader.load();
        Scene scene = new Scene(loaderParent);
        return scene;
    }

    public static int generateRandomIntructions(int length){
        Random rand = new Random();
        return  (int) (Math.random() * Math.pow(2,length) + 12800);
    }
}
