-- Table block
INSERT INTO block (block_mere, longueur, largeur, hauteur, date_entree, prix_revient, etat)
VALUES
    (NULL, 100.00, 50.00, 30.00, '2024-01-01', 500.00, 1),  -- Bloc mère
    (1, 50.00, 25.00, 15.00, '2024-02-01', 250.00, 0),     -- Bloc fille de bloc 1
    (1, 60.00, 30.00, 20.00, '2024-03-01', 300.00, 0),     -- Bloc fille de bloc 1
    (2, 30.00, 15.00, 10.00, '2024-04-01', 150.00, 1);     -- Bloc fille de bloc 2

-- Table format
INSERT INTO format (longueur, largeur, hauteur)
VALUES
    (10.00, 5.00, 2.00),
    (20.00, 10.00, 5.00),
    (15.00, 7.50, 3.00);

-- Table produit
INSERT INTO produit (prix_vente, id_format)
VALUES
    (150.00, 1),
    (300.00, 2),
    (200.00, 3);

-- Table mvt_stock
INSERT INTO mvt_stock (designation, daty, origine, etat)
VALUES
    ('Reception', '2024-01-05', 1, 0),
    ('Sortie vers client', '2024-01-10', 2, 1),
    ('Retour client', '2024-01-15', 1, 0),
    ('Déstockage', '2024-02-01', 1, 1);

-- Table mvt_stock_fille
INSERT INTO mvt_stock_fille (id_mere, entree, sortie, id_produit, prix_revient)
VALUES
    (1, 100, 0, 1, 100.00),
    (1, 200, 0, 2, 150.00),
    (2, 0, 50, 1, 90.00),
    (3, 30, 0, 3, 120.00);

--
INSERT INTO recette(essence, papier, durcisseur, date_debut) VALUES
(2, 3, 0.5, '2023-12-14');