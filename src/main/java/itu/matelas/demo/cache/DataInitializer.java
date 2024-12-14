package itu.matelas.demo.cache;

import itu.matelas.demo.configuration.RecetteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer {

    @Autowired
    private DataCache dataCache;
    @Autowired
    private RecetteRepository recetteRepository;

    @PostConstruct
    public void loadData() {
        // Charger les données depuis la base de données une seule fois
        dataCache.setRecettes(recetteRepository.findAll());
    }
}
