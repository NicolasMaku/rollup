package itu.matelas.demo.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, String> {

    @Query("select c from Configuration c where c.cle = ?1")
    Configuration findByCle(String cle);
}
