/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Marcin
 */
public class Pracownik {

    Integer idPracownika;
    String imie1;
    String imie2;
    String nazwisko;
    String telefon;
    String email;

    public Integer getIdPracownika() {
        return idPracownika;
    }

    public void setIdPracownika(Integer idPracownika) {
        this.idPracownika = idPracownika;
    }

    public String getImie1() {
        return imie1;
    }

    public void setImie1(String imie1) {
        this.imie1 = imie1;
    }

    public String getImie2() {
        return imie2;
    }

    public void setImie2(String imie2) {
        this.imie2 = imie2;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    static Pracownik getPracownikZHaslem(Connection conn, Integer idPracownika, String haslo) {
        ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT IMIE1, IMIE2, NAZWISKO, NUMER_TELEFONU, ADRES_EMAIL FROM PRACOWNICY WHERE ID_PRACOWNIKA = ? AND NAZWISKO = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPracownika);
            stmt.setString(2, haslo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.idPracownika = idPracownika;
                pracownik.imie1 = rs.getString(1);
                pracownik.imie2 = rs.getString(2);
                pracownik.nazwisko = rs.getString(3);
                pracownik.telefon = rs.getString(4);
                pracownik.email = rs.getString(5);
                listaPracownikow.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        if (listaPracownikow.size() == 1) {
            return listaPracownikow.get(0);
        } else {
            return null;
        }
    }

    static Pracownik getPracownik(Connection conn, Integer idPracownika) {
        ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
        String sql = "SELECT IMIE1, IMIE2, NAZWISKO, NUMER_TELEFONU, ADRES_EMAIL FROM PRACOWNICY WHERE ID_PRACOWNIKA = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPracownika);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.idPracownika = idPracownika;
                pracownik.imie1 = rs.getString(1);
                pracownik.imie2 = rs.getString(2);
                pracownik.nazwisko = rs.getString(3);
                pracownik.telefon = rs.getString(4);
                pracownik.email = rs.getString(5);
                listaPracownikow.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        if (listaPracownikow.size() == 1) {
            return listaPracownikow.get(0);
        } else {
            return null;
        }
    }

}
