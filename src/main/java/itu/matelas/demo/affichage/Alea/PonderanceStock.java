package itu.matelas.demo.affichage.Alea;

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
@Table(name = "ponderance_stock")
public class PonderanceStock {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private Produit idProduit;

    @Column(name = "moyenne")
    private BigDecimal moyenne;

    @Column(name = "restant")
    private Long restant;

}