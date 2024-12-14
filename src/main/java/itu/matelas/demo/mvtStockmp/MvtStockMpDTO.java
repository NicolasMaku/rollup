package itu.matelas.demo.mvtStockmp;

import itu.matelas.demo.block.Block;
import itu.matelas.demo.block.BlockDTO;
import itu.matelas.demo.configuration.RecetteDTO;
import itu.matelas.demo.util.Dbconnect;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MvtStockMpDTO {
    private Integer id;

    private double entree;

    private double sortie;

    private Integer idProduit;

    private Double prixRevient;

    private LocalDate date;

    public static List<Integer> productType() {
        List<Integer> id_produits = new ArrayList<>();
        String sqlProduit = "select distinct id_produit from mvt_stock_mp where entree is not null";

        try (
                Connection conn = Dbconnect.dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(sqlProduit);
                ResultSet resultSet = pstmt.executeQuery();
        ) {


            while (resultSet.next()) {
                id_produits.add(resultSet.getInt("id_produit"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id_produits;
    }

    public static Map<Integer, List<MvtStockMpDTO>> findAll() {
        Map<Integer, List<MvtStockMpDTO>> achats = new HashMap<>();

        List<Integer> productType = productType();


        for (int idProduit : productType) {
            String sql = "select * from mvt_stock_mp where entree>0 and id_produit=" + idProduit + " order by date asc";
            List<MvtStockMpDTO> liste = new ArrayList<>();

            try (
                    Connection conn = Dbconnect.dbConnect();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet resultSet = pstmt.executeQuery();
            ) {


                while (resultSet.next()) {
                    MvtStockMpDTO mvt = new MvtStockMpDTO();
                    int id = resultSet.getInt("id_mvt_stock_mp");
                    double entree = resultSet.getDouble("entree");
                    double sortie = resultSet.getDouble("sortie");
                    double prixRevient = resultSet.getDouble("prix_revient");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format personnalisé
                    LocalDate date = LocalDate.parse(resultSet.getString("date"), formatter);
                    mvt.setId(id);
                    mvt.setEntree(entree);
                    mvt.setSortie(sortie);
                    mvt.setIdProduit(idProduit);
                    mvt.setPrixRevient(prixRevient);
                    mvt.setDate(date);
                    liste.add(mvt);
                }

                achats.put(idProduit, liste);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return achats;
    }
    public static MvtStockMpDTO[] genererStock(BlockDTO[] blocks) throws Exception {
        List<MvtStockMpDTO> sorties = new ArrayList<>();

        Map<Integer, List<MvtStockMpDTO>> achats = findAll();
        Map<Integer, Double> recettes = RecetteDTO.getRecette();
        Map<Integer, Integer> indice = new HashMap<>();

        for (Map.Entry<Integer, Double> entry : recettes.entrySet() ) {
//            System.out.println("getkey : " + entry.getKey());
            indice.put(entry.getKey(), 0);
        }


        for (BlockDTO block : blocks) {
            double valeur = 0;

            for (Map.Entry<Integer, Double> entry : recettes.entrySet() ) {

                double qte_totale = 0;
                int cle = entry.getKey();
//                System.out.println(cle);
                int cur_indice = indice.get(cle);
//                System.out.println("curr indice : " + cur_indice);
                MvtStockMpDTO mvt = achats.get(cle).get(cur_indice);
                double qteRest = mvt.getEntree();
                double qteNecessaire = block.getVolume()*entry.getValue();
//                System.out.println(qteNecessaire);
//                valeur += qteRest*mvt.getPrixRevient();x

                if (mvt.getDate().isAfter(block.getDateEntree())) {
                    System.out.println("Date achat : " + mvt.getDate());
                    System.out.println("Date production : " + block.getDateEntree());
                    throw new Exception("Stocks insuffisants" + cle);
                }

//                System.out.println("rest: " + qteRest);
//                System.out.println("necess: " + qteNecessaire);

                while (qteRest < qteNecessaire) {
                    valeur += qteRest*mvt.getPrixRevient();
                    MvtStockMpDTO sortie = new MvtStockMpDTO();
                    sortie.setSortie(qteRest);
                    sortie.setIdProduit(cle);
                    sortie.setPrixRevient(mvt.getPrixRevient());
                    sortie.setDate(mvt.getDate());
                    if (sortie.getSortie() > 0)
                        sorties.add(sortie);

//                    System.out.println(" prix : " + qteRest*mvt.getPrixRevient());
                    mvt.setEntree(0);
                    cur_indice += 1;
                    qteNecessaire = qteNecessaire - qteRest;
                    mvt = achats.get(cle).get(cur_indice);

                    if (mvt.getDate().isAfter(block.getDateEntree()))
                        throw new Exception("Stocks insuffisants");

                    qteRest = mvt.getEntree();
                    qte_totale += qteRest;
                }

                mvt.setEntree(qteRest - qteNecessaire);

//                System.out.println(" prix : " +mvt.getPrixRevient()*qteNecessaire);
                valeur += qteNecessaire*mvt.getPrixRevient();
                MvtStockMpDTO sortieF = new MvtStockMpDTO();
                sortieF.setSortie(qteNecessaire);
                sortieF.setIdProduit(cle);
                sortieF.setPrixRevient(mvt.getPrixRevient());
                sortieF.setDate(mvt.getDate());
                if (sortieF.sortie > 0)
                    sorties.add(sortieF);

//                System.out.println("prix theorque : " + valeur);
                qte_totale += qteRest;
                indice.replace(cle, cur_indice);

//                sortie.setSortie(qte_totale);
//                sortie.setPrixRevient(valeur/qte_totale);
//                sortie.setIdProduit(cle);

//                sorties.add(sortie);
            }
            block.setPrTheorique(valeur);
        }

        return sorties.toArray(new MvtStockMpDTO[0]);
    }


    public static void insertMvtStockMp(MvtStockMpDTO[] mvtStockMpDTOs ) {
        String insertSQL = """
            INSERT INTO mvt_stock_mp (entree, sortie, id_produit, prix_revient, date)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = Dbconnect.dbConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            int batchSize = 1000;
            int count = 0;

            for (MvtStockMpDTO item : mvtStockMpDTOs) {
                preparedStatement.setDouble(1, item.getEntree());
                preparedStatement.setDouble(2, item.getSortie());
                preparedStatement.setInt(3, item.getIdProduit());
                preparedStatement.setDouble(4, item.getPrixRevient());
                preparedStatement.setObject(5, item.getDate()); // Compatible avec LocalDate

                preparedStatement.addBatch();
                count++;

                // Execute le batch tous les 1000 éléments
                if (count % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }

            // Exécute les restes du batch
            preparedStatement.executeBatch();

            System.out.println("Insertion réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }
}