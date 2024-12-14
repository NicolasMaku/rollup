package itu.matelas.demo.mvtStockmp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "mvt_stock_mp")
public class MvtStockMp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mvt_stock_mp", nullable = false)
    private Integer id;

    @Column(name = "entree")
    private Double entree;

    @Column(name = "sortie")
    private Double sortie;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "prix_revient")
    private Double prixRevient;

    @Column(name = "date")
    private LocalDate date;

}