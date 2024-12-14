package itu.matelas.demo.EtatStock;

import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatStockRepository extends JpaRepository<EtatStock, Integer> {
}
