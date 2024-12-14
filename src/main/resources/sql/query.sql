CREATE VIEW vue_block_originel AS
WITH RECURSIVE block_originel AS (
    -- Point de départ : sélectionne le block d'origine de chaque mouvement stock fille
    SELECT
        msf.id_mvt_stock_fille,
        b.id_block AS id_block_actuel,
        b.block_mere
    FROM
        mvt_stock_fille msf
            JOIN
        mvt_stock ms ON ms.id_mvt_stock = msf.id_mere
            JOIN
        block b ON b.id_block = ms.origine

    UNION ALL

    -- Récursion : continue de chercher le parent (block_mere) jusqu'à ce qu'il n'y ait plus de parent
    SELECT
        bo.id_mvt_stock_fille,
        b.id_block AS id_block_actuel,
        b.block_mere
    FROM
        block b
            JOIN
        block_originel bo ON b.id_block = bo.block_mere
)

-- Sélectionne le block originel pour chaque mouvement de stock fille
SELECT
    msf.*,
    bo.id_block_actuel AS id_block_originel
FROM
    mvt_stock_fille msf
        JOIN
    block_originel bo ON msf.id_mvt_stock_fille = bo.id_mvt_stock_fille
WHERE
    bo.block_mere IS NULL; -- Filtre pour obtenir le block originel (celui sans parent)


create or replace view block_data as
select *, volume*m3 as pr_theorique from
(select *, (longueur*block.largeur*block.hauteur) as volume from block cross join (select sum(prix_revient*quantite) as m3 from recette) as prix_cube) as allof ;

-- classement des machines
create or replace view classement_machine as
select row_number() over (order by sum(prix_revient - pr_theorique*volume) asc) as rang
    ,id_machine,sum(prix_revient - pr_theorique) as ecart from block_data group by id_machine ;


-- moyenne des blocks actuelles
select AVG(pr_theorique/(longueur*largeur*hauteur)) from block;
select AVG(pr_theorique/(longueur*largeur*hauteur)) from (select * from block limit 5) as blocks;

select (sum(prix_revient)/sum(longueur*largeur*hauteur)) from block;