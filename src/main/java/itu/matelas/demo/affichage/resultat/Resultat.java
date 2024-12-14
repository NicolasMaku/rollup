package itu.matelas.demo.affichage.resultat;

import itu.matelas.demo.EtatStock.EtatStock;
import itu.matelas.demo.EtatStock.mvtBlockUsuel.VueBlockOriginel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Resultat {
    List<EtatStock> etatStocks;
    Rendu p1;
    Rendu p2;
    Rendu p3;
    List<VueBlockOriginel> originels;

    public Resultat() {
    }

    public Resultat(List<EtatStock> etatStocks, Rendu p1, Rendu p2, Rendu p3, List<VueBlockOriginel> originels) {
        this.etatStocks = etatStocks;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.originels = originels;
    }

    public double getValeurStock() {
        double valeur = 0;
        for (EtatStock es : etatStocks) {
            valeur += es.getValeur();
        }
        return valeur;
    }
}
