package itu.matelas.demo.MvtStock;

import itu.matelas.demo.block.Block;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "mvt_stock")
public class MvtStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mvt_stock", nullable = false)
    private Integer id;

    @Column(name = "designation", length = 30)
    private String designation;

    @Column(name = "daty")
    private LocalDate daty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origine")
    private Block origine;

    @Column(name = "etat")
    private Integer etat;

}