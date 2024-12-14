package itu.matelas.demo.classement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassementMachineRepository extends JpaRepository<ClassementMachine ,Integer> {

//    @Query(value = "select row_number() over (order by sum(prix_revient - pr_theorique*volume) asc) as rang," +
//            "       id_machine ," +
//            "        sum(volume) as volume," +
//            "       sum(pr_theorique) as pr_theorique," +
//            "       sum(prix_revient) as pr_pratique," +
//            "       sum(prix_revient - pr_theorique) as ecart from block_data" +
//            "       where extract(year from date_entree)=:annee" +
//            "       group by id_machine ;", nativeQuery = true)
//    List<ClassementMachine> findClassementByAnnee(@Param("annee") int annee);
//
//    @Query(value = "select row_number() over (order by sum(prix_revient - pr_theorique*volume) asc) as rang," +
//            "       id_machine ," +
//            "        sum(volume) as volume," +
//            "       sum(pr_theorique) as pr_theorique," +
//            "       sum(prix_revient) as pr_pratique," +
//            "       sum(prix_revient - pr_theorique) as ecart from block_data" +
//            "       group by id_machine ;", nativeQuery = true)
//    List<ClassementMachine> findClassementByAnnee();

    @Query(value = "select row_number() over (order by ABS(sum(prix_revient - pr_theorique*volume)) asc) as rang," +
            "       id_machine ," +
            "        sum(volume) as volume," +
            "       sum(pr_theorique) as pr_theorique," +
            "       sum(prix_revient) as pr_pratique," +
            "       ABS(sum(prix_revient - pr_theorique)) as ecart from block_data" +
            "       where extract(year from date_entree)=:annee" +
            "       group by id_machine ;", nativeQuery = true)
    List<ClassementMachine> findClassementByAnnee(@Param("annee") int annee);

    @Query(value = "select row_number() over (order by ABS(sum(prix_revient - pr_theorique*volume)) asc) as rang," +
            "       id_machine ," +
            "        sum(volume) as volume," +
            "       sum(pr_theorique) as pr_theorique," +
            "       sum(prix_revient) as pr_pratique," +
            "       ABS(sum(prix_revient - pr_theorique)) as ecart from block_data" +
            "       group by id_machine ;", nativeQuery = true)
    List<ClassementMachine> findClassementByAnnee();

}
