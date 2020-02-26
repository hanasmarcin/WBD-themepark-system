/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WidokEdycjiKlientaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField poleIdKlientaSprzedaz;

    @FXML
    private TextField poleImie1Sprzedaz;

    @FXML
    private TextField poleImie2Sprzedaz;

    @FXML
    private ChoiceBox<String> polePlecSprzedaz;

    @FXML
    private TextField poleTelefonSprzedaz;

    @FXML
    private TextField poleEmailSprzedaz;

    @FXML
    private TextField poleNazwiskoSprzedaz;

    @FXML
    private TextField poleDataUrodzeniaSprzedaz;

    @FXML
    private Button przyciskZapiszKlienta;

    @FXML
    void onClickZapiszKlienta() {
        if (wybranyKlient == null) {
            wybranyKlient = new Klient();
            try {
                wybranyKlient.setImie1(poleImie1Sprzedaz.getText());
                wybranyKlient.setImie2(poleImie2Sprzedaz.getText());
                wybranyKlient.setNazwisko(poleNazwiskoSprzedaz.getText());
                wybranyKlient.setPlec(polePlecSprzedaz.getSelectionModel().getSelectedItem());
                wybranyKlient.setDataUrodzenia(Date.valueOf(poleDataUrodzeniaSprzedaz.getText()));
                wybranyKlient.setEmail(poleEmailSprzedaz.getText());
                wybranyKlient.setTelefon(poleTelefonSprzedaz.getText());
            } catch (Exception e) {
                controller.showErrorAlert("Niepoprawny format danych", "Popraw dane, aby były we właściwym formacie");
                wybranyKlient = null;
                return;
            }
            Klient.insertKlient(controller.conn, wybranyKlient.getImie1(), wybranyKlient.getImie2(), wybranyKlient.getNazwisko(), wybranyKlient.getPlec(), wybranyKlient.getDataUrodzenia(), wybranyKlient.getTelefon(), wybranyKlient.getEmail());

            controller.setTabelaKlientow(FXCollections.observableArrayList());
            controller.setTabelaKlientow(Klient.getKlienci(controller.conn));
        }
        int index = controller.getTabelaKlienci().getItems().indexOf(wybranyKlient);

        try {
            wybranyKlient.setImie1(poleImie1Sprzedaz.getText());
            wybranyKlient.setImie2(poleImie2Sprzedaz.getText());
            wybranyKlient.setNazwisko(poleNazwiskoSprzedaz.getText());
            wybranyKlient.setPlec(polePlecSprzedaz.getSelectionModel().getSelectedItem());
            wybranyKlient.setDataUrodzenia(Date.valueOf(poleDataUrodzeniaSprzedaz.getText()));
            wybranyKlient.setEmail(poleEmailSprzedaz.getText());
            wybranyKlient.setTelefon(poleTelefonSprzedaz.getText());
        } catch (Exception e) {
            controller.showErrorAlert("Niepoprawny format danych", "Popraw dane, aby były we właściwym formacie");
            return;
        }
        System.out.printf("INDEX: %d", index);
        Klient.updateKlient(controller.conn, wybranyKlient.getIdKlienta(), wybranyKlient.getImie1(), wybranyKlient.getImie2(), wybranyKlient.getNazwisko(), wybranyKlient.getPlec(), wybranyKlient.getDataUrodzenia(), wybranyKlient.getTelefon(), wybranyKlient.getEmail());

        controller.setTabelaKlientow(FXCollections.observableArrayList());
        controller.setTabelaKlientow(Klient.getKlienci(controller.conn));

        Stage currentStage = (Stage) przyciskZapiszKlienta.getScene().getWindow();

        currentStage.close();

    }

    @FXML
    void initialize() {
        polePlecSprzedaz.getItems().add("M");
        polePlecSprzedaz.getItems().add("K");
    }

    private Klient wybranyKlient;
    private KsiegowaController controller;

    void startEdit(Klient klient, KsiegowaController ksiegowaController) {
        poleIdKlientaSprzedaz.setDisable(true);
        poleImie1Sprzedaz.setDisable(false);
        poleImie2Sprzedaz.setDisable(false);
        polePlecSprzedaz.setDisable(false);
        poleTelefonSprzedaz.setDisable(false);
        poleNazwiskoSprzedaz.setDisable(false);
        poleDataUrodzeniaSprzedaz.setDisable(false);
        poleEmailSprzedaz.setDisable(false);
        setDaneKlienta(klient);

        wybranyKlient = klient;
        controller = ksiegowaController;
    }

    void setDaneKlienta(Klient klient) {
        poleIdKlientaSprzedaz.setText(klient.getIdKlienta().toString());
        poleImie1Sprzedaz.setText(klient.getImie1());
        poleImie2Sprzedaz.setText(klient.getImie2());
        poleNazwiskoSprzedaz.setText(klient.getNazwisko());
        poleTelefonSprzedaz.setText(klient.getTelefon());
        poleEmailSprzedaz.setText(klient.getEmail());
        polePlecSprzedaz.setValue(klient.getPlec());
        poleDataUrodzeniaSprzedaz.setText(klient.getDataUrodzenia().toString());
    }

    void startAdd(KsiegowaController ksiegowaController) {
        poleIdKlientaSprzedaz.setDisable(true);
        poleImie1Sprzedaz.setDisable(false);
        poleImie2Sprzedaz.setDisable(false);
        polePlecSprzedaz.setDisable(false);
        poleTelefonSprzedaz.setDisable(false);
        poleNazwiskoSprzedaz.setDisable(false);
        poleDataUrodzeniaSprzedaz.setDisable(false);
        poleEmailSprzedaz.setDisable(false);

        controller = ksiegowaController;
    }
}
