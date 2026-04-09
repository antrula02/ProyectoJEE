<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>La Casa del TERROR - Escape Room</title>
  <link rel="stylesheet" href="style.css">
  <script src="script.js" defer></script>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@300;400;500;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body>

<header class="topbar">
  <a href="index.jsp" class="logo-link">
    <div class="logo">
      <span class="logo-top">LA CASA DEL</span>
      <span class="logo-bottom">TERROR</span>
    </div>
  </a>

  <nav class="menu">
    <a href="index.jsp">Home</a>
    <a href="#comentarios">Experiencias</a>
    <a href="#contacto">Contacto</a>
    <a href="#reserva" class="btn-reservar">Reservar</a>
    <% if (usuario != null && usuario.getAdmin() == 1) { %>
      <a href="admin" class="btn-admin">Panel Admin</a>
    <% } %>
  </nav>

  <% if (usuario != null) { %>
    <div class="menu-usuario-centro">
      <a href="misReservas">Mis reservas</a>
      <a href="#comentarios">Comentarios</a>
      <a href="logout">Cerrar sesión</a>
    </div>
  <% } %>
</header>

<section class="hero">
  <div class="overlay"></div>
  <div class="hero-content">
    <p class="subtitulo">Atrévete a entrar</p>
    <h1>LA CASA DEL TERROR</h1>
    <p class="descripcion">
      Algo extraño ocurre en esta casa. Cada puerta esconde un secreto, cada pista os acerca a la verdad
      y cada minuto juega en vuestra contra. Reúne a tu equipo, mantén la calma y prepárate para una experiencia
      cargada de tensión, misterio y adrenalina.
    </p>
    <a href="#usuarios" class="btn-principal">COMENZAR LA EXPERIENCIA</a>
  </div>
</section>

<main id="usuarios">

  <section class="intro-seccion">
    <div class="modos-juego">
      <h3>ELIGE TU EXPERIENCIA</h3>

      <div class="modo-card normal">
        <h4>🧠 MODO NORMAL</h4>
        <p>
          Una experiencia centrada en el misterio, la lógica y la tensión constante.
          Tendréis que resolver enigmas, conectar pistas y mantener la calma para avanzar.
          Ideal para quienes buscan una aventura intensa sin sobresaltos extremos.
        </p>
      </div>

      <div class="modo-card terror">
        <h4>😈 MODO TERROR</h4>
        <p>
          Todo lo anterior… pero llevado al límite.
          La oscuridad, la presión y la ambientación harán que cada decisión sea más difícil.
          No solo tendréis que pensar… también controlar los nervios.

          No es para todo el mundo.
        </p>
      </div>
    </div>

    <h2>¿ESTÁIS PREPARADOS?</h2>
    <p>
      No todos los que entran en esta casa salen igual. En su interior os esperan enigmas, señales inquietantes
      y una historia que irá revelándose paso a paso. Tendréis que observar, pensar con sangre fría y trabajar en equipo
      para avanzar antes de que sea demasiado tarde.
    </p>
    <p>
      Regístrate, inicia sesión y reserva tu fecha para vivir una experiencia inmersiva en la que la tensión no deja de crecer.
      La casa os está esperando.
    </p>
  </section>

  <section class="contenedor-formularios">
    <article class="card">
      <h3>REGISTRO</h3>
      <p class="texto-card">
        Crea tu acceso y prepárate para adentrarte en la casa.
      </p>

      <form id="formRegistro">
        <div class="grupo-input">
          <label for="nombreRegistro">Nombre de usuario</label>
          <input type="text" id="nombreRegistro" name="nombreRegistro" placeholder="Introduce tu nombre" required>
        </div>

        <div class="grupo-input">
          <label for="emailRegistro">Correo electrónico</label>
          <input type="email" id="emailRegistro" name="emailRegistro" placeholder="Introduce tu email" required>
        </div>

        <div class="grupo-input">
          <label for="passwordRegistro">Contraseña</label>
          <input type="password" id="passwordRegistro" name="passwordRegistro" placeholder="Introduce tu contraseña" required>
        </div>

        <button type="submit" class="btn-form">REGISTRARSE</button>
      </form>

      <div class="mensaje" id="mensajeRegistro"></div>
    </article>

    <article class="card">
      <h3>INICIAR SESIÓN</h3>
      <p class="texto-card">
        Accede con tu cuenta para gestionar tu reserva y seguir la experiencia.
      </p>

      <form id="formLogin">
        <div class="grupo-input">
          <label for="emailLogin">Correo electrónico</label>
          <input type="email" id="emailLogin" name="emailLogin" placeholder="Introduce tu email" required>
        </div>

        <div class="grupo-input">
          <label for="passwordLogin">Contraseña</label>
          <input type="password" id="passwordLogin" name="passwordLogin" placeholder="Introduce tu contraseña" required>
        </div>

        <button type="submit" class="btn-form">INICIAR SESIÓN</button>
      </form>

      <div class="mensaje" id="mensajeLogin"></div>
    </article>
  </section>

  <section class="reservas-seccion" id="reserva">
    <div class="titulo-tabla">
      <h2>RESERVA TU ENTRADA</h2>
      <p class="texto-card">
        Elige la fecha, reúne a tu grupo y decide cómo queréis enfrentaros a la casa.
      </p>
    </div>

    <div class="reservas-contenido">
      <div class="calendario-card">
        <h3>Calendario de disponibilidad</h3>
        <div id="calendarioReservas" class="calendario-reservas"></div>
        <div class="leyenda-calendario">
          <span class="leyenda-item">
            <span class="color-box disponible"></span> Disponible
          </span>
          <span class="leyenda-item">
            <span class="color-box ocupado"></span> Ocupado
          </span>
        </div>
      </div>

      <div class="card">
        <h3>NUEVA RESERVA</h3>

        <form id="formReserva">
          <div class="grupo-input">
            <label for="nombreReserva">Nombre</label>
            <input type="text" id="nombreReserva" name="nombreReserva" placeholder="Nombre del responsable" required>
          </div>

          <div class="grupo-input">
            <label for="telefonoReserva">Teléfono</label>
            <input type="text"
                   id="telefonoReserva"
                   name="telefonoReserva"
                   pattern="[0-9]{9,11}"
                   maxlength="11"
                   minlength="9"
                   required
                   placeholder="Introduce tu teléfono">
          </div>

          <div class="grupo-input">
            <label for="fechaReserva">Fecha</label>
            <input type="date" id="fechaReserva" name="fechaReserva" required>
          </div>

          <div class="grupo-input">
            <label for="emailReserva">Email</label>
            <input type="email" id="emailReserva" name="emailReserva" placeholder="Introduce tu email" required>
          </div>

          <div class="grupo-input">
            <label for="numPlayerReserva">Número de jugadores</label>
            <input type="number" id="numPlayerReserva" name="numPlayerReserva" min="1" max="6" required>
          </div>

          <div class="grupo-input">
            <label for="modoReserva">Modo</label>
            <select id="modoReserva" name="modoReserva" required>
              <option value="0">Normal</option>
              <option value="1">Terror</option>
            </select>
          </div>

          <div class="grupo-input">
            <label for="notasReserva">Notas</label>
            <textarea id="notasReserva" name="notasReserva" rows="4" placeholder="Añade aquí cualquier detalle importante"></textarea>
          </div>

          <button type="submit" class="btn-form">RESERVAR</button>

          <p style="font-size:12px; margin-top:10px;">
            Al reservar aceptas nuestras
            <a href="condiciones.jsp" style="color:#ff4444;">condiciones</a>.
          </p>

          <label style="font-size:12px;">
            <input type="checkbox" required>
            Acepto las <a href="condiciones.jsp">condiciones</a>
          </label>
        </form>

        <div class="mensaje" id="mensajeReserva"></div>
      </div>
    </div>
  </section>

  <section class="comentarios-seccion" id="comentarios">
    <div class="titulo-tabla">
      <h2>EXPERIENCIAS DE OTROS GRUPOS</h2>

      <p class="texto-card">
        Descubre lo que otros se atrevieron a vivir... si es que lograron salir.
      </p>

      <% if (usuario != null) { %>
        <div style="text-align:center; margin-top:20px;">
          <a href="comentarios.html" class="btn-comentario">Escribir comentario</a>
        </div>
      <% } %>
    </div>

    <div id="listaComentarios" class="lista-comentarios">
      <p class="sin-comentarios">Cargando comentarios...</p>
    </div>
  </section>

</main>

<footer class="footer" id="contacto">
  <div class="footer-contenido-horizontal">
    <div class="footer-item">
      📞 666 555 444
    </div>

    <div class="footer-item">
      📧 slinkor901@gmail.com
    </div>

    <div class="footer-item">
      <a href="https://www.instagram.com/" target="_blank" class="insta-link">
        <img src="https://cdn-icons-png.flaticon.com/512/2111/2111463.png" alt="Instagram" class="icono-insta">
        @lacasadelterror
      </a>
    </div>

    <div class="footer-item">
      <a href="condiciones.jsp">Condiciones</a>
    </div>
  </div>

  <p class="footer-copy">Escape Room de Terror © 2026</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
</body>
</html>