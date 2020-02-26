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
import java.sql.Statement;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Marcin
 */
public class Bilet {

    Integer idBiletu;
    Float cena;
    String typBiletu;
    Date dataZakupu;
    Date dataWaznosci;

    public Integer getIdBiletu() {
        return idBiletu;
    }

    public void setIdBiletu(Integer idBiletu) {
        this.idBiletu = idBiletu;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public String getTypBiletu() {
        return typBiletu;
    }

    public void setTypBiletu(String typBiletu) {
        this.typBiletu = typBiletu;
    }

    public Date getDataZakupu() {
        return dataZakupu;
    }

    public void setDataZakupu(Date dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public Date getDataWaznosci() {
        return dataWaznosci;
    }

    public void setDataWaznosci(Date dataWaznosci) {
        this.dataWaznosci = dataWaznosci;
    }

    public static ObservableList<Bilet> getDlaKlienta(Connection conn, Integer idKlienta) {
        ObservableList<Bilet> listaBiletow = FXCollections.observableArrayList();
        String sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE Id_klienta = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idKlienta);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.idBiletu = rs.getInt(1);
                bilet.cena = rs.getFloat(2);
                bilet.typBiletu = rs.getString(3);
                bilet.dataZakupu = rs.getDate(4);
                bilet.dataWaznosci = rs.getDate(5);
                listaBiletow.add(bilet);
                System.out.printf("Bilet: %d", bilet.idBiletu);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaBiletow;
    }

    public static ObservableList<Bilet> getBilety(Connection conn) {
        ObservableList<Bilet> listaBiletow = FXCollections.observableArrayList();
        String sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.idBiletu = rs.getInt(1);
                bilet.cena = rs.getFloat(2);
                bilet.typBiletu = rs.getString(3);
                bilet.dataZakupu = rs.getDate(4);
                bilet.dataWaznosci = rs.getDate(5);
                listaBiletow.add(bilet);
                System.out.printf("Bilet: %d", bilet.idBiletu);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaBiletow;
    }

    public static Bilet getBilet(Connection conn, Integer idBiletu, Integer idKlienta) {
        ObservableList<Bilet> listaBiletow = FXCollections.observableArrayList();
        String sql;
        PreparedStatement stmt;
        ResultSet rs;
        try {

            sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE id_biletu = ? AND id_klienta = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idBiletu);
            stmt.setInt(2, idKlienta);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.idBiletu = rs.getInt(1);
                bilet.cena = rs.getFloat(2);
                bilet.typBiletu = rs.getString(3);
                bilet.dataZakupu = rs.getDate(4);
                bilet.dataWaznosci = rs.getDate(5);
                listaBiletow.add(bilet);
                System.out.printf("Bilet: %d", bilet.idBiletu);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        if (listaBiletow.size() == 1) {
            return listaBiletow.get(0);
        } else {
            return null;
        }
    }

    public static ObservableList<Bilet> getDlaKlientaWybrane(Connection conn, Integer idKlienta, Date dataOd, Date dataDo) {
        ObservableList<Bilet> listaBiletow = FXCollections.observableArrayList();
        String sql;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            if (dataOd != null && dataDo != null) {
                sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE Id_klienta = ? AND data_zakupu BETWEEN ? AND ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idKlienta);
                stmt.setDate(2, dataOd);
                stmt.setDate(3, dataDo);
            } else if (dataOd != null && dataDo == null) {
                sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE Id_klienta = ? AND data_zakupu >= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idKlienta);
                stmt.setDate(2, dataOd);
            } else if (dataOd == null && dataDo != null) {
                sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE Id_klienta = ? AND data_zakupu <= ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idKlienta);
                stmt.setDate(2, dataDo);
            } else {
                sql = "SELECT id_biletu,cena,typ_biletu,data_zakupu,data_waznosci FROM BILETY WHERE Id_klienta = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idKlienta);

            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Bilet bilet = new Bilet();
                bilet.idBiletu = rs.getInt(1);
                bilet.cena = rs.getFloat(2);
                bilet.typBiletu = rs.getString(3);
                bilet.dataZakupu = rs.getDate(4);
                bilet.dataWaznosci = rs.getDate(5);
                listaBiletow.add(bilet);
                System.out.printf("Bilet: %d", bilet.idBiletu);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaBiletow;
    }

    public static void updateBilet(Connection conn, Integer idBiletu, Float cena, String typBiletu, Date dataZakupu, Date dataWaznosci) {
        String sql = "UPDATE BILETY SET CENA = ?, TYP_BILETU = ?, DATA_ZAKUPU = ?, DATA_WAZNOSCI = ? WHERE ID_BILETU = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, cena);
            stmt.setString(2, typBiletu);
            stmt.setDate(3, dataZakupu);
            stmt.setDate(4, dataWaznosci);
            stmt.setInt(5, idBiletu);
            stmt.executeUpdate();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
    }

    public static Integer insertBilet(Connection conn, Integer idKlienta, Float cena, String typBiletu, Date dataZakupu, Date dataWaznosci) {
        String sql = "INSERT INTO BILETY (ID_BILETU, CENA, TYP_BILETU, DATA_ZAKUPU, DATA_WAZNOSCI, ID_KLIENTA) VALUES (ID_BILETU_SEQ.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, cena);
            stmt.setString(2, typBiletu);
            stmt.setDate(3, dataZakupu);
            stmt.setDate(4, dataWaznosci);
            stmt.setInt(5, idKlienta);
            stmt.execute();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ID_BILETU_SEQ.CURRVAL FROM dual");
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

    public static void deleteBilet(Connection conn, Integer idBiletu) {
        String sql = "DELETE FROM BILETY WHERE ID_BILETU = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idBiletu);
            stmt.execute();
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        return (idBiletu == ((Bilet) o).idBiletu && cena.equals(((Bilet) o).cena) && typBiletu.equals(((Bilet) o).typBiletu) && dataZakupu.equals(((Bilet) o).dataZakupu) && dataWaznosci.equals(((Bilet) o).dataWaznosci));
//    }
}
