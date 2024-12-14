package itu.matelas.demo.MvtStock;

import itu.matelas.demo.block.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MvtStockRepository extends JpaRepository<MvtStock, Integer> {

    @Query("select m from MvtStock m where m.origine = ?1")
    MvtStock findByOrigine(Block origine);
}
