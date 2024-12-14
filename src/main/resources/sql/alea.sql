select row_number() over (order by sum(prix_revient - pr_theorique*volume) asc) as rang,
        id_machine ,
       sum(volume) as volume,
       sum(pr_theorique) as pr_theorique,
       sum(prix_revient) as pr_pratique,
       sum(prix_revient - pr_theorique) as ecart from block_data
group by id_machine ;


select row_number() over (order by sum(prix_revient - pr_theorique*volume) asc) as rang,
       id_machine ,
        sum(volume) as volume,
       sum(pr_theorique) as pr_theorique,
       sum(prix_revient) as pr_pratique,
       sum(prix_revient - pr_theorique) as ecart from block_data
where extract(year from date_entree)=:annee
group by id_machine ;


--
select sum(entree - sortie) from mvt_stock_mp group by id_produit;