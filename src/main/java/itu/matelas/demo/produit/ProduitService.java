package itu.matelas.demo.produit;

import org.springframework.stereotype.Service;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public Produit getRentable() {
        return produitRepository.orderByRentabilite().get(0);
    }

    public Produit getMinimum() {
        return produitRepository.orderByMinimalite().get(0);
    }

}
