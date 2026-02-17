package tucil1_13524084;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL lokasiLayoutFXML = getClass().getResource("/tucil1_13524084/primary.fxml");
        if (lokasiLayoutFXML == null) {
            throw new RuntimeException("FILE TIDAK DITEMUKAN: primary.fxml");
        }

        FXMLLoader loader = new FXMLLoader(lokasiLayoutFXML);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Penyelesaian Permainan Queens Linkedin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}