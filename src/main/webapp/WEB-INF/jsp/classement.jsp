<%@ page import="itu.matelas.demo.affichage.resultat.Resultat" %>
<%@ page import="itu.matelas.demo.EtatStock.EtatStock" %>
<%@ page import="itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille" %>
<%@ page import="itu.matelas.demo.EtatStock.mvtBlockUsuel.VueBlockOriginel" %>
<%@ page import="itu.matelas.demo.classement.ClassementMachine" %>
<%@ page import="java.util.List" %>
<%@ page import="itu.matelas.demo.util.Generateur" %><%--
  Created by IntelliJ IDEA.
  User: nicol
  Date: 04/11/2024
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%
    List<ClassementMachine> classements = (List<ClassementMachine>) request.getAttribute("classement");
    int annee = (int) request.getAttribute("annee");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Classement des machines (ecart) pour : <span style="color: green"> <%= annee>0 ? annee : "Tous" %> </span> | ETU002554</h1>
<form action="/classement-machine/list">
    <select name="annee" id="">
        <option value="-1">Tous</option>
        <option value="2022">2022</option>
        <option value="2023">2023</option>
        <option value="2024">2024</option>
    </select>
    <input type="submit" value="Valider">
</form>

<br />

<table border="1" style="border-collapse: collapse">
        <tr>
            <th>rang</th>
            <th>Machine</th>
            <th>volume</th>
            <th>Prix de revient théorique</th>
            <th>pr pratique</th>
            <th>Ecart</th>
        </tr>



        <% for (ClassementMachine cm : classements) { %>
            <tr>
                <td><%=cm.getRang()%></td>
                <td>Machine<%=cm.getIdMachine()%></td>
                <td><%=String.format("%.2f",cm.getVolume())%></td>
                <td><%=Generateur.formatNumber(cm.getPrTheorique().doubleValue())%></td>
                <td><%=Generateur.formatNumber(cm.getPrPratique().doubleValue())%></td>
                <td><%=Generateur.formatNumber(cm.getEcart().doubleValue())%></td>
            </tr>
        <% } %>

</table>


