\c matelas;

create table block(
    id_block serial primary key,
    block_mere int,
    longueur decimal(10,2),
    largeur decimal(10,2),
    hauteur decimal(10,2),
    date_entree date,
    prix_revient decimal(18,2),
    etat int set default 0,
    id_machine int,
    pr_theorique decimal(18,2),
    foreign key (block_mere) references block(id_block)
);

create table format(
    id_format serial primary key,
    longueur decimal(10,2),
    largeur decimal(10,2),
    hauteur decimal(10,2)
);

create table produit(
    id_produit serial primary key,
    nom varchar(40),
    prix_vente decimal(18,2),
    id_format int,
    foreign key (id_format) references format(id_format)
);

create table mvt_stock(
    id_mvt_stock serial primary key,
    designation varchar(30),
    daty date,
    origine int,
    etat int,
    foreign key (origine) references block(id_block)
);

create table mvt_stock_fille(
    id_mvt_stock_fille serial primary key,
    id_mere int,
    entree int default 0,
    sortie int default 0,
    id_produit int,
    prix_revient decimal(12,2),
    foreign key (id_produit) references produit(id_produit),
    foreign key (id_mere) references produit(id_mvt_stock)
);

create table configuration(
    cle varchar(40) primary key,
    value varchar(50)
);

-- Sujet 2

create table recette(
    id_recette serial primary key,
    essence decimal(15,2),
    papier decimal(15,2),
    durcisseur decimal(15,2),
    date_debut date
);

create table machine(
    id_machine serial primary key,
    lib varchar(100),
    date_debut date
);

create table mvt_stock_mp(
    id_mvt_stock_mp serial primary key,
    entree int default 0,
    sortie int default 0,
    id_produit int,
    prix_revient decimal(12,2)
);

-- view
create or replace view etat_stock_format as
select
    row_number() over() as id,
    id_produit, sum(entree) - sum(sortie) as reste, sum(entree) as entree, sum(sortie) as sortie from mvt_stock_fille
group by id_produit;