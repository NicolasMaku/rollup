package itu.matelas.demo.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class RecetteDTO {

    private Integer id;

    private LocalDate dateDebut;

    private BigDecimal quantite;

    private Integer idProduit;

    public static HashMap<Integer, Double > getRecette() throws SQLException {
        HashMap<Integer, Double> recette = new HashMap<>();
        String sql = "select * from recette";

        String url = "jdbc:postgresql://localhost:5432/matelas2";
        String user = "postgres";
        String password = "root";

        try (
             Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                int cle = resultSet.getInt("id_produit");
                double value = resultSet.getDouble("quantite");
                recette.put(cle, value);
            }

        }

        return recette;
    }
}
