package itu.matelas.demo.block;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_machine", nullable = false)
    private Integer id;

    //TODO [JPA Buddy] generate columns from DB
}