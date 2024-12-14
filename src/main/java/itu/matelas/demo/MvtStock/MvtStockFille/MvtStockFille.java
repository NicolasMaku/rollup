package itu.matelas.demo.MvtStock.MvtStockFille;

import itu.matelas.demo.MvtStock.MvtStock;
import itu.matelas.demo.produit.Produit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mvt_stock_fille")
public class MvtStockFille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mvt_stock_fille", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mere")
    private MvtStock idMere;

    @Column(name = "entree")
    private Integer entree = 0;

    @Column(name = "sortie")
    private Integer sortie = 0;

    @Column(name = "prix_revient")
    private Double prixRevient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private Produit idProduit;

}