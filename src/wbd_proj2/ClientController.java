/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ClientController {

    @FXML
    private TableView<Bilet> tabelaBilety;

    @FXML
    private TableColumn<Bilet, Integer> kolumnaIdBiletu;

    @FXML
    private TableColumn<Bilet, String> kolumnaTypBiletu;

    @FXML
    private TableColumn<Bilet, Date> kolumnaDataZakupu;

    @FXML
    private TableColumn<Bilet, Date> kolumnaDataWaznosci;

    @FXML
    private TableColumn<Bilet, Float> kolumnaCena;

    @FXML
    private TextField poleImie1;

    @FXML
    private TextField poleImie2;

    @FXML
    private TextField poleNazwisko;

    @FXML
    private TextField poleTelefon;

    @FXML
    private TextField poleEmail;

    @FXML
    private Button przyciskZapisz;

    @FXML
    private Button przyciskEdytuj;

    @FXML
    private DatePicker poleDataOd;

    @FXML
    private Button przyciskSzukaj;

    @FXML
    private DatePicker poleDataDo;

    private int id_klienta = 1;
    private Connection conn;
    private Stage currentStage;

    @FXML
    void initialize() {
        conn = DBConnection.getConnection();
    }

    void setTabelaBiletow(ObservableList<Bilet> listaBiletow) {

        //set up of columns
        kolumnaIdBiletu.setCellValueFactory(new PropertyValueFactory<>("idBiletu"));
        kolumnaTypBiletu.setCellValueFactory(new PropertyValueFactory<>("typBiletu"));
        kolumnaDataWaznosci.setCellValueFactory(new PropertyValueFactory<>("dataWaznosci"));
        kolumnaDataZakupu.setCellValueFactory(new PropertyValueFactory<>("dataZakupu"));
        kolumnaCena.setCellValueFactory(new PropertyValueFactory<>("cena"));

        tabelaBilety.setItems(listaBiletow);
    }

    void setDaneKlienta() {
        Klient klient = Klient.getKlient(conn, id_klienta);
        poleImie1.setText(klient.getImie1());
        poleImie2.setText(klient.getImie2());
        poleNazwisko.setText(klient.getNazwisko());
        poleTelefon.setText(klient.getTelefon());
        poleEmail.setText(klient.getEmail());
    }

    void setPolaEdycji(boolean isEditable) {
        poleImie1.setDisable(isEditable);
        poleImie2.setDisable(isEditable);
        poleNazwisko.setDisable(isEditable);
        poleTelefon.setEditable(isEditable);
        poleEmail.setEditable(isEditable);
        przyciskZapisz.setDisable(!isEditable);
        przyciskEdytuj.setDisable(isEditable);

    }

    @FXML
    void onClickPrzyciskEdytuj() {
        setPolaEdycji(true);
        poleTelefon.requestFocus();
    }

    @FXML
    void onClickPrzyciskZapisz() {
        setPolaEdycji(false);
        Klient.updateKlient(conn, id_klienta, poleTelefon.getText(), poleEmail.getText());
    }

    @FXML
    Button przyciskWylogujKlienta;

    @FXML
    void onClickWylogujKlienta() {
        try {
            Stage currentStage = (Stage) przyciskWylogujKlienta.getScene().getWindow();
            FXMLLoader fxmlLoader;
            Parent root;
            Stage stage = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("Logowanie.fxml"));
            root = fxmlLoader.load();
            new JMetro(root, Style.LIGHT);
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.show();
            currentStage.close();
        } catch (Exception e) {

        }
    }

    @FXML
    void onClickPrzyciskSzukaj() {
        Date dataOd = null;
        Date dataDo = null;
        try {
            if (poleDataOd.getValue() != null) {
                dataOd = Date.valueOf(poleDataOd.getValue());
            }
            if (poleDataDo.getValue() != null) {
                dataDo = Date.valueOf(poleDataDo.getValue());
            }
            ObservableList<Bilet> listaBiletow = Bilet.getDlaKlientaWybrane(conn, id_klienta, dataOd, dataDo);
            setTabelaBiletow(listaBiletow);
        } catch (Exception e) {
            showErrorAlert("Podano zły format daty", "Data musi być podane w formacie YYYY-MM-DD.");
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

    void setId(Integer idKlienta, Stage stage) {
        id_klienta = idKlienta;
        currentStage = stage;
        ObservableList<Bilet> listaBiletow = Bilet.getDlaKlienta(conn, id_klienta);
        setTabelaBiletow(listaBiletow);
        setDaneKlienta();
    }
}
