package itu.matelas.demo.classement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/classement-machine")
public class ClassementMachineController {

    private final ClassementMachineService classementMachineService;
    private final ClassementMachineRepository classementMachineRepository;

    public ClassementMachineController(ClassementMachineService classementMachineService,
                                       ClassementMachineRepository classementMachineRepository) {
        this.classementMachineService = classementMachineService;
        this.classementMachineRepository = classementMachineRepository;
    }

    @GetMapping("/list-def")
    public ModelAndView listeClassement() {
        ModelAndView mv = new ModelAndView("layout");

        List<ClassementMachine> classement = null;
        classement = classementMachineRepository.findClassementByAnnee();

        mv.addObject("classement", classement);
        mv.addObject("page", "classement");
        mv.addObject("annee", -1);
        return mv;
    }

    @GetMapping("/list")
    public ModelAndView listeClassement(@RequestParam(value = "annee") Integer annee) {
        ModelAndView mv = new ModelAndView("layout");

        List<ClassementMachine> classement = null;

        if (annee < 0)
            classement = classementMachineRepository.findClassementByAnnee();
        else {
            classement = classementMachineRepository.findClassementByAnnee(annee);
        }

        System.out.println(classement);
        if (annee == null && classement == null)
            classement = classementMachineRepository.findClassementByAnnee();

        mv.addObject("classement", classement);
        mv.addObject("page", "classement");
        mv.addObject("annee", annee);

        return mv;
    }
}