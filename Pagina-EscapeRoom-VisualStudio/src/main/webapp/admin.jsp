<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Comentario" %>
<%@ page import="modelo.Reserva" %>
<%@ page import="modelo.Usuario" %>

<%-- 
    Página de administración.
    Permite al administrador visualizar y eliminar comentarios y reservas.
    Solo puede acceder un usuario con rol de administrador.
--%>

<%
    // Obtener el usuario de la sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    // Comprobar acceso solo para administradores
    if (usuario == null || !usuario.esAdmin()) {
        response.sendRedirect("index.html");
        return;
    }

    // Obtener listas enviadas desde el servlet
    List<Comentario> listaComentarios = (List<Comentario>) request.getAttribute("listaComentarios");
    List<Reserva> listaReservas = (List<Reserva>) request.getAttribute("listaReservas");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel de Administración</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #111;
            margin: 0;
            color: white;
        }

        /* CABECERA DEL PANEL */
        .header-admin {
            background: linear-gradient(90deg, black, #1a0000);
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid red;
        }

        .header-admin h1 {
            margin: 0;
            font-size: 22px;
        }

        .nav-admin a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            transition: 0.3s;
        }

        .nav-admin a:hover {
            color: red;
        }

        .logout {
            background: red;
            padding: 6px 12px;
            border-radius: 5px;
        }

        .logout:hover {
            background: darkred;
        }

        /* CONTENIDO PRINCIPAL */
        .contenedor {
            max-width: 1200px;
            margin: auto;
            padding: 30px;
        }

        .bloque {
            background: #1c1c1c;
            padding: 20px;
            margin-bottom: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(255,0,0,0.2);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        table th, table td {
            border: 1px solid #333;
            padding: 10px;
        }

        table th {
            background: black;
            color: red;
        }

        .btn-eliminar {
            background: crimson;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
        }

        .btn-eliminar:hover {
            background: darkred;
        }

        .vacio {
            color: #aaa;
        }

        .usuario {
            margin-top: 10px;
            font-weight: bold;
            color: #ccc;
        }
    </style>
</head>

<body>

<!-- CABECERA DEL PANEL DE ADMINISTRACIÓN -->
<div class="header-admin">
    <h1>Panel de Administración</h1>

    <div class="nav-admin">
        <a href="index.jsp">Inicio</a>
        <a href="misReservas">Mis reservas</a>
        <a href="admin">Admin</a>
        <a href="logout" class="logout">Cerrar sesión</a>
    </div>
</div>

<div class="contenedor">

    <!-- Mostrar nombre del administrador -->
    <div class="usuario">
        Admin: <%= usuario.getNombre() %>
    </div>

    <!-- BLOQUE DE COMENTARIOS -->
    <div class="bloque">
        <h2>Comentarios</h2>

        <%-- Mostrar comentarios si existen --%>
        <% if (listaComentarios != null && !listaComentarios.isEmpty()) { %>
            <table>
                <tr>
                    <th>ID</th>
                    <th>ID Usuario</th>
                    <th>Nombre</th>
                    <th>Modo</th>
                    <th>Opinión</th>
                    <th>Acción</th>
                </tr>

                <% for (Comentario c : listaComentarios) { %>
                <tr>
<td><%= c.getIdComentario() %></td>                    <td><%= c.getIdUsuario() %></td>
                    <td><%= c.getNombreUser() %></td>
                    <td><%= c.getModo() %></td>
                    <td><%= c.getOpinion() %></td>
                    <td>
                        <form action="eliminarComentario" method="post" onsubmit="return confirm('¿Eliminar comentario?');">
                            <input type="hidden" name="id" value="<%= c.getIdComentario() %>">
                            <button type="submit" class="btn-eliminar">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </table>
        <% } else { %>
            <p class="vacio">No hay comentarios.</p>
        <% } %>
    </div>

    <!-- BLOQUE DE RESERVAS -->
    <div class="bloque">
        <h2>Reservas</h2>

        <%-- Mostrar reservas si existen --%>
        <% if (listaReservas != null && !listaReservas.isEmpty()) { %>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Teléfono</th>
                    <th>Fecha</th>
                    <th>Email</th>
                    <th>Jugadores</th>
                    <th>Modo</th>
                    <th>Notas</th>
                    <th>Acción</th>
                </tr>

                <% for (Reserva r : listaReservas) { %>
                <tr>
                    <td><%= r.getIdReserva() %></td>
                    <td><%= r.getNombreRes() %></td>
                    <td><%= r.getTelf() %></td>
                    <td><%= r.getFechaRes() %></td>
                    <td><%= r.getEmailRes() %></td>
                    <td><%= r.getNumPlayer() %></td>
                    <td><%= r.getMode() == 1 ? "Terror" : "Normal" %></td>
                    <td><%= r.getNotas() %></td>
                    <td>
                        <form action="eliminarReserva" method="post" onsubmit="return confirm('¿Eliminar reserva?');">
                            <input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                            <button type="submit" class="btn-eliminar">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </table>

            <p>Total reservas: <%= listaReservas.size() %></p>

        <% } else { %>
            <p class="vacio">No hay reservas.</p>
        <% } %>
    </div>

</div>

</body>
</html>