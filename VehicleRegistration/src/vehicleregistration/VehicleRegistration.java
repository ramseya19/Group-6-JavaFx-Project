package vehicleregistration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VehicleRegistration extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
            new FXMLLoader(getClass().getResource("VehicleSystem.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Vehicle Registration System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
