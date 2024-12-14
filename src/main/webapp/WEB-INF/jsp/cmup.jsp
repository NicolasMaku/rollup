<%@ page import="itu.matelas.demo.affichage.resultat.Resultat" %>
<%@ page import="itu.matelas.demo.EtatStock.EtatStock" %>
<%@ page import="itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille" %>
<%@ page import="itu.matelas.demo.EtatStock.mvtBlockUsuel.ponderanceStock" %>
<%@ page import="itu.matelas.demo.affichage.Alea.PonderanceStock" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: nicol
  Date: 04/11/2024
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%
    List<PonderanceStock> ponderanceStockList = (List<PonderanceStock>) request.getAttribute("ponderance");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<h1>Affichage de originel</h1>
<table border="1" style="border-collapse: collapse">
    <thead>
    <tr>
        <th>Format usuel</th>
        <th>Restant</th>
        <th>Moyenne prix de vente</th>
    </tr>
    </thead>
    <tbody>
    <% for (PonderanceStock ponderanceStock : ponderanceStockList) { %>
    <tr>
        <td><%=ponderanceStock.getIdProduit().getNom()%></td>
        <td><%=ponderanceStock.getRestant()%></td>
        <td><%=ponderanceStock.getMoyenne()%></td>
    </tr>
    <% } %>
    </tbody>
</table>

</table>
