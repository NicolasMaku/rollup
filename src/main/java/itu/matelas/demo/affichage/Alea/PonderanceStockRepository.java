package itu.matelas.demo.affichage.Alea;

import itu.matelas.demo.block.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PonderanceStockRepository extends JpaRepository<PonderanceStock, Integer> {

}
