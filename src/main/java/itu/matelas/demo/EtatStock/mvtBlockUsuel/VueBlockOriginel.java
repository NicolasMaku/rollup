package itu.matelas.demo.EtatStock.mvtBlockUsuel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "vue_block_originel")
public class VueBlockOriginel {
    @Id
    @Column(name = "id_mvt_stock_fille")
    private Integer idMvtStockFille;

    @Column(name = "id_mere")
    private Integer idMere;

    @Column(name = "entree")
    private Integer entree;

    @Column(name = "sortie")
    private Integer sortie;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "prix_revient", precision = 12, scale = 2)
    private BigDecimal prixRevient;

    @Column(name = "id_block_originel")
    private Integer idBlockOriginel;

}