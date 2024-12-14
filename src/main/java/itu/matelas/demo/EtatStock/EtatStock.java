package itu.matelas.demo.EtatStock;

import itu.matelas.demo.produit.Produit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "etat_stock")
public class EtatStock {
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private Produit idProduit;

    @Column(name = "reste")
    private Integer reste;

    @Column(name = "entree")
    private Integer entree;

    @Column(name = "sortie")
    private Integer sortie;

    @Column(name = "prix_revient")
    private Double prixRevient;

    public double getValeur() {
        return getIdProduit().getPrixVente()*getReste();
    }

}