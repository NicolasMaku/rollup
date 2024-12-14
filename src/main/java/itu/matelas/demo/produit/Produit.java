package itu.matelas.demo.produit;

import itu.matelas.demo.Format.Format;
import itu.matelas.demo.block.Block;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit", nullable = false)
    private Integer id;

    @Column(name = "prix_vente")
    private Double prixVente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_format")
    private Format idFormat;

    @Column(name = "nom", length = 40)
    private String nom;

    public double getVolume() {
        return getIdFormat().getVolume();
    }

    public double calculPR(Block mere) {
        return (getVolume() * mere.getPrixRevient())/mere.getVolume();
    }
}