select pr.*, (f.longueur*f.largeur*f.hauteur) as volume, (pr.prix_vente/(f.longueur*f.largeur*f.hauteur)) as prix_volume from produit pr join format f on pr.id_format = f.id_format
order by prix_volume desc limit 1;

select pr.*, (f.longueur*f.largeur*f.hauteur) as volume from produit pr join format f on pr.id_format = f.id_format
order by volume asc limit 1;