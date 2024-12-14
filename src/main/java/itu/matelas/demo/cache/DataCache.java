package itu.matelas.demo.cache;

import itu.matelas.demo.configuration.Recette;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
public class DataCache {

    List<Recette> recettes;

}
