package itu.matelas.demo.Format;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.Double;

@Getter
@Setter
@Entity
@Table(name = "format")
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_format", nullable = false)
    private Integer id;

    @Column(name = "longueur")
    private Double longueur;

    @Column(name = "largeur")
    private Double largeur;

    @Column(name = "hauteur")
    private Double hauteur;

    public double getVolume() {
        return getLongueur() * getLargeur() * getHauteur();
    }

}