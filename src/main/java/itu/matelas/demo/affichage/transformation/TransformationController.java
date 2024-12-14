package itu.matelas.demo.affichage.transformation;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transformation")
public class TransformationController {
    @PostMapping("/process")
    public void transformer() {

    }
}
