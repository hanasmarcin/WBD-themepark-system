/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class KsiegowaController {

    @FXML
    private Button przyciskDodajBilet;

    @FXML
    private Button przyciskEdytujBilet;

    @FXML
    private Button przyciskUsunBilet;

    @FXML
    private TableView<Klient> tabelaKlienci;

    @FXML
    private TableColumn<Klient, Integer> kolumnaIdKlienta;

    @FXML
    private TableColumn<Klient, String> kolumnaImie1Klienta;

    @FXML
    private TableColumn<Klient, String> kolumnaImie2Klienta;

    @FXML
    private TableColumn<Klient, String> kolumnaNazwiskoKlienta;

    @FXML
    private TableColumn<Klient, Date> kolumnaDataUrodzeniaKlienta;

    @FXML
    private TableColumn<Klient, String> kolumnaTelefonKlienta;

    @FXML
    private TableColumn<Klient, String> kolumnaEmailKlienta;

    @FXML
    private TableView<Bilet> tabelaBiletySprzedaz;

    @FXML
    private TableColumn<Bilet, Integer> kolumnaIdBiletuSprzedaz;

    @FXML
    private TableColumn<Bilet, String> kolumnaTypBiletuSprzedaz;

    @FXML
    private TableColumn<Bilet, Date> kolumnaDataZakupuSprzedaz;

    @FXML
    private TableColumn<Bilet, Date> kolumnaDataWaznosciSprzedaz;

    @FXML
    private TableColumn<Bilet, Float> kolumnaCenaSprzedaz;

    @FXML
    private Button przyciskDodajBilet1;

    @FXML
    private Button przyciskEdytujBilet1;

    @FXML
    private Button przyciskUsunBilet1;

    @FXML
    private RadioButton wyszukiwaniePoIdKlienta;

    @FXML
    private ToggleGroup wyszukajKlientaGrupa;

    @FXML
    private TextField poleIdKlientaSprzedazSzukaj;

    @FXML
    private RadioButton wyszukiwaniePoImieniuNazwiskuKlienta;

    @FXML
    private TextField poleImieKlientaSprzedaz;

    @FXML
    private TextField poleNazwiskoKlientaSprzedaz;

    @FXML
    private Button przyciskSzukajKlienta;

    @FXML
    private RadioButton wyszukiewaniePoIdBiletu;

    @FXML
    private ToggleGroup wyszukajBiletGrupa;

    @FXML
    private TextField poleIdBiletu;

    @FXML
    private RadioButton wyszukiwaniePoDacieZakupuBiletu;

    @FXML
    private DatePicker poleDataZakupuBiletuOd;

    @FXML
    private DatePicker poleDataZakupuBiletuDo;

    @FXML
    private Button przyciskSzukajBiletuSprzedaz;

    @FXML
    private TextField poleImie1Sprzedaz;

    @FXML
    private TextField poleImie2Sprzedaz;

    @FXML
    private TextField poleNazwiskoSprzedaz;

    @FXML
    private TextField poleTelefonSprzedaz;

    @FXML
    private TextField poleEmailSprzedaz;

    @FXML
    private Button przyciskEdytujBilet11;

    @FXML
    private Button przyciskDodajBilet2;

    @FXML
    private Button przyciskEdytujBilet2;

    @FXML
    private Button przyciskUsunBilet2;

    @FXML
    private TableView<Wynagrodzenie> tabelaWynagrodzenia;

    @FXML
    private TableColumn<Wynagrodzenie, Date> kolumnaDataWynagrodzenia;

    @FXML
    private TableColumn<Wynagrodzenie, Float> kolumnaKwotaPodstawowa;

    @FXML
    private TableColumn<Wynagrodzenie, Float> kolumnaKwotaDodatkowa;

    Connection conn;
    private Integer idPracownika;

    @FXML
    void initialize() {
        conn = DBConnection.getConnection();
        ObservableList<Klient> listaKlientow = Klient.getKlienci(conn);
//        poleTypBiletu.getItems().add("Normalny");
//        poleTypBiletu.getItems().add("Ulgowy");
        setTabelaKlientow(listaKlientow);
    }

    @FXML
    void onClickTabelaKlienci() {
        Klient wybranyKlient = tabelaKlienci.getSelectionModel().getSelectedItem();
        ObservableList<Bilet> bilety = Bilet.getDlaKlienta(conn, wybranyKlient.getIdKlienta());
        setTabelaBiletow(bilety);
//        setDaneKlienta(wybranyKlient);
        tabelaBiletySprzedaz.setDisable(false);
//        clearDaneBiletu();
    }

    @FXML
    void onClickTabelaBilety() {
        Bilet wybranyBilet = tabelaBiletySprzedaz.getSelectionModel().getSelectedItem();
//        setDaneBiletu(wybranyBilet);
    }

    @FXML
    void onClickDodajBIlet() {
        if (tabelaKlienci.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WidokEdycjiBiletu.fxml"));
            Parent root = fxmlLoader.load();
            new JMetro(root, Style.LIGHT);
            ((WidokEdycjiBiletuController) fxmlLoader.getController()).startAdd(this, tabelaKlienci.getSelectionModel().getSelectedItem().getIdKlienta());

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickEdytujBilet() {
        if (tabelaBiletySprzedaz.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WidokEdycjiBiletu.fxml"));
            Parent root = fxmlLoader.load();
            new JMetro(root, Style.LIGHT);
            ((WidokEdycjiBiletuController) fxmlLoader.getController()).startEdit(tabelaBiletySprzedaz.getSelectionModel().getSelectedItem(), this);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickDodajKlienta() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WidokEdycjiKlienta.fxml"));
            Parent root = fxmlLoader.load();
            new JMetro(root, Style.LIGHT);
            ((WidokEdycjiKlientaController) fxmlLoader.getController()).startAdd(this);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickEdytujKlienta() {
        if (tabelaKlienci.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WidokEdycjiKlienta.fxml"));
            Parent root = fxmlLoader.load();
            new JMetro(root, Style.LIGHT);
            ((WidokEdycjiKlientaController) fxmlLoader.getController()).startEdit(tabelaKlienci.getSelectionModel().getSelectedItem(), this);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("round_golf_course_black_48dp.png")));
            stage.setTitle("Program parku rozrywki");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickUsunKlienta() {
        if (tabelaKlienci.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Nie wybrano klienta", "Proszę wybrać klienta do usunięcia");
            return;
        }
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
        new JMetro(Style.LIGHT).setScene(alert.getDialogPane().getScene());

        alert.setTitle("");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Anuluj");
        alert.setHeaderText("Czy chcesz usunąć klienta?");
        alert.setResizable(false);
        alert.setContentText("Wybierz OK, aby usunąć klienta.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Klient klient = tabelaKlienci.getSelectionModel().getSelectedItem();
            Klient.deleteKlienta(conn, tabelaKlienci.getSelectionModel().getSelectedItem().getIdKlienta());
            tabelaKlienci.getItems().remove(klient);
        }
    }

    @FXML
    void onClickSzukajBiletuSprzedaz() {
        if (tabelaKlienci.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (wyszukiwaniePoDacieZakupuBiletu.equals(wyszukajBiletGrupa.getSelectedToggle())) {
            Date dataOd = null;
            Date dataDo = null;
            try {
                if (poleDataZakupuBiletuOd.getValue() != null) {
                    dataOd = Date.valueOf(poleDataZakupuBiletuOd.getValue());
                }
                if (poleDataZakupuBiletuDo.getValue() != null) {
                    dataDo = Date.valueOf(poleDataZakupuBiletuDo.getValue());
                }

                ObservableList<Bilet> listaBiletow = Bilet.getDlaKlientaWybrane(conn, tabelaKlienci.getSelectionModel().getSelectedItem().getIdKlienta(), dataOd, dataDo);
                setTabelaBiletow(listaBiletow);
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Podano zły format daty", "Data musi być podane w formacie YYYY-MM-DD.");
            }
        } else if (wyszukiewaniePoIdBiletu.equals(wyszukajBiletGrupa.getSelectedToggle())) {
            if (poleIdBiletu.getText().trim().isEmpty()) {
                ObservableList<Bilet> listaBiletow = Bilet.getDlaKlienta(conn, tabelaKlienci.getSelectionModel().getSelectedItem().getIdKlienta());
                setTabelaBiletow(listaBiletow);
            } else {
                ObservableList<Bilet> listaBiletow = FXCollections.observableArrayList();
                try {
                    listaBiletow.add(Bilet.getBilet(conn, Integer.parseInt(poleIdBiletu.getText()), tabelaKlienci.getSelectionModel().getSelectedItem().getIdKlienta()));
                    setTabelaBiletow(listaBiletow);
                } catch (NumberFormatException e) {
                    showErrorAlert("Błąd formatu danych", "Popraw dane wyszukiwania.");
                }
            }
        }
    }

    @FXML
    void onClickSzukajKlienta() {
        if (wyszukiwaniePoImieniuNazwiskuKlienta.equals(wyszukajKlientaGrupa.getSelectedToggle())) {
            ObservableList<Klient> klienci = Klient.getKlienciPoImieniuNazwisku(conn, poleImieKlientaSprzedaz.getText(), poleNazwiskoKlientaSprzedaz.getText());
            setTabelaKlientow(klienci);
            setTabelaBiletow(FXCollections.observableArrayList());
        } else if (wyszukiwaniePoIdKlienta.equals(wyszukajKlientaGrupa.getSelectedToggle())) {
            if (poleIdKlientaSprzedazSzukaj.getText().trim().isEmpty()) {
                ObservableList<Klient> klienci = Klient.getKlienci(conn);
                setTabelaKlientow(klienci);
                setTabelaBiletow(FXCollections.observableArrayList());
            } else {
                ObservableList<Klient> klienci = FXCollections.observableArrayList();
                try {
                    klienci.add(Klient.getKlient(conn, Integer.parseInt(poleIdKlientaSprzedazSzukaj.getText())));
                    setTabelaKlientow(klienci);
                    setTabelaBiletow(FXCollections.observableArrayList());
                } catch (NumberFormatException e) {
                    showErrorAlert("Błąd formatu danych", "Popraw dane wyszukiwania.");
                }
            }
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
    void onClickUsunBilet() {
        if (tabelaBiletySprzedaz.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Nie wybrano biletu", "Proszę wybrać bilet do usunięcia");
            return;
        }
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
        new JMetro(Style.LIGHT).setScene(alert.getDialogPane().getScene());

        alert.setTitle("");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Anuluj");
        alert.setHeaderText("Czy chcesz usunąć bilet?");
        alert.setResizable(false);
        alert.setContentText("Wybierz OK, aby usunąć bilet.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Bilet bilet = tabelaBiletySprzedaz.getSelectionModel().getSelectedItem();
            Bilet.deleteBilet(conn, tabelaBiletySprzedaz.getSelectionModel().getSelectedItem().getIdBiletu());
            tabelaBiletySprzedaz.getItems().remove(bilet);
        }
    }

    @FXML
    void onClickWyszukiewaniePoIdBiletu() {
        poleDataZakupuBiletuOd.setValue(null);
        poleDataZakupuBiletuDo.setValue(null);

        poleIdBiletu.setDisable(false);
        poleDataZakupuBiletuOd.setDisable(true);
        poleDataZakupuBiletuDo.setDisable(true);
    }

    @FXML
    void onClickWyszukiwaniePoDacieZakupuBiletu() {
        poleIdBiletu.clear();

        poleIdBiletu.setDisable(true);
        poleDataZakupuBiletuOd.setDisable(false);
        poleDataZakupuBiletuDo.setDisable(false);
    }

    @FXML
    Button przyciskWyloguj;

    @FXML
    void onClickWylogujPracownika() {
        try {
            Stage currentStage = (Stage) przyciskWyloguj.getScene().getWindow();
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
    void onClickWyszukiwaniePoIdKlienta() {
        poleImieKlientaSprzedaz.clear();
        poleNazwiskoKlientaSprzedaz.clear();

        poleIdKlientaSprzedazSzukaj.setDisable(false);
        poleImieKlientaSprzedaz.setDisable(true);
        poleNazwiskoKlientaSprzedaz.setDisable(true);
    }

    @FXML
    void onClickWyszukiwaniePoImieniuNazwiskuKlienta() {
        poleIdKlientaSprzedazSzukaj.clear();

        poleIdKlientaSprzedazSzukaj.setDisable(true);
        poleImieKlientaSprzedaz.setDisable(false);
        poleNazwiskoKlientaSprzedaz.setDisable(false);
    }

    void setTabelaKlientow(ObservableList<Klient> listaKlientow) {
        kolumnaIdKlienta.setCellValueFactory(new PropertyValueFactory<>("idKlienta"));
        kolumnaImie1Klienta.setCellValueFactory(new PropertyValueFactory<>("imie1"));
        kolumnaImie2Klienta.setCellValueFactory(new PropertyValueFactory<>("imie2"));
        kolumnaNazwiskoKlienta.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kolumnaDataUrodzeniaKlienta.setCellValueFactory(new PropertyValueFactory<>("dataUrodzenia"));
        kolumnaTelefonKlienta.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        kolumnaEmailKlienta.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabelaKlienci.setItems(listaKlientow);
    }

    void setTabelaBiletow(ObservableList<Bilet> listaBiletow) {

        //set up of columns
        kolumnaIdBiletuSprzedaz.setCellValueFactory(new PropertyValueFactory<>("idBiletu"));
        kolumnaTypBiletuSprzedaz.setCellValueFactory(new PropertyValueFactory<>("typBiletu"));
        kolumnaDataWaznosciSprzedaz.setCellValueFactory(new PropertyValueFactory<>("dataWaznosci"));
        kolumnaDataZakupuSprzedaz.setCellValueFactory(new PropertyValueFactory<>("dataZakupu"));
        kolumnaCenaSprzedaz.setCellValueFactory(new PropertyValueFactory<>("cena"));

        tabelaBiletySprzedaz.setItems(listaBiletow);
    }

    void setTabelaWynagrodzen(ObservableList<Wynagrodzenie> listaWynagrodzen) {

        //set up of columns
        kolumnaDataWynagrodzenia.setCellValueFactory(new PropertyValueFactory<>("data"));
        kolumnaKwotaPodstawowa.setCellValueFactory(new PropertyValueFactory<>("kwotaPodstawowa"));
        kolumnaKwotaDodatkowa.setCellValueFactory(new PropertyValueFactory<>("kwotaDodatkowa"));

        tabelaWynagrodzenia.setItems(listaWynagrodzen);
    }

    void setId(Integer idLogowanie) {
        idPracownika = idLogowanie;
        Pracownik pracownik = Pracownik.getPracownik(conn, idLogowanie);
        setDanePracownika(pracownik);
        ObservableList<Wynagrodzenie> wynagrodzenia = Wynagrodzenie.getDlaPracownika(conn, idPracownika);
        setTabelaWynagrodzen(wynagrodzenia);
    }

    TableView<Bilet> getTabelaBilety() {
        return tabelaBiletySprzedaz;
    }

    TableView<Klient> getTabelaKlienci() {
        return tabelaKlienci;
    }

    @FXML
    TextField poleIdPracownika;

    @FXML
    TextField poleImie1Pracownika;

    @FXML
    TextField poleImie2Pracownika;

    @FXML
    TextField poleNazwiskoPracownika;

    @FXML
    TextField poleTelefonPracownika;

    @FXML
    TextField poleEmailPracownika;

    private void setDanePracownika(Pracownik pracownik) {
        poleIdPracownika.setText(pracownik.getIdPracownika().toString());
        poleImie1Pracownika.setText(pracownik.getImie1());
        poleImie2Pracownika.setText(pracownik.getImie2());
        poleNazwiskoPracownika.setText(pracownik.getNazwisko());
        poleTelefonPracownika.setText(pracownik.getTelefon());
        poleEmailPracownika.setText(pracownik.getEmail());
    }

}
