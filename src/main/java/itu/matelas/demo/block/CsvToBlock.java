package itu.matelas.demo.block;

import itu.matelas.demo.block.BlockDTO;
import itu.matelas.demo.mvtStockmp.MvtStockMpDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CsvToBlock {

    public static BlockDTO[] blocksFromCsv(String csvFile) {
        String delimiter = ";"; // Délimiteur utilisé dans le fichier CSV
        List<BlockDTO> blocks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(delimiter);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // Parse les attributs
                LocalDate dateEntree = LocalDate.parse(attributes[0], formatter); // Assure-toi que le format de la date est correct
                Double longueur = Double.parseDouble(attributes[1]);
                Double largeur = Double.parseDouble(attributes[2]);
                Double hauteur = Double.parseDouble(attributes[3]);
                Double prixRevient = Double.parseDouble(attributes[4]);
                Integer idMachine = Integer.parseInt(attributes[5]);
                int etat = 0;

                // Crée l'objet Block (id et blockMere fixés à null pour cet exemple)
                BlockDTO block = new BlockDTO(null, null, longueur, largeur, hauteur, dateEntree, etat, prixRevient, idMachine, 0.0);
                blocks.add(block);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erreur de parsing des données : " + e.getMessage());
        }

        blocks.sort(Comparator.comparing(BlockDTO::getDateEntree));
        return blocks.toArray(new BlockDTO[0]);
    }

    public static void main(String[] args) throws SQLException {
        String csvFile = "C:\\Users\\nicol\\Documents\\0-ITU\\S5\\architectureLogiciel\\matelasSpring\\src\\main\\java\\itu\\matelas\\demo\\block\\data_examen.csv"; // Remplace par le chemin de ton fichier CSV


        // Convertit la liste en tableau
        BlockDTO[] blocksArray = blocksFromCsv(csvFile);
//        BlockDTO[] blocksArray = BlockDTO.genererBlockRandom(1000000);

        // Affiche les blocs pour vérifier
//        for (BlockDTO block : blocksArray) {
//            System.out.println(block.getPrixRevient());
//        }

//        for (int i = 0; i < blocksArray.length; i++) {
//            System.out.println(blocksArray[i].getPrixRevient());
//        }

        System.out.println("Length : " + blocksArray.length);
        MvtStockMpDTO[] sorties = null;
        try {
            sorties = MvtStockMpDTO.genererStock(blocksArray);
//            MvtStockMpDTO.insertMvtStockMp(sorties);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        for (int i = 0; i < sorties.length; i++) {
//            System.out.println( i + " : " + sorties[i].getSortie());
//        }

//        BlockDTO.importBlock(blocksArray);
    }
}
