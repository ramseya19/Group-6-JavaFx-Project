package vehicleregistration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML private TextField txtOwner;
    @FXML private TextField txtVehicle;
    @FXML private TextField txtLicense;
    @FXML private TextField txtPlate;
    @FXML private TextArea txtOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtOutput.setText("System ready...\n");
    }

    @FXML
    private void handleRegister(ActionEvent event) {

        String owner = txtOwner.getText().trim();
        String vehicle = txtVehicle.getText().trim();
        String license = txtLicense.getText().trim();
        String plate = txtPlate.getText().trim();

        if (owner.isEmpty() || vehicle.isEmpty()
                || license.isEmpty() || plate.isEmpty()) {

            txtOutput.appendText("❌ All fields are required.\n");
            return;
        }

        String sql =
            "INSERT INTO vehicle_registration " +
            "(owner_name, vehicle_name, license_number, plate_number) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, owner);
            pst.setString(2, vehicle);
            pst.setString(3, license);
            pst.setString(4, plate);

            pst.executeUpdate();
            txtOutput.appendText("✅ Vehicle registered successfully.\n");
            handleClear(null);

        } catch (SQLException ex) {

            if (ex.getMessage().contains("license_number")) {
                txtOutput.appendText("❌ License number already exists.\n");
            } else if (ex.getMessage().contains("plate_number")) {
                txtOutput.appendText("❌ Plate number already exists.\n");
            } else {
                txtOutput.appendText("❌ Database error: "
                        + ex.getMessage() + "\n");
            }
        }
    }
    

    @FXML
    private void handleClear(ActionEvent event) {
        txtOwner.clear();
        txtVehicle.clear();
        txtLicense.clear();
        txtPlate.clear();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) txtOwner.getScene().getWindow();
        stage.close();
    }
}
