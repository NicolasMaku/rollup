package itu.matelas.demo.block;

import itu.matelas.demo.MvtStock.MvtStock;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFilleRepository;
import itu.matelas.demo.MvtStock.MvtStockRepository;
import itu.matelas.demo.configuration.ConfigurationRepository;
import itu.matelas.demo.produit.Produit;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlockService {

    private final BlockRepository blockRepository;
    private final MvtStockRepository mvtStockRepository;
    private final MvtStockFilleRepository mvtStockFilleRepository;
    private final ConfigurationRepository configurationRepository;

    public BlockService(BlockRepository blockRepository,
                        MvtStockRepository mvtStockRepository,
                        MvtStockFilleRepository mvtStockFilleRepository,
                        ConfigurationRepository configurationRepository) {
        this.blockRepository = blockRepository;
        this.mvtStockRepository = mvtStockRepository;
        this.mvtStockFilleRepository = mvtStockFilleRepository;
        this.configurationRepository = configurationRepository;
    }

    @Transactional
    public Block transformation(Block mere, Map<Produit, Integer> sortie, Double longueur, Double largeur, Double hauteur, LocalDate dateTransformation) throws Exception {
        System.out.println("a debute");
        Block reste = new Block(longueur, largeur, hauteur, dateTransformation, mere);
        reste.setPrixRevient(reste.calculPR());

        double marge = Double.parseDouble(configurationRepository.findByCle("marge").getValue());
        check_transformation(mere, sortie, reste, marge);

        MvtStock mvt = new MvtStock();
        mvt.setDaty(dateTransformation);
        mvt.setDesignation("Resultat de transformation");
        mvt.setOrigine(mere);
        mvtStockRepository.save(mvt);


        List<MvtStockFille> mvts = new ArrayList<MvtStockFille>();
        for (Map.Entry<Produit, Integer> entry : sortie.entrySet()) {
            Produit key = entry.getKey();
            Integer val = entry.getValue();

            if (val == 0)
                continue;

            MvtStockFille mvtf = new MvtStockFille();
            mvtf.setEntree(val);
            mvtf.setIdMere(mvt);
            mvtf.setIdProduit(key);
            mvtf.setPrixRevient(key.calculPR(mere));
            mvts.add(mvtf);
        }
        mvtStockFilleRepository.saveAll(mvts);
        System.out.println("Mouvement : " + mvts.size());

        mere.setEtat(1);
        blockRepository.save(mere);
        if (reste.getVolume() != 0)
            blockRepository.save(reste);
        return reste;
    }

    public void check_transformation(Block mere, Map<Produit, Integer> sortie, Block reste, double marge) throws Exception {
        double volumeTotalContenu = 0;
        for (Map.Entry<Produit, Integer> entry : sortie.entrySet()) {
            Produit key = entry.getKey();
            Integer val = entry.getValue();

//            if (mere.quantitePossible(key) < val)
//                throw new Exception("La quantite de usuel inserer invalide");

            volumeTotalContenu += key.getVolume() * val;
        }

        double volumeRest_normal = mere.getVolume() - volumeTotalContenu;
        double margeSup = volumeRest_normal + (mere.getVolume()*marge)/100;
        double margeInf = volumeRest_normal - (mere.getVolume()*marge)/100;
//        double margeSup = volumeRest_normal + (volumeRest_normal*marge)/100;
//        double margeInf = volumeRest_normal - (volumeRest_normal*marge)/100;

        if (reste.getVolume() < margeInf || reste.getVolume() > margeSup)
            throw new Exception("Le reste depasse la marge de " + marge + " (le teo=" + reste.getVolume() + "; normale=" + volumeRest_normal + ") : " + margeSup + " > " + margeInf );

        System.out.println("Le reste depasse la marge de " + marge + " (le teo=" + reste.getVolume() + "; normale=" + volumeRest_normal + ") : " + margeSup + " > " + margeInf);

    }

    public Famille getFamilleBlock(Block block) {
        Famille fam = new Famille();

        List<Block> famille = new ArrayList<>();
        List<MvtStockFille> familleMvt = new ArrayList<>();
        Block mere = block;
        familleMvt.addAll(mvtStockFilleRepository.findByIdMere_Origine(mere));
        Block fille = blockRepository.findByBlockMere(mere);
        int number = 0;

        while (fille != null) {
            famille.add(fille);
            List<MvtStockFille> tempo = new ArrayList<>();
            tempo = mvtStockFilleRepository.findByIdMere_Origine(fille);
            number += tempo.size();
            familleMvt.addAll(tempo);

            mere = fille;
            fille = blockRepository.findByBlockMere(mere);
        }

        System.out.println("taty: " + number);
        fam.setBlockFilles(famille);
        fam.setMvtStockFilles(familleMvt);
        return fam;
    }

    public void importBlock(BlockDTO[] blocks) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/matelas";
        String user = "postgres";
        String password = "root";


        String query = "INSERT INTO block (id_block, block_mere, longueur, largeur, hauteur, date_entree, etat, prix_revient, id_machine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false); // Active le batch processing
            for (int i = 0; i < blocks.length; i++) {
                pstmt.setInt(1, blocks[i].getId());
                pstmt.setInt(2, blocks[i].getBlockMere());
                pstmt.setDouble(3, blocks[i].getLongueur());
                pstmt.setDouble(4, blocks[i].getLargeur());
                pstmt.setDouble(5, blocks[i].getHauteur());
                pstmt.setDate(6, Date.valueOf(blocks[i].getDateEntree()));
                pstmt.setDouble(7, blocks[i].getEtat());
                pstmt.setDouble(8, blocks[i].getPrixRevient());
                pstmt.setDouble(9, blocks[i].getIdMachine());
                pstmt.addBatch();

                if (i % 1000 == 0) { // Regrouper par lot de 1000
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch(); // Exécute les données restantes
            conn.commit();
        }
    }

}
