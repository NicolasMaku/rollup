<%@ page import="itu.matelas.demo.affichage.resultat.Resultat" %>
<%@ page import="itu.matelas.demo.EtatStock.EtatStock" %>
<%@ page import="itu.matelas.demo.MvtStock.MvtStockFille.MvtStockFille" %>
<%@ page import="itu.matelas.demo.EtatStock.mvtBlockUsuel.VueBlockOriginel" %><%--
  Created by IntelliJ IDEA.
  User: nicol
  Date: 04/11/2024
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%
    Resultat resultat = (Resultat) request.getAttribute("resultat");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <h1>Etat de stock</h1>
    <table border="1" style="border-collapse: collapse">
        <thead>
            <tr>
                <th>Format usuel</th>
                <th>Restant</th>
                <th>Entree</th>
                <th>Sortie</th>
                <th>Prix de vente unitaire</th>
                <th>Prix de vente</th>
                <th>Prix de revient</th>
                <th>moyenne de P. revient unitaire</th>
            </tr>
        </thead>
        <tbody>
            <% for (EtatStock etatStock : resultat.getEtatStocks()) { %>
                <tr>
                    <td><%=etatStock.getIdProduit().getNom()%></td>
                    <td><%=etatStock.getReste()%></td>
                    <td><%=etatStock.getEntree()%></td>
                    <td><%=etatStock.getSortie()%></td>
                    <td><%=etatStock.getIdProduit().getPrixVente()%></td>
                    <td><%=etatStock.getValeur()%></td>
                    <td><%=etatStock.getPrixRevient()%></td>
                    <td><%=String.format("%.2f",etatStock.getPrixRevient()/etatStock.getReste())%></td>
                </tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="5"><strong>Total</strong></td>
                <td><%=resultat.getValeurStock()%></td>
                <td><%=resultat.getP1().getPrixRevient()%></td>
            </tr>
        </tfoot>
    </table>

    <h1>Etat de stock le plus rentable</h1>
    <table border="1" style="border-collapse: collapse">
        <thead>
        <tr>
            <th>Format usuel</th>
            <th>Restant</th>
            <th>Entree</th>
            <th>Sortie</th>
            <th>Prix de vente unitaire</th>
            <th>Prix de vente</th>
            <th>Prix de revient</th>
        </tr>
        </thead>
        <tbody>
        <% for (MvtStockFille mvtf : resultat.getP2().getMvtStockFilleTempo()) { %>
        <tr>
            <td><%=mvtf.getIdProduit().getNom()%></td>
            <td>-</td>
            <td><%=mvtf.getEntree()%></td>
            <td><%=mvtf.getSortie()%></td>
            <td><%=String.format("%.2f",mvtf.getIdProduit().getPrixVente())%></td>
            <td><%=String.format("%.2f",mvtf.getIdProduit().getPrixVente()*(mvtf.getEntree()- mvtf.getSortie())) %></td>
            <td><%=String.format("%.2f",mvtf.getPrixRevient()*(mvtf.getEntree()- mvtf.getSortie()))%></td>
        </tr>
        <% } %>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="5"><strong>Normal</strong></td>
            <td><%=String.format("%.2f",resultat.getP1().getCa())%></td>
            <td><%=String.format("%.2f",resultat.getP1().getPrixRevient())%></td>
        </tr>
        <tr>
            <td colspan="5"><strong>Total</strong></td>
            <td><%=String.format("%.2f",resultat.getP2().getCa())%></td>
            <td><%=String.format("%.2f",resultat.getP2().getPrixRevient())%></td>
        </tr>
        </tfoot>
    </table>

<h1>Etat de stock & le plus petit</h1>
<table border="1" style="border-collapse: collapse">
    <thead>
    <tr>
        <th>Format usuel</th>
        <th>Restant</th>
        <th>Entree</th>
        <th>Sortie</th>
        <th>Prix de vente unitaire</th>
        <th>Prix de vente</th>
        <th>Prix de revient</th>
    </tr>
    </thead>
    <tbody>
    <% for (MvtStockFille mvtf : resultat.getP3().getMvtStockFilleTempo()) { %>
    <tr>
        <td><%=mvtf.getIdProduit().getNom()%></td>
        <td>-</td>
        <td><%=mvtf.getEntree()%></td>
        <td><%=mvtf.getSortie()%></td>
        <td><%=String.format("%.2f",mvtf.getIdProduit().getPrixVente())%></td>
        <td><%=String.format("%.2f",mvtf.getIdProduit().getPrixVente()*(mvtf.getEntree()- mvtf.getSortie())) %></td>
        <td><%=String.format("%.2f",mvtf.getPrixRevient()*(mvtf.getEntree()- mvtf.getSortie()))%></td>
    </tr>
    <% } %>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="5"><strong>Normal</strong></td>
        <td><%=String.format("%.2f",resultat.getP1().getCa())%></td>
        <td><%=String.format("%.2f",resultat.getP1().getPrixRevient())%></td>
    </tr>
    <tr>
        <td colspan="5"><strong>Total</strong></td>
        <td><%=String.format("%.2f",resultat.getP3().getCa())%></td>
        <td><%=String.format("%.2f",resultat.getP3().getPrixRevient())%></td>
    </tr>
    </tfoot>
</table>

    <h1>Affichage de resultat</h1>
    <table border="1" style="border-collapse: collapse">
        <tr>
            <th>Categorie</th>
            <th>Chiffre affaire</th>
            <th>Prix revient</th>
            <th>Benefice</th>
        </tr>
            <tr>
                <td>Montant 1</td>
                <td><%=resultat.getP1().getCa()%></td>
                <td><%=resultat.getP1().getPrixRevient()%></td>
                <td><%=resultat.getP1().getBenefice()%></td>
            </tr>
        <tr>
            <td>Reste usuel rentable</td>
            <td><%=String.format("%.2f",resultat.getP2().getCa())%></td>
            <td><%=String.format("%.2f",resultat.getP2().getPrixRevient())%></td>
            <td><%=String.format("%.2f",resultat.getP2().getBenefice())%></td>
        </tr>
        <tr>
            <td>Reste usuel petit volume</td>
            <td><%=String.format("%.2f",resultat.getP3().getCa())%></td>
            <td><%=String.format("%.2f", resultat.getP3().getPrixRevient())%></td>
            <td><%=String.format("%.2f", resultat.getP3().getBenefice())%></td>
        </tr>

    </table>

<h1>Affichage de originel</h1>
<table border="1" style="border-collapse: collapse">
    <thead>
    <tr>
        <th>id stock usuel</th>
        <th>id originel</th>
        <th>Entree</th>
        <th>Sortie</th>
        <th>Prix de vente unitaire</th>
        <th>Prix de vente</th>
        <th>Prix de revient</th>
    </tr>
    </thead>
    <tbody>
    <% for (VueBlockOriginel vueBlockOriginel : resultat.getOriginels()) { %>
    <tr>
        <td><%=vueBlockOriginel.getIdMvtStockFille()%></td>
        <td><%=vueBlockOriginel.getIdBlockOriginel()%></td>
        <td><%=vueBlockOriginel.getEntree()%></td>
        <td><%=vueBlockOriginel.getSortie()%></td>
        <td><%=vueBlockOriginel.getPrixRevient()%></td>
        <td><%=vueBlockOriginel.getPrixRevient()%></td>
    </tr>
    <% } %>
    </tbody>
</table>

