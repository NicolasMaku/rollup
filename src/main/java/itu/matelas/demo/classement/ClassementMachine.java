package itu.matelas.demo.classement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "classement_machine")
public class ClassementMachine {
    @Id
    @Column(name = "rang")
    private Long rang;

    @Column(name = "id_machine")
    private Integer idMachine;

    @Column(name = "ecart")
    private BigDecimal ecart;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "pr_theorique")
    private BigDecimal prTheorique;

    @Column(name = "pr_pratique")
    private BigDecimal prPratique;

}