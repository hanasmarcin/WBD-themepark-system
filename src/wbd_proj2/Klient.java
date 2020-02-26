/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_proj2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author Marcin
 */
public class Klient {

    static Klient getKlientZHaslem(Connection conn, int idKlienta, String nazwisko) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();
        String sql = "SELECT IMIE1, IMIE2, PLEC, DATA_URODZENIA, NR_TELEFONU, EMAIL FROM KLIENCI WHERE ID_KLIENTA = ? AND NAZWISKO = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idKlienta);
            stmt.setString(2, nazwisko);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Klient klient = new Klient();
                klient.idKlienta = idKlienta;
                klient.imie1 = rs.getString(1);
                klient.imie2 = rs.getString(2);
                klient.nazwisko = nazwisko;
                klient.plec = rs.getString(3);
                klient.dataUrodzenia = rs.getDate(4);
                klient.telefon = rs.getString(5);
                klient.email = rs.getString(6);
                listaKlientow.add(klient);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        if (listaKlientow.size() == 1) {
            return listaKlientow.get(0);
        } else {
            return null;
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        return (idKlienta == ((Klient) o).idKlienta && imie1.equals(((Klient) o).imie1) && imie2.equals(((Klient) o).imie2) && nazwisko.equals(((Klient) o).nazwisko) && plec.equals(((Klient) o).plec) && dataUrodzenia.equals(((Klient) o).dataUrodzenia) && email.equals(((Klient) o).email) && telefon.equals(((Klient) o).telefon));
//    }

    public static Integer insertKlient(Connection conn, String imie1, String imie2, String nazwisko, String plec, Date dataUrodzenia, String telefon, String email) {
        String sql = "INSERT INTO KLIENCI (ID_KLIENTA, IMIE1, IMIE2, NAZWISKO, PLEC, DATA_URODZENIA, NR_TELEFONU, EMAIL, ID_PARKU) VALUES (ID_KLIENTA_SEQ.NEXTVAL,?, ?, ?, ?, ?, ?, ?, 1)";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie1);
            stmt.setString(2, imie2);
            stmt.setString(3, nazwisko);
            stmt.setString(4, plec);
            stmt.setDate(5, dataUrodzenia);
            stmt.setString(6, telefon);
            stmt.setString(7, email);
            stmt.execute();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ID_KLIENTA_SEQ.CURRVAL FROM dual");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return null;
    }

    public static Integer updateKlient(Connection conn, Integer idKlienta, String imie1, String imie2, String nazwisko, String plec, Date dataUrodzenia, String telefon, String email) {
        String sql = "UPDATE KLIENCI SET IMIE1 = ?, IMIE2 = ?, NAZWISKO = ?, PLEC = ?, DATA_URODZENIA = ?, NR_TELEFONU = ?, EMAIL = ? WHERE ID_KLIENTA = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie1);
            stmt.setString(2, imie2);
            stmt.setString(3, nazwisko);
            stmt.setString(4, plec);
            stmt.setDate(5, dataUrodzenia);
            stmt.setString(6, telefon);
            stmt.setString(7, email);
            stmt.setInt(8, idKlienta);
            stmt.executeUpdate();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return null;
    }

    static ObservableList<Klient> getKlienciPoImieniuNazwisku(Connection conn, String imie, String nazwisko) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();
        String sql;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            sql = "SELECT ID_KLIENTA, IMIE1, IMIE2, NAZWISKO, PLEC, DATA_URODZENIA, NR_TELEFONU, EMAIL FROM KLIENCI WHERE LOWER(IMIE1) LIKE LOWER(?) AND LOWER(NAZWISKO) LIKE LOWER(?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie + "%");
            stmt.setString(2, nazwisko + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Klient klient = new Klient();
                klient.idKlienta = rs.getInt(1);
                klient.imie1 = rs.getString(2);
                klient.imie2 = rs.getString(3);
                klient.nazwisko = rs.getString(4);
                klient.plec = rs.getString(5);
                klient.dataUrodzenia = rs.getDate(6);
                klient.telefon = rs.getString(7);
                klient.email = rs.getString(8);
                listaKlientow.add(klient);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaKlientow;
    }

    Integer idKlienta;
    String imie1;
    String imie2;
    String nazwisko;
    String plec;
    Date dataUrodzenia;
    String telefon;
    String email;

    public Integer getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(Integer idKlienta) {
        this.idKlienta = idKlienta;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
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

    public static ObservableList<Klient> getKlienci(Connection conn) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();
        String sql = "SELECT ID_KLIENTA, IMIE1, IMIE2, NAZWISKO, PLEC, DATA_URODZENIA, NR_TELEFONU, EMAIL FROM KLIENCI";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Klient klient = new Klient();
                klient.idKlienta = rs.getInt(1);
                klient.imie1 = rs.getString(2);
                klient.imie2 = rs.getString(3);
                klient.nazwisko = rs.getString(4);
                klient.plec = rs.getString(5);
                klient.dataUrodzenia = rs.getDate(6);
                klient.telefon = rs.getString(7);
                klient.email = rs.getString(8);
                listaKlientow.add(klient);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaKlientow;
    }

    public static Klient getKlient(Connection conn, Integer idKlienta) {
        ObservableList<Klient> listaKlientow = FXCollections.observableArrayList();
        String sql = "SELECT IMIE1, IMIE2, NAZWISKO, PLEC, DATA_URODZENIA, NR_TELEFONU, EMAIL FROM KLIENCI WHERE ID_KLIENTA = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idKlienta);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Klient klient = new Klient();
                klient.idKlienta = idKlienta;
                klient.imie1 = rs.getString(1);
                klient.imie2 = rs.getString(2);
                klient.nazwisko = rs.getString(3);
                klient.plec = rs.getString(4);
                klient.dataUrodzenia = rs.getDate(5);
                klient.telefon = rs.getString(6);
                klient.email = rs.getString(7);
                listaKlientow.add(klient);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        if (listaKlientow.size() == 1) {
            return listaKlientow.get(0);
        } else {
            return null;
        }
    }

    public static void updateKlient(Connection conn, Integer idKlienta, String telefon, String email) {
        String sql = "UPDATE KLIENCI SET NR_TELEFONU = ?, EMAIL = ? WHERE ID_KLIENTA = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, telefon);
            stmt.setString(2, email);
            stmt.setInt(3, idKlienta);
            stmt.executeUpdate();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
    }
    
    public static void deleteKlienta(Connection conn, Integer idKlienta) {
        String sql = "DELETE FROM KLIENCI WHERE ID_KLIENTA = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idKlienta);
            stmt.execute();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
    }
}
