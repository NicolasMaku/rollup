package itu.matelas.demo.affichage.resultat;

import itu.matelas.demo.EtatStock.EtatStockRepository;
import itu.matelas.demo.EtatStock.mvtBlockUsuel.VueBlockOriginelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResultatController {

    private final EtatStockRepository etatStockRepository;
    private final ResultatService resultatService;
    private final VueBlockOriginelRepository vueBlockOriginelRepository;

    public ResultatController(EtatStockRepository etatStockRepository, ResultatService resultatService,
                              VueBlockOriginelRepository vueBlockOriginelRepository) {
        this.etatStockRepository = etatStockRepository;
        this.resultatService = resultatService;
        this.vueBlockOriginelRepository = vueBlockOriginelRepository;
    }

    @GetMapping("/resultat")
    public ModelAndView resultat() {
        ModelAndView mv = new ModelAndView("layout");

        Resultat resultat = new Resultat(etatStockRepository.findAll(), resultatService.calculMontantTotal(), resultatService.calculMontantP2(), resultatService.calculMontantP3(), vueBlockOriginelRepository.findAll());
        mv.addObject("resultat", resultat);
        mv.addObject("page","resultat");
        return mv;
    }
}
