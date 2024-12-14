<%
    String pg = (String) request.getAttribute("page");
    pg = pg + ".jsp";
    String alert = (String) request.getParameter("alert");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matelas</title>
    <style>
        /* Global style reset */
        * {
            font-family: sans-serif;
        }

        body {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Topbar styles */
        .topbar {
            background-color: #4A90E2;
            color: #ffffff;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-family: Arial, sans-serif;
        }

        .topbar .logo {
            font-size: 1.5rem;
            font-weight: bold;
        }

        .topbar .nav-links {
            display: flex;
            gap: 1.5rem;
        }

        .topbar .nav-links a {
            color: #ffffff;
            text-decoration: none;
            font-size: 1rem;
            transition: color 0.3s;
        }

        .topbar .nav-links a:hover {
            color: #f1f1f1;
        }

        .topbar .cta {
            background-color: #ffffff;
            color: #4A90E2;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.3s, color 0.3s;
        }

        .topbar .cta:hover {
            background-color: #e1e1e1;
        }

        .content {
            padding: 2%;
        }

        .alert {
            width: 100%;
            background-color: #f8d7da; /* Couleur de fond douce */
            color: #721c24; /* Texte rouge foncé */
            border: 1px solid #f5c6cb; /* Bordure assortie */
            padding: 10px 20px;
            border-radius: 5px; /* Bordures arrondies */
            font-family: Arial, sans-serif;
            font-size: 16px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* Ombre douce */
            margin-top: 10px;
        }
        th, td {
            padding: 1%;
        }
        table {
            width: 75%;
        }

    </style>
</head>
<body>

<!-- Topbar structure -->
<div class="topbar">
    <div class="logo">Matelas</div>
    <div class="nav-links">
        <a href="/block/form">Form block</a>
        <a href="/block/transf-form">Transformation</a>
        <a href="/resultat">Resultats</a>
        <a href="/block/update-form">Changer PR</a>
        <a href="/block/stock">CMUP</a>

    </div>
<%--    <a href="#signup" class="cta">Sign Up</a>--%>
</div>

<% if (alert != null) { %>
    <div class="alert">
        <% out.println(alert); %>
    </div>
<% } %>

<div class="content">
    <jsp:include page="<%= pg %>"/>
</div>

</body>
</html>
