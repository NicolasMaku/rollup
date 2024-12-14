package itu.matelas.demo.EtatStock.mvtBlockUsuel;

import itu.matelas.demo.EtatStock.EtatStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VueBlockOriginelRepository extends JpaRepository<VueBlockOriginel, Integer> {
}
