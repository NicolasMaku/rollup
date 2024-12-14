package itu.matelas.demo.block;

import itu.matelas.demo.mvtStockmp.MvtStockMpDTO;
import itu.matelas.demo.util.Dbconnect;
import itu.matelas.demo.util.Generateur;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;
import java.time.LocalDate;

import static itu.matelas.demo.util.Generateur.*;

@Getter
@Setter
public class BlockDTO {
    private Integer id;

    private Integer blockMere;
    private Double longueur;

    private Double largeur;

    private Double hauteur;

    private LocalDate dateEntree;

    private Integer etat = 0;
    private Double prixRevient;
    private int idMachine;
    private Double prTheorique;

    private static final Random random = new Random();

    public double getVolume() {
        return getLongueur() * getLargeur() * getHauteur();
    }

    public BlockDTO() {
    }

//    Constructeur hoanle random
    public BlockDTO(double coutRevientUnitaire) {
        longueur = generateDecimal(5.0, 7.0, 2);
        largeur = generateDecimal(20.0, 25.0, 2);
        hauteur = generateDecimal(10.0, 15.0, 2);
        dateEntree = genererDateOuvrable(LocalDate.of(2022, 1, 1), LocalDate.of(2024, 12, 31));
        etat = random.nextInt(2); // Etat (0 ou 1)
        idMachine = random.nextInt(1,5);
        prTheorique = 0.0; // je recalcule ce champs lors de l'import des blocks avec check achat dispo
        prixRevient = coutRevientUnitaire*getVolume();
    }



    public BlockDTO(Integer id, Integer blockMere, Double longueur, Double largeur, Double hauteur,
                 LocalDate dateEntree, Integer etat, Double prixRevient, int idMachine, Double prTheorique) {
        this.id = id;
        this.blockMere = blockMere;
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.dateEntree = dateEntree;
        this.etat = etat;
        this.prixRevient = prixRevient;
        this.idMachine = idMachine;
        this.prTheorique = prTheorique;
    }

    public static double getMoyennePr(Connection conn) throws SQLException {
        boolean isOpen = false;
        double moyennePr = 0;

        if (conn == null) {
            isOpen = true;
            conn = Dbconnect.dbConnect();
        }

        String query = "select (sum(prix_revient)/sum(longueur*largeur*hauteur)) as moyenne from block;";
        try ( PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            moyennePr = resultSet.getDouble("moyenne");
        } catch (Exception e) {
            throw e;
        }

        if (isOpen) {
            conn.close();
        }

        return moyennePr;
    }

    public static BlockDTO[] genererBlockRandom(int numberOfBlocks) throws SQLException {
        BlockDTO[] blocks = new BlockDTO[numberOfBlocks];
        double pr_initial = BlockDTO.getMoyennePr(null);

        for (int i = 0; i < numberOfBlocks; i++) {

            double pourcentage = generateDouble(-10,10);
            BlockDTO block = new BlockDTO((pr_initial)*(1 + pourcentage/100));
            blocks[i] = block;
        }

        return blocks;
    }

//    Importer dans la base
    public static void importBlock(BlockDTO[] blocks) throws SQLException {

        String query = "INSERT INTO block (longueur, largeur, hauteur, date_entree, etat, prix_revient, id_machine, pr_theorique) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Dbconnect.dbConnect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);
            for (int i = 0; i < blocks.length; i++) {
                pstmt.setDouble(1, blocks[i].getLongueur());
                pstmt.setDouble(2, blocks[i].getLargeur());
                pstmt.setDouble(3, blocks[i].getHauteur());
                pstmt.setDate(4, Date.valueOf(blocks[i].getDateEntree()));
                pstmt.setDouble(5, blocks[i].getEtat());
                pstmt.setDouble(6, blocks[i].getPrixRevient());
                pstmt.setDouble(7, blocks[i].getIdMachine());
                pstmt.setDouble(8, blocks[i].getPrTheorique());
                pstmt.addBatch();

                if (i % 20000 == 0) { // Regrouper par lot de 10000
                    pstmt.executeBatch();
                }


            }
            pstmt.executeBatch();
            conn.commit();
        }
    }

    public static void genererCsv(BlockDTO[] blocks) throws SQLException, IOException {

        String csvFilePath = "C:\\Users\\nicol\\Documents\\0-ITU\\S5\\architectureLogiciel\\matelasSpring\\src\\main\\java\\itu\\matelas\\demo\\block\\blockGeneration\\blocks_temp.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("longueur,largeur,hauteur,date_entree,etat,prix_revient,id_machine\n");
            for (BlockDTO block : blocks) {
                String line = String.format(Locale.US,"%f,%f,%f,%s,%d,%f,%d\n",
                        block.getLongueur(),
                        block.getLargeur(),
                        block.getHauteur(),
                        block.getDateEntree(),
                        block.getEtat(),
                        block.getPrixRevient(),
                        block.getIdMachine());
//                System.out.println(line);
                writer.write(line);
            }
        }



//        Si besoin d'insertion dans la base
//        String url = "jdbc:postgresql://localhost:5432/matelas2";
//        String user = "postgres";
//        String password = "root";
//        String copyQuery = "COPY block (longueur, largeur, hauteur, date_entree, etat, " +
//                "prix_revient, id_machine) FROM STDIN WITH CSV";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             FileReader fileReader = new FileReader(csvFilePath);) {
//            CopyManager copyManager = new CopyManager((BaseConnection) conn);
//            copyManager.copyIn(copyQuery, fileReader);
//        }
    }


    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        BlockDTO[] blocks = genererBlockRandom(1000000);
        genererCsv(blocks);

        System.out.println("Temps d execution :" + (System.currentTimeMillis() - startTime));

//        Pour la gestion des achats lors de l'import de block
//        MvtStockMpDTO[] sorties = null;
//        try {
//            sorties = MvtStockMpDTO.genererStock(blocks);
//            importBlock(blocks);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
    }

}
