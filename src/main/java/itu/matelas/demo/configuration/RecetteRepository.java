package itu.matelas.demo.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetteRepository extends JpaRepository<Recette, String> {

    @Query(value = "select * from recette order by date_debut desc limit 1;", nativeQuery = true)
    Recette findRecette();
}
