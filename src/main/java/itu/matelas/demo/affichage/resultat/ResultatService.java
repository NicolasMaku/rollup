package itu.matelas.demo.affichage.resultat;

import itu.matelas.demo.EtatStock.EtatStock;
import itu.matelas.demo.EtatStock.EtatStockRepository;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFilleRepository;
import itu.matelas.demo.MvtStock.MvtStockRepository;
import itu.matelas.demo.block.Block;
import itu.matelas.demo.block.BlockRepository;
import itu.matelas.demo.produit.Produit;
import itu.matelas.demo.produit.ProduitRepository;
import itu.matelas.demo.produit.ProduitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultatService {

    private final MvtStockFilleRepository mvtStockFilleRepository;
    private final EtatStockRepository etatStockRepository;
    private final ProduitRepository produitRepository;
    private final ProduitService produitService;
    private final BlockRepository blockRepository;
    private final MvtStockRepository mvtStockRepository;


    public ResultatService(MvtStockFilleRepository mvtStockFilleRepository,
                           EtatStockRepository etatStockRepository,
                           ProduitRepository produitRepository, ProduitService produitService,
                           BlockRepository blockRepository,
                           MvtStockRepository mvtStockRepository) {
        this.mvtStockFilleRepository = mvtStockFilleRepository;
        this.etatStockRepository = etatStockRepository;
        this.produitRepository = produitRepository;
        this.produitService = produitService;
        this.blockRepository = blockRepository;
        this.mvtStockRepository = mvtStockRepository;
    }

    public Rendu calculMontantTotal() {
        double montant = 0;
//        List<MvtStockFille> mvtfs = mvtStockFilleRepository.findAll();
//        for(MvtStockFille mvtf : mvtfs) {
//            montant += mvtf.getEntree() * mvtf.getPrixRevient();
//        }

        List<EtatStock> etatStocks = etatStockRepository.findAll();
        for(EtatStock es : etatStocks ) {
            montant += es.getReste() * es.getIdProduit().getPrixVente();
        }

        Rendu rendu = new Rendu();
        rendu.setCa(montant);
        rendu.setPrixRevient(calculPRp1());
        return rendu;
    }

    public double calculPRp1() {
        double pr = 0;
        List<MvtStockFille> mvtStockFilles = mvtStockFilleRepository.findAll();
        for (MvtStockFille mvtf : mvtStockFilles) {
            pr += mvtf.getEntree()*mvtf.getPrixRevient();
        }
        return pr;
    }

    public Rendu calculRendu(Produit usuel) {
        List<Block> restants = blockRepository.findByEtat(0);
        int quantitePossible = 0;
        double pr = 0;

        List<MvtStockFille> mvtStockFilles = new ArrayList<>();
        for (Block block : restants) {
            quantitePossible += block.quantitePossible(usuel);
            MvtStockFille simulated = block.simuleFille(usuel);
            pr += simulated.getEntree() * simulated.getPrixRevient();
            mvtStockFilles.add(simulated);
        }

        Rendu rendu = new Rendu();
        rendu.setMvtStockFilleTempo(mvtStockFilles);
        rendu.setCa(calculMontantTotal().getCa() + quantitePossible*usuel.getPrixVente());
        rendu.setPrixRevient(calculMontantTotal().getPrixRevient() + pr);
        return rendu;
    }
    public Rendu calculMontantP2() {

        Produit rentable = produitService.getRentable();
        return calculRendu(rentable);
    }

//    public Map<Block, Integer> calculMontantP2Details() {
//        List<Block> restants = blockRepository.findByEtat(0);
//        Produit rentable = produitService.getRentable();
//
//        Map<Block, Integer> map = new HashMap<>();
//        for (Block block : restants) {
//            map.put(block, block.quantitePossible(rentable));
//        }
//
//        return map;
//    }
//
//    public double calculPRp2() {
//        double pr = 0;
//        List<MvtStockFille> mvtStockFilles = mvtStockFilleRepository.findAll();
//        for (MvtStockFille mvtf : mvtStockFilles) {
//            pr += mvtf.getEntree()*mvtf.getPrixRevient();
//        }
//        return pr;
//    }

    public Rendu calculMontantP3() {
        Produit minimum = produitService.getMinimum();
        return calculRendu(minimum);
    }

    public Map<Block, Integer> calculMontantP3Details() {
        List<Block> restants = blockRepository.findByEtat(0);
        Produit rentable = produitService.getMinimum();

        Map<Block, Integer> map = new HashMap<>();
        for (Block block : restants) {
            map.put(block, block.quantitePossible(rentable));
        }

        return map;
    }
}
