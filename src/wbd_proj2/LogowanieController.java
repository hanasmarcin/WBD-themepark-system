/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class LogowanieController {

    @FXML
    private TextField poleIdLogowanie;

    @FXML
    private Button przyciskZaloguj;

    @FXML
    private PasswordField poleNazwiskoLogowanie;

    @FXML
    private ChoiceBox<String> poleWidokLogowanie;

    Connection conn;

    @FXML
    void onClickZaloguj() throws IOException {
        FXMLLoader fxmlLoader;
        Parent root;
        Stage stage = new Stage();
        try {
            if (poleWidokLogowanie.getSelectionModel().getSelectedItem().equals("Klient")) {
                Klient klient = Klient.getKlientZHaslem(conn, Integer.parseInt(poleIdLogowanie.getText()), poleNazwiskoLogowanie.getText());
                if (klient != null) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
                    root = fxmlLoader.load();
                    new JMetro(root, Style.LIGHT);
                    ((ClientController) fxmlLoader.getController()).setId(klient.getIdKlienta(), stage);
                } else {
                    throw new InputMismatchException();
                }

            } else {
                Pracownik pracownik = Pracownik.getPracownikZHaslem(conn, Integer.parseInt(poleIdLogowanie.getText()), poleNazwiskoLogowanie.getText());
                if (pracownik != null) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("Ksiegowa.fxml"));
                    root = fxmlLoader.load();
                    new JMetro(root, Style.LIGHT);
                    ((KsiegowaController) fxmlLoader.getController()).setId(pracownik.getIdPracownika());
                } else {
                    throw new InputMismatchException();
                }
            }

            Stage currentStage = (Stage) przyciskZaloguj.getScene().getWindow();
            currentStage.hide();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.show();
            stage.setOnCloseRequest((WindowEvent event) -> {
                currentStage.show();
                poleNazwiskoLogowanie.clear();
            });
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Błąd logowania", "Podano niepoprawne dane logowania.");
            return;
        }
    }

    void showErrorAlert(String title, String header) {
        FlatAlert alert = new FlatAlert(Alert.AlertType.ERROR);
        new JMetro(Style.LIGHT).setScene(alert.getDialogPane().getScene());

        alert.setTitle("");
        alert.setHeaderText(title);
        alert.setResizable(false);
        alert.setContentText(header);
        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    void initialize() {
        conn = DBConnection.getConnection();
        poleWidokLogowanie.getItems().add("Klient");
        poleWidokLogowanie.getItems().add("Sprzedawca");
    }
}
