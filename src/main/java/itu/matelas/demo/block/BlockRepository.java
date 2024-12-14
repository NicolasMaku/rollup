package itu.matelas.demo.block;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {

    @Query("select b from Block b where b.etat = ?1")
    List<Block> findByEtat(Integer etat);

    @Query("select b from Block b where b.blockMere = ?1")
    Block findByBlockMere(Block blockMere);
}
