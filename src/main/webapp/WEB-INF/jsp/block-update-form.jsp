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
    List<Block> blocks = (List<Block>) request.getAttribute("blocks");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <h1>update de block</h1>
    <form action="/block/update" method="post">
        <p>Choix du block a transformer</p>
        <select name="blockMere" id="">
            <% for (Block block : blocks) { %>
                <option value=<%=block.getId()%>>Block<%=block.getId()%></option>
            <% } %>
        </select>

        <p>Prix de revient <input type="number" step="0.01" value="0" name="prixRevient"></p>

        <input type="submit" value="valider">
    </form>
