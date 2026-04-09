<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Reserva" %>
<%@ page import="modelo.Usuario" %>

<%-- 
    Página JSP que muestra las reservas del usuario autenticado.
    Si no hay sesión iniciada, redirige al usuario al inicio.
--%>

<%
    // Obtener el usuario guardado en sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    // Si no hay usuario logueado, volver al inicio
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    // Obtener la lista de reservas enviada desde el servlet
    List<Reserva> listaReservas = (List<Reserva>) request.getAttribute("listaReservas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Reservas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #111;
            color: white;
            padding: 30px;
        }

        h1 {
            color: #ff4444;
        }

        .reserva {
            background: #222;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 10px;
            border: 1px solid #444;
        }

        a {
            color: #ff4444;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <h1>Mis Reservas</h1>

    <%-- Mostrar datos del usuario logueado --%>
    <p>Bienvenido, <strong><%= usuario.getNombre() %></strong></p>
    <p>Correo asociado: <strong><%= usuario.getEmail() %></strong></p>

    <%
        // Comprobar si el usuario tiene reservas
        if (listaReservas == null || listaReservas.isEmpty()) {
    %>
        <p>No tienes ninguna reserva asociada a tu correo.</p>
    <%
        } else {
            // Recorrer y mostrar cada reserva
            for (Reserva r : listaReservas) {
    %>
        <div class="reserva">
            <p><strong>Nombre:</strong> <%= r.getNombreRes() %></p>
            <p><strong>Fecha:</strong> <%= r.getFechaRes() %></p>
            <p><strong>Email:</strong> <%= r.getEmailRes() %></p>
            <p><strong>Teléfono:</strong> <%= r.getTelf() %></p>
            <p><strong>Número de personas:</strong> <%= r.getNumPlayer() %></p>
            <p><strong>Modo:</strong> <%= r.getMode() == 1 ? "Terror" : "Normal" %></p>
            <p><strong>Notas:</strong> <%= r.getNotas() %></p>
        </div>
    <%
            }
        }
    %>

    <br>
    <a href="index.jsp">Volver al inicio</a>

</body>
</html>