package itu.matelas.demo.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configuration")
public class Configuration {
    @Id
    @Column(name = "cle", nullable = false, length = 40)
    private String cle;

    @Column(name = "value", length = 50)
    private String value;

}