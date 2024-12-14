<%--
  Created by IntelliJ IDEA.
  User: nicol
  Date: 04/11/2024
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Insertion block</h1>
<form action="/block/insert" method="post">
    <p>longueur</p>
    <input type="number" step="0.01" name="longueur" placeholder="longueur">

    <p>largeur</p>
    <input type="number" step="0.01" name="largeur" placeholder="largeur">

    <p>hauteur</p>
    <input type="number" step="0.01" name="hauteur" placeholder="hauteur">

    <p>Date d'entree</p>
    <input type="date" name="dateEntree">

    <p>prix de revient</p>
    <input type="number" step="0.01" name="prixRevient" placeholder="Prix de revient">

    <input type="submit" value="Inserer">
</form>
