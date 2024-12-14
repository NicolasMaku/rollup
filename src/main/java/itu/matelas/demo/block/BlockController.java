package itu.matelas.demo.block;

import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFilleRepository;
import itu.matelas.demo.produit.Produit;
import itu.matelas.demo.produit.ProduitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/block")
public class BlockController{

    private final BlockService blockService;
    private final BlockRepository blockRepository;
    private final ProduitRepository produitRepository;
    private final MvtStockFilleRepository mvtStockFilleRepository;

    public BlockController(BlockService blockService,
                           BlockRepository blockRepository,
                           ProduitRepository produitRepository,
                           MvtStockFilleRepository mvtStockFilleRepository) {
        this.blockService = blockService;
        this.blockRepository = blockRepository;
        this.produitRepository = produitRepository;
        this.mvtStockFilleRepository = mvtStockFilleRepository;
    }

    @GetMapping("/form")
    public ModelAndView Form() {
        ModelAndView mv = new ModelAndView("layout");
        mv.addObject("page", "block-form");
        return mv;
    }

    @PostMapping("/insert")
    public String insertion(
            @RequestParam("longueur") Double longueur,
            @RequestParam("largeur") Double largeur,
            @RequestParam("hauteur") Double hauteur,
            @RequestParam("dateEntree") LocalDate dateEntree,
            @RequestParam("prixRevient") Double prixRevient
    ) {
        Block block = new Block(longueur, largeur, hauteur, dateEntree, prixRevient);
        blockRepository.save(block);
        return "redirect:/block/form";
    }

    @GetMapping("/update-form")
    public ModelAndView updateForm() {
        ModelAndView mv = new ModelAndView("layout");
        mv.addObject("page", "block-update-form");
        mv.addObject("blocks", blockRepository.findAll());
        return mv;
    }

    @PostMapping("/update")
    public String update(
            @RequestParam("blockMere") Block block,
            @RequestParam("prixRevient") Double prixRevient
    ) throws Exception {
        if (prixRevient < 0) {
            throw new Exception("Prix de revient ne doit pas etre negatif");
        }

        double proportion = prixRevient/block.getPrixRevient();
        Famille famille = blockService.getFamilleBlock(block);
        System.out.println("Proportion : " + proportion);

        block.setPrixRevient(prixRevient);

        System.out.println("BlockDescendant " + famille.getBlockFilles().size());
        for (Block blockF : famille.getBlockFilles()) {
            System.out.println(blockF.getId());
            blockF.setPrixRevient(blockF.getPrixRevient()*proportion);
        }

        System.out.println("MvtStockDescendant " + famille.getMvtStockFilles().size());
        for (MvtStockFille mvtf : famille.getMvtStockFilles()) {
            mvtf.setPrixRevient(mvtf.getPrixRevient()*proportion);
        }

        blockRepository.save(block);
        blockRepository.saveAll(famille.getBlockFilles());
        mvtStockFilleRepository.saveAll(famille.getMvtStockFilles());

        return "redirect:/block/update";
    }

    @GetMapping("/transf-form")
    public ModelAndView transformForm() {
        ModelAndView mv = new ModelAndView("layout");
        mv.addObject("produits", produitRepository.findAll());
        mv.addObject("blocks", blockRepository.findByEtat(0));
        mv.addObject("page", "transformation-form");
        return mv;
    }

    @PostMapping("/transformer")
    public String transformer(
            @RequestParam("blockMere") Block mere,
            @RequestParam("usuels[]") List<Produit> usuels,
            @RequestParam("qte[]") List<Integer> quantites,
            @RequestParam("daty") LocalDate dateTransformation,
            @RequestParam("longueur") String longueur,
            @RequestParam("largeur") String largeur,
            @RequestParam("hauteur") String hauteur
            ) throws Exception {

        Map<Produit, Integer> sortie = new HashMap<>();
        for (int i = 0; i < usuels.size(); i++) {
            sortie.put(usuels.get(i), quantites.get(i) );
        }

        Double dlongueur = Block.convert(longueur);
        Double dlargeur = Block.convert(largeur);
        Double dhauteur = Block.convert(hauteur);
        Block reste = blockService.transformation(mere, sortie, dlongueur, dlargeur, dhauteur, dateTransformation );
        return "redirect:/block/transf-form";
    }
}
