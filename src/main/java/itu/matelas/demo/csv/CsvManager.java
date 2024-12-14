package itu.matelas.demo.csv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileReader;
import java.io.IOException;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class CsvManager {
    public static void main(String[] args) {
        // Configuration de la connexion
        String url = "jdbc:postgresql://localhost:5432/matelas";
        String user = "postgres";
        String password = "root";
        String cheminCSV = "C:\\Users\\nicol\\Documents\\0-ITU\\S5\\architectureLogiciel\\dmp\\block.csv";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Convertir la connexion en BaseConnection pour CopyManager
            CopyManager copyManager = new CopyManager((BaseConnection) conn);

            // Exécuter la commande COPY
            FileReader fileReader = new FileReader(cheminCSV);
            String table = "block"; // Remplacez par le nom de votre table
            copyManager.copyIn("COPY " + table + "(id_block, block_mere, longueur, largeur, hauteur, date_entree, etat, prix_revient, id_machine) FROM STDIN WITH (FORMAT CSV, HEADER)", fileReader);

            System.out.println("Importation réussie !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
