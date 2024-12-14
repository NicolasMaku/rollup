package itu.matelas.demo.produit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    @Query(value = "select pr.*, (f.longueur*f.largeur*f.hauteur) as volume, (pr.prix_vente/(f.longueur*f.largeur*f.hauteur)) as prix_volume from produit pr join format f on pr.id_format = f.id_format\norder by prix_volume desc", nativeQuery = true)
    List<Produit> orderByRentabilite();

    @Query(value = "select pr.*, (f.longueur*f.largeur*f.hauteur) as volume from produit pr join format f on pr.id_format = f.id_format\n" +
            "order by volume asc", nativeQuery = true)
    List<Produit> orderByMinimalite();

}
