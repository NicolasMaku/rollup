package itu.matelas.demo.block;

import itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Famille {
    List<Block> blockFilles;
    List<MvtStockFille> mvtStockFilles;
}
