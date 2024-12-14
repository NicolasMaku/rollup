package itu.matelas.demo.MvtStock.MvtStockFille;

import itu.matelas.demo.block.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MvtStockFilleRepository extends JpaRepository<MvtStockFille, Integer> {
    @Query("select m from MvtStockFille m where m.idMere.origine = ?1")
    List<MvtStockFille> findByIdMere_Origine(Block origine);

    @Query(value = "select * from mvt_stock_fille order by ", nativeQuery = true)
    List<MvtStockFille> findAllOrderByDate();

    @Query("select m from MvtStockFille m order by m.idMere.daty desc")
    List<MvtStockFille> findByIdMere_Daty(LocalDate daty);
}

