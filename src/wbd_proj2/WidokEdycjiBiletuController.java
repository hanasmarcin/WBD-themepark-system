/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WidokEdycjiBiletuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField poleCenaBiletu;

    @FXML
    private ChoiceBox<String> poleTypBiletu;

    @FXML
    private TextField poleDataZakupuBiletu;

    @FXML
    private TextField poleDataWaznosciBiletu;

    @FXML
    private TextField poleIdBiletu;

    @FXML
    private Button przyciskEdytujBilet1;

    private KsiegowaController controller;
    private Integer idKlienta;

    @FXML
    void onClickZapiszBilet(ActionEvent event) {
        if (idKlienta != null) {
            wybranyBilet = new Bilet();
            try {
                wybranyBilet.setCena(Float.parseFloat(poleCenaBiletu.getText()));
                wybranyBilet.setTypBiletu(poleTypBiletu.getSelectionModel().getSelectedItem());
                wybranyBilet.setDataWaznosci(Date.valueOf(poleDataWaznosciBiletu.getText()));
                wybranyBilet.setDataZakupu(Date.valueOf(poleDataZakupuBiletu.getText()));

            } catch (Exception e) {
                controller.showErrorAlert("Niepoprawny format danych", "Popraw dane, aby były we właściwym formacie");
                return;
            }
            Integer index = Bilet.insertBilet(controller.conn, idKlienta, wybranyBilet.getCena(), wybranyBilet.getTypBiletu(), wybranyBilet.getDataZakupu(), wybranyBilet.getDataWaznosci());
            wybranyBilet.setIdBiletu(index);
            controller.setTabelaBiletow(FXCollections.observableArrayList());
            controller.setTabelaBiletow(Bilet.getDlaKlienta(controller.conn, controller.getTabelaKlienci().getSelectionModel().getSelectedItem().getIdKlienta()));
            Stage currentStage = (Stage) przyciskEdytujBilet1.getScene().getWindow();
            currentStage.close();
        }

        try {
            wybranyBilet.setCena(Float.parseFloat(poleCenaBiletu.getText()));
            wybranyBilet.setTypBiletu(poleTypBiletu.getSelectionModel().getSelectedItem());
            wybranyBilet.setDataWaznosci(Date.valueOf(poleDataWaznosciBiletu.getText()));
            wybranyBilet.setDataZakupu(Date.valueOf(poleDataZakupuBiletu.getText()));
        } catch (Exception e) {
            controller.showErrorAlert("Niepoprawny format danych", "Popraw dane, aby były we właściwym formacie");
            return;
        }
        int index = controller.getTabelaBilety().getItems().indexOf(wybranyBilet);

        Bilet.updateBilet(controller.conn, wybranyBilet.getIdBiletu(), wybranyBilet.getCena(), wybranyBilet.getTypBiletu(), wybranyBilet.getDataZakupu(), wybranyBilet.getDataWaznosci());
        controller.setTabelaBiletow(FXCollections.observableArrayList());
        controller.setTabelaBiletow(Bilet.getDlaKlienta(controller.conn, controller.getTabelaKlienci().getSelectionModel().getSelectedItem().getIdKlienta()));
        Stage currentStage = (Stage) przyciskEdytujBilet1.getScene().getWindow();

        currentStage.close();

    }

    @FXML
    void initialize() {
        poleTypBiletu.getItems().add("Normalny");
        poleTypBiletu.getItems().add("Ulgowy");
    }

    void setDaneBiletu(Bilet bilet) {
        poleIdBiletu.setText(bilet.getIdBiletu().toString());
        poleCenaBiletu.setText(bilet.getCena().toString());
        poleTypBiletu.setValue(bilet.getTypBiletu());
        poleDataZakupuBiletu.setText(bilet.getDataZakupu().toString());
        poleDataWaznosciBiletu.setText(bilet.getDataWaznosci().toString());
    }

    void disableDaneBiletu(boolean disabled) {
        poleIdBiletu.setDisable(true);
        poleCenaBiletu.setDisable(disabled);
        poleTypBiletu.setDisable(disabled);
        poleDataZakupuBiletu.setDisable(disabled);
        poleDataWaznosciBiletu.setDisable(disabled);
    }

    void clearDaneBiletu() {
        poleIdBiletu.clear();
        poleCenaBiletu.clear();
        poleTypBiletu.getSelectionModel().clearSelection();
        poleDataZakupuBiletu.clear();
        poleDataWaznosciBiletu.clear();
    }

    Bilet wybranyBilet;

    void startEdit(Bilet bilet, KsiegowaController ksiegowaController) {
        poleIdBiletu.setDisable(true);
        poleCenaBiletu.setDisable(false);
        poleTypBiletu.setDisable(false);
        poleDataZakupuBiletu.setDisable(false);
        poleDataWaznosciBiletu.setDisable(false);
        setDaneBiletu(bilet);
        wybranyBilet = bilet;
        idKlienta = null;
        controller = ksiegowaController;
    }

    void startAdd(KsiegowaController ksiegowaController, Integer wybranyKlient) {
        poleIdBiletu.setDisable(true);
        poleCenaBiletu.setDisable(false);
        poleTypBiletu.setDisable(false);
        poleDataZakupuBiletu.setDisable(false);
        poleDataWaznosciBiletu.setDisable(false);
        wybranyBilet = null;
        idKlienta = wybranyKlient;
        controller = ksiegowaController;
    }
}
