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

/**
 *
 * @author Marcin
 */
public class Wynagrodzenie {

    private Date data;
    private Float kwotaPodstawowa;
    private Float kwotaDodatkowa;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getKwotaPodstawowa() {
        return kwotaPodstawowa;
    }

    public void setKwotaPodstawowa(Float kwotaPodstawowa) {
        this.kwotaPodstawowa = kwotaPodstawowa;
    }

    public Float getKwotaDodatkowa() {
        return kwotaDodatkowa;
    }

    public void setKwotaDodatkowa(Float kwotaDodatkowa) {
        this.kwotaDodatkowa = kwotaDodatkowa;
    }

    public static ObservableList<Wynagrodzenie> getDlaPracownika(Connection conn, Integer idPracownika) {
        ObservableList<Wynagrodzenie> listaWynagrodzen = FXCollections.observableArrayList();
        String sql = "SELECT DATA, KWOTA_PODSTAWOWA, KWOTA_DODATKOWA FROM WYNAGRODZENIA WHERE ID_PRACOWNIKA = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPracownika);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Wynagrodzenie wynagrodzenie = new Wynagrodzenie();
                wynagrodzenie.data = rs.getDate(1);
                wynagrodzenie.kwotaPodstawowa = rs.getFloat(2);
                wynagrodzenie.kwotaDodatkowa = rs.getFloat(3);

                listaWynagrodzen.add(wynagrodzenie);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad z dostepem do bazy");
            alert.setContentText("Szczegoly: " + exc.getMessage());
            alert.showAndWait();
        }
        return listaWynagrodzen;
    }

}
