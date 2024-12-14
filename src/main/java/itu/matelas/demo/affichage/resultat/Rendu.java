package itu.matelas.demo.affichage.resultat;

import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Rendu {
    double ca;
    double prixRevient;
    List<MvtStockFille> mvtStockFilleTempo;

    public double getBenefice() {
        return getCa() - getPrixRevient();
    }
    public double getSommeRenduReste() {
        double somme = 0;
        for(MvtStockFille mvt : mvtStockFilleTempo ) {
            somme += (mvt.getEntree()-mvt.getSortie())*mvt.getPrixRevient();
        }

        return somme;
    }
}
