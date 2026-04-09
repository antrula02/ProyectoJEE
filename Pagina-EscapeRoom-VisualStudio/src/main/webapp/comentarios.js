/**
 * Se ejecuta cuando el contenido del DOM ha sido cargado por completo.
 * 
 * Funcionalidades principales:
 * - Obtiene del localStorage el id y nombre del usuario.
 * - Comprueba si el usuario ha iniciado sesión.
 * - Rellena automáticamente el campo de nombre del comentario.
 * - Gestiona el envío del formulario de comentarios.
 */
document.addEventListener("DOMContentLoaded", function () {
  /** @type {string|null} Identificador del usuario guardado en localStorage */
  const idUsuario = localStorage.getItem("idUsuario");

  /** @type {string|null} Nombre del usuario guardado en localStorage */
  const nombreUsuario = localStorage.getItem("nombreUsuario");

  /** @type {HTMLInputElement|null} Campo de texto donde se muestra el nombre del usuario */
  const nombreInput = document.getElementById("nombreComentario");

  /** @type {HTMLFormElement|null} Formulario para enviar comentarios */
  const formComentario = document.getElementById("formComentario");

  /** @type {HTMLElement|null} Contenedor donde se muestran mensajes al usuario */
  const mensajeComentario = document.getElementById("mensajeComentario");

  console.log('idUsuario=' + idUsuario + 'nombreUsuario=' + nombreUsuario);

  // Si no existe sesión iniciada, redirige al usuario a la página principal
  if (!idUsuario || !nombreUsuario) {
    window.location.href = "index.jsp";
    return;
  }

  // Rellena automáticamente el nombre del usuario en el input
  if (nombreInput) {
    nombreInput.value = nombreUsuario;
  }

  // Si existe el formulario, añade el evento submit
  if (formComentario) {
    formComentario.addEventListener("submit", async function (e) {
      e.preventDefault();

      /** @type {HTMLInputElement|null} Campo para seleccionar el modo del comentario */
      const modoInput = document.getElementById("modoComentario");

      /** @type {HTMLTextAreaElement|null|HTMLInputElement} Campo donde el usuario escribe su opinión */
      const opinionInput = document.getElementById("opinionComentario");

      // Verifica que todos los elementos necesarios existan
      if (!modoInput || !opinionInput || !mensajeComentario) {
        return;
      }

      /** @type {string} Valor del modo seleccionado */
      const modo = modoInput.value;

      /** @type {string} Texto del comentario escrito por el usuario */
      const opinion = opinionInput.value.trim();

      // Comprueba que el comentario no esté vacío
      if (opinion === "") {
        mensajeComentario.textContent = "Escribe un comentario.";
        return;
      }

      try {
        /** @type {URLSearchParams} Datos enviados al servidor */
        const datos = new URLSearchParams();
        datos.append("idUsuario", idUsuario);
        datos.append("nombreUser", nombreUsuario);
        datos.append("modo", modo);
        datos.append("opinion", opinion);

        /**
         * Petición al servidor para guardar el comentario.
         * 
         * @type {Response}
         */
        const respuesta = await fetch("guardarComentario", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: datos.toString()
        });

        /** @type {string} Respuesta en texto devuelta por el servidor */
        const resultado = await respuesta.text();

        mensajeComentario.textContent = resultado;

        // Si el comentario se guardó correctamente, limpia el formulario
        if (resultado.toLowerCase().includes("correctamente")) {
          formComentario.reset();

          // Vuelve a colocar el nombre del usuario en el input
          if (nombreInput) {
            nombreInput.value = nombreUsuario;
          }
        }

      } catch (error) {
        console.error("Error al guardar comentario:", error);
        mensajeComentario.textContent = "Error al guardar el comentario.";
      }
    });
  }
});

/**
 * Carga y muestra en pantalla las reservas del usuario actual.
 * 
 * Funcionamiento:
 * - Obtiene el email del usuario desde localStorage.
 * - Realiza una petición al servidor para recuperar sus reservas.
 * - Inserta dinámicamente cada reserva en el contenedor HTML.
 * - Muestra un mensaje si no existen reservas o si ocurre un error.
 * 
 * @async
 * @function cargarMisReservas
 * @returns {Promise<void>} No devuelve ningún valor, solo actualiza el DOM.
 */
async function cargarMisReservas() {
  /** @type {string|null} Email del usuario guardado en localStorage */
  const emailUsuario = localStorage.getItem("emailUsuario");

  /** @type {HTMLElement|null} Contenedor donde se mostrarán las reservas */
  const contenedor = document.getElementById("misReservasUsuario");

  // Si no hay email o no existe el contenedor, se detiene la función
  if (!emailUsuario || !contenedor) {
    return;
  }

  try {
    /**
     * Petición al servidor para obtener las reservas del usuario.
     * 
     * @type {Response}
     */
    const respuesta = await fetch("misReservas?email=" + encodeURIComponent(emailUsuario));

    // Comprueba si la respuesta del servidor fue correcta
    if (!respuesta.ok) {
      throw new Error("No se pudieron cargar las reservas");
    }

    /**
     * Lista de reservas obtenidas en formato JSON.
     * 
     * @type {Array<Object>}
     */
    const reservas = await respuesta.json();

    // Limpia el contenido anterior del contenedor
    contenedor.innerHTML = "";

    // Si no hay reservas, muestra un mensaje informativo
    if (!reservas || reservas.length === 0) {
      contenedor.innerHTML = "<p>No tienes reservas registradas.</p>";
      return;
    }

    // Recorre cada reserva y la muestra en pantalla
    reservas.forEach(function (reserva) {
      /** @type {HTMLDivElement} Tarjeta contenedora de una reserva */
      const div = document.createElement("div");
      div.className = "tarjeta-reserva";

      /** @type {string} Texto descriptivo del modo de juego */
      const modoTexto = reserva.modo == 1 ? "Terror" : "Normal";

      div.innerHTML = `
        <p><strong>Nombre:</strong> ${reserva.nombre}</p>
        <p><strong>Fecha:</strong> ${reserva.fecha}</p>
        <p><strong>Email:</strong> ${reserva.email}</p>
        <p><strong>Teléfono:</strong> ${reserva.telefono}</p>
        <p><strong>Jugadores:</strong> ${reserva.numPlayer}</p>
        <p><strong>Modo:</strong> ${modoTexto}</p>
      `;

      contenedor.appendChild(div);
    });

  } catch (error) {
    console.error("Error al cargar reservas:", error);
    contenedor.innerHTML = "<p>Error al cargar tus reservas.</p>";
  }
}