package itu.matelas.demo.block;

import itu.matelas.demo.MvtStock.MvtStock;
import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import itu.matelas.demo.MvtStock.MvtStockRepository;
import itu.matelas.demo.produit.Produit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_block", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_mere")
    private Block blockMere;

    @Column(name = "longueur")
    private Double longueur;

    @Column(name = "largeur")
    private Double largeur;

    @Column(name = "hauteur")
    private Double hauteur;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "etat")
    private Integer etat = 0;

    @Column(name = "prix_revient")
    private Double prixRevient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_machine")
    private Machine idMachine;

    @Column(name = "pr_theorique")
    private Double prTheorique;

    public Block() {
    }

    public Block(Double longueur, Double largeur, Double hauteur, LocalDate dateEntree, Double prixRevient) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.dateEntree = dateEntree;
        this.prixRevient = prixRevient;
    }

    public Block(Double longueur, Double largeur, Double hauteur, LocalDate dateEntree, Block blockMere) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.dateEntree = dateEntree;
        this.blockMere = blockMere;
    }

    public double getVolume() {
        return getLongueur() * getLargeur() * getHauteur();
    }
    public int quantitePossible(Produit usuel) {
        double dquantite = getVolume()/usuel.getVolume();
        int quantite = (int)dquantite;
        if (dquantite - (int)dquantite > 0.999)
            quantite += 1;
        return quantite;
    }

    public MvtStockFille simuleFille(Produit usuel) {
        int quantite = quantitePossible(usuel);
        MvtStockFille mvtf = new MvtStockFille();
        mvtf.setIdProduit(usuel);
        mvtf.setEntree(quantite);
        mvtf.setPrixRevient(usuel.calculPR(this));

        return mvtf;
    }

//    public int quantitePossible(Produit usuel) {
//        int quantite = 0;
//        int longueur = (int)(getLongueur()/usuel.getIdFormat().getLongueur());
//        int largeur = (int)(getLargeur()/usuel.getIdFormat().getLargeur());
//        int hauteur = (int)(getHauteur()/usuel.getIdFormat().getHauteur());
//
//        quantite = longueur * largeur * hauteur;
//        return quantite;
//    }

    public double calculPR() throws Exception {
        if (getBlockMere() == null)
            throw new Exception("Block mere inexistant");

        return (getVolume() * getBlockMere().getPrixRevient())/getBlockMere().getVolume();
    }

    public boolean testNullite() {
        if (getLargeur() == 0 || getLongueur() == 0 || getHauteur() == 0)
            return true;
        return false;
    }

//    public void updateMe(BlockRepository blockRepository, MvtStockRepository mvtStockRepository,  ,  double proportion) {
//        Block fille = blockRepository.findByBlockMere(this);
//        MvtStock mvt = mvtStockRepository.findByOrigine(this);
//        List<MvtStockFille> sorties = m
//    }

    public static double convert(String input) {
        // Supprimer les espaces éventuels avant et après la chaîne
        input = input.trim();

        // Si l'entrée est vide, renvoyer 0 (ou une valeur par défaut)
        if (input.isEmpty()) {
            throw new IllegalArgumentException("L'entrée ne peut pas être vide.");
        }

        // Récupérer l'unité et la valeur
        double value;
        String unit = "";

        // Identifier l'unité en fonction des caractères à la fin de la chaîne
        if (input.endsWith("cm")) {
            value = Double.parseDouble(input.substring(0, input.length() - 2));
            unit = "cm";
        } else if (input.endsWith("mm")) {
            value = Double.parseDouble(input.substring(0, input.length() - 2));
            unit = "mm";
        } else if (input.endsWith("m")) {
            value = Double.parseDouble(input.substring(0, input.length() - 1));
            unit = "m";
        } else {
            // Si aucune unité n'est précisée, on suppose que c'est en mètres
            value = Double.parseDouble(input);
            unit = "m";  // Par défaut en mètres
        }

        // Convertir la valeur en mètres en fonction de l'unité
        switch (unit) {
            case "cm":
                return value / 100; // Conversion de centimètres en mètres
            case "mm":
                return value / 1000; // Conversion de millimètres en mètres
            case "m":
                return value; // Si c'est déjà en mètres, pas de conversion
            default:
                // Cas où il y a une erreur ou une unité non supportée
                throw new IllegalArgumentException("Unité non supportée: " + unit);
        }
    }
}