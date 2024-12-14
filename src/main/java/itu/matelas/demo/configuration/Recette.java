package itu.matelas.demo.configuration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "recette")
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recette", nullable = false)
    private Integer id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "quantite", precision = 15, scale = 2)
    private BigDecimal quantite;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "prix_revient", precision = 15, scale = 2)
    private BigDecimal prixRevient;

}