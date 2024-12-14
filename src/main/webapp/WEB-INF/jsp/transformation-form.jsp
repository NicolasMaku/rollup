<%@ page import="itu.matelas.demo.produit.Produit" %>
<%@ page import="java.util.List" %>
<%@ page import="itu.matelas.demo.block.Block" %><%--
  Created by IntelliJ IDEA.
  User: nicol
  Date: 04/11/2024
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%
    List<Produit> produits = (List<Produit>) request.getAttribute("produits");
    List<Block> blocks = (List<Block>) request.getAttribute("blocks");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <h1>Transformation de block</h1>
    <form action="/block/transformer" method="post">
        <p>Choix du block a transformer</p>
        <select name="blockMere" id="">
            <% for (Block block : blocks) { %>
                <option value=<%=block.getId()%>>Block<%=block.getId()%></option>
            <% } %>
        </select>

        <% for (Produit prod : produits) { %>
            <p>
                <%= prod.getNom() %>
                <input type="hidden" name="usuels[]" value=<%=prod.getId()%>>
                <input type="number" name="qte[]" value="0">
            </p>
        <% } %>

        <p>Reste</p>
        <p>Longeur <input type="text" step="0.01" value="0" name="longueur"></p>
        <p>Largeur <input type="text" step="0.01" value="0" name="largeur"></p>
        <p>Hauteur <input type="text" step="0.01" value="0" name="hauteur"></p>

        <p>Date de transformation</p>
        <input type="date" name="daty">

        <input type="submit" value="valider">
    </form>
