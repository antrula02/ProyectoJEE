let fechasReservadas = [];

/**
 * Se ejecuta cuando el contenido de la página se ha cargado por completo.
 * Configura los formularios, carga comentarios, reservas y calendario.
 */
document.addEventListener("DOMContentLoaded", function () {
  const formRegistro = document.getElementById("formRegistro");
  const mensajeRegistro = document.getElementById("mensajeRegistro");

  // Configuración del formulario de registro
  if (formRegistro && mensajeRegistro) {
    formRegistro.addEventListener("submit", async function (e) {
      e.preventDefault();

      const nombreInput = document.getElementById("nombreRegistro");
      const emailInput = document.getElementById("emailRegistro");
      const passwordInput = document.getElementById("passwordRegistro");

      if (!nombreInput || !emailInput || !passwordInput) {
        mensajeRegistro.className = "mensaje mensaje-error";
        mensajeRegistro.textContent = "Error en el formulario.";
        return;
      }

      const nombre = nombreInput.value.trim();
      const email = emailInput.value.trim();
      const password = passwordInput.value.trim();

      if (nombre === "" || email === "" || password === "") {
        mensajeRegistro.className = "mensaje mensaje-error";
        mensajeRegistro.textContent = "Completa todos los campos.";
        return;
      }

      try {
        const datos = new URLSearchParams();
        datos.append("nombre", nombre);
        datos.append("email", email);
        datos.append("password", password);

        const respuesta = await fetch("registro", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: datos.toString()
        });

        const resultado = await respuesta.text();

        if (resultado.toLowerCase().includes("correctamente")) {
          mensajeRegistro.className = "mensaje mensaje-ok";
          formRegistro.reset();
        } else {
          mensajeRegistro.className = "mensaje mensaje-error";
        }

        mensajeRegistro.textContent = resultado;

      } catch (error) {
        console.error("Error registro:", error);
        mensajeRegistro.className = "mensaje mensaje-error";
        mensajeRegistro.textContent = "Error al conectar con el servidor.";
      }
    });
  }

  const formLogin = document.getElementById("formLogin");
  const mensajeLogin = document.getElementById("mensajeLogin");

  // Configuración del formulario de login
  if (formLogin && mensajeLogin) {
    formLogin.addEventListener("submit", async function (e) {
      e.preventDefault();

      const emailInput = document.getElementById("emailLogin");
      const passwordInput = document.getElementById("passwordLogin");

      if (!emailInput || !passwordInput) {
        mensajeLogin.className = "mensaje mensaje-error";
        mensajeLogin.textContent = "Error en el formulario.";
        return;
      }

      const email = emailInput.value.trim();
      const password = passwordInput.value.trim();

      if (email === "" || password === "") {
        mensajeLogin.className = "mensaje mensaje-error";
        mensajeLogin.textContent = "Completa todos los campos.";
        return;
      }

      try {
        const datos = new URLSearchParams();
        datos.append("email", email);
        datos.append("password", password);

        const respuesta = await fetch("login", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: datos.toString()
        });

        const texto = await respuesta.text();
        console.log("Respuesta login:", texto);

        const partes = texto.split("|");

        if (partes[0] === "OK") {
          const idUsuario = partes[1] ? partes[1].trim() : "";
          const nombreUsuario = partes[2] ? partes[2].trim() : "";
          const emailUsuario = partes[3] ? partes[3].trim() : "";
          const admin = partes[4] ? partes[4].trim() : "0";

          localStorage.setItem("idUsuario", idUsuario);
          localStorage.setItem("nombreUsuario", nombreUsuario);
          localStorage.setItem("emailUsuario", emailUsuario);

          mensajeLogin.className = "mensaje mensaje-ok";
          mensajeLogin.textContent = "Inicio de sesión correcto";

          setTimeout(() => {
            if (admin === "1") {
              window.location.href = "admin";
            } else {
              window.location.href = "comentarios.html";
            }
          }, 1000);

        } else {
          mensajeLogin.className = "mensaje mensaje-error";
          mensajeLogin.textContent = texto;
        }

      } catch (error) {
        console.error("Error login:", error);
        mensajeLogin.className = "mensaje mensaje-error";
        mensajeLogin.textContent = "Error al iniciar sesión.";
      }
    });
  }

  configurarMinFechaReserva();
  cargarComentarios();
  cargarCalendarioReservas();
  configurarFormularioReserva();
  configurarCalendarioReservaInput();
});

/**
 * Normaliza el formato de las fechas recibidas desde el servidor.
 *
 * @param {Array} datos lista de fechas o de objetos con fechas
 * @returns {Array} lista de fechas en formato válido
 */
function normalizarFechasReservas(datos) {
  if (!Array.isArray(datos)) {
    return [];
  }

  return datos
    .map(function (item) {
      if (typeof item === "string") {
        return item;
      }

      if (item && typeof item === "object" && item.fecha) {
        return item.fecha;
      }

      if (item && typeof item === "object" && item.fechaReserva) {
        return item.fechaReserva;
      }

      return null;
    })
    .filter(function (fecha) {
      return fecha !== null && fecha !== "";
    });
}

/**
 * Carga y muestra los comentarios en la página.
 */
async function cargarComentarios() {
  const contenedor = document.getElementById("listaComentarios");

  if (!contenedor) {
    return;
  }

  try {
    const respuesta = await fetch("comentarios");

    if (!respuesta.ok) {
      throw new Error("No se pudieron cargar los comentarios");
    }

    const comentarios = await respuesta.json();

    contenedor.innerHTML = "";

    if (!comentarios || comentarios.length === 0) {
      contenedor.innerHTML = '<p class="sin-comentarios">No hay comentarios publicados todavía.</p>';
      return;
    }

    comentarios.forEach(function (comentario) {
      const tarjeta = document.createElement("div");
      tarjeta.className = "comentario-card";

      const modoTexto = comentario.modo == 1 ? "Terror" : "Normal";

      tarjeta.innerHTML = `
        <div class="comentario-header">
          <span class="comentario-autor">${comentario.nombreUser}</span>
          <span class="comentario-fecha">${comentario.fechaComentario}</span>
        </div>
        <div class="comentario-modo">Modo: ${modoTexto}</div>
        <div class="comentario-opinion">${comentario.opinion}</div>
      `;

      contenedor.appendChild(tarjeta);
    });

  } catch (error) {
    console.error("Error al cargar comentarios:", error);
    contenedor.innerHTML = '<p class="sin-comentarios">Error al cargar los comentarios.</p>';
  }
}

/**
 * Carga las reservas desde el servidor y actualiza el calendario visual.
 */
async function cargarCalendarioReservas() {
  const contenedor = document.getElementById("calendarioReservas");

  if (!contenedor) {
    return;
  }

  try {
    const respuesta = await fetch("reservas");

    if (!respuesta.ok) {
      throw new Error("No se pudieron cargar las reservas");
    }

    const datos = await respuesta.json();
    const fechasOcupadas = normalizarFechasReservas(datos);

    fechasReservadas = fechasOcupadas;
    pintarCalendario(contenedor, fechasOcupadas);

  } catch (error) {
    console.error("Error al cargar calendario:", error);
    contenedor.innerHTML = "<p>Error al cargar el calendario.</p>";
  }
}

/**
 * Dibuja el calendario de reservas de los próximos tres meses.
 *
 * @param {HTMLElement} contenedor elemento donde se pintará el calendario
 * @param {Array} fechasOcupadas fechas que ya están reservadas
 */
function pintarCalendario(contenedor, fechasOcupadas) {
  contenedor.innerHTML = "";

  const hoy = new Date();

  for (let offsetMes = 0; offsetMes < 3; offsetMes++) {
    const fechaMes = new Date(hoy.getFullYear(), hoy.getMonth() + offsetMes, 1);
    const year = fechaMes.getFullYear();
    const month = fechaMes.getMonth();

    const bloqueMes = document.createElement("div");
    bloqueMes.className = "bloque-mes";

    const tituloMes = document.createElement("h4");
    tituloMes.className = "titulo-mes";
    tituloMes.textContent = fechaMes.toLocaleDateString("es-ES", {
      month: "long",
      year: "numeric"
    });

    const gridMes = document.createElement("div");
    gridMes.className = "grid-mes";

    const diasSemana = ["L", "M", "X", "J", "V", "S", "D"];
    diasSemana.forEach(function (dia) {
      const cabecera = document.createElement("div");
      cabecera.className = "dia-semana";
      cabecera.textContent = dia;
      gridMes.appendChild(cabecera);
    });

    const primerDia = new Date(year, month, 1);
    const ultimoDia = new Date(year, month + 1, 0);

    const diaSemanaInicio = primerDia.getDay();
    const totalDias = ultimoDia.getDate();

    const inicio = diaSemanaInicio === 0 ? 6 : diaSemanaInicio - 1;

    for (let i = 0; i < inicio; i++) {
      const vacio = document.createElement("div");
      vacio.className = "dia-calendario dia-vacio";
      gridMes.appendChild(vacio);
    }

    for (let dia = 1; dia <= totalDias; dia++) {
      const fechaTexto = formatearFecha(year, month + 1, dia);

      const celda = document.createElement("div");
      celda.className = "dia-calendario";

      if (fechasOcupadas.includes(fechaTexto)) {
        celda.classList.add("dia-ocupado");
      } else {
        celda.classList.add("dia-disponible");
      }

      celda.textContent = dia;
      gridMes.appendChild(celda);
    }

    bloqueMes.appendChild(tituloMes);
    bloqueMes.appendChild(gridMes);
    contenedor.appendChild(bloqueMes);
  }
}

/**
 * Da formato a una fecha en formato YYYY-MM-DD.
 *
 * @param {number} year año
 * @param {number} month mes
 * @param {number} day día
 * @returns {string} fecha formateada
 */
function formatearFecha(year, month, day) {
  const mm = month < 10 ? "0" + month : month;
  const dd = day < 10 ? "0" + day : day;
  return year + "-" + mm + "-" + dd;
}

/**
 * Configura el formulario de reserva y gestiona su envío al servidor.
 */
function configurarFormularioReserva() {
  const form = document.getElementById("formReserva");
  const mensaje = document.getElementById("mensajeReserva");

  if (!form || !mensaje) {
    return;
  }

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const nombreInput = document.getElementById("nombreReserva");
    const telefonoInput = document.getElementById("telefonoReserva");
    const fechaInput = document.getElementById("fechaReserva");
    const emailInput = document.getElementById("emailReserva");
    const numPlayerInput = document.getElementById("numPlayerReserva");
    const modoInput = document.getElementById("modoReserva");
    const notasInput = document.getElementById("notasReserva");

    if (!nombreInput || !telefonoInput || !fechaInput || !emailInput || !numPlayerInput || !modoInput || !notasInput) {
      mensaje.className = "mensaje mensaje-error";
      mensaje.textContent = "Error en el formulario de reserva.";
      return;
    }

    const nombre = nombreInput.value.trim();
    const telefono = telefonoInput.value.trim();
    const fecha = fechaInput.value;
    const email = emailInput.value.trim();
    const numPlayer = numPlayerInput.value;
    const modo = modoInput.value;
    const notas = notasInput.value.trim();

    if (!nombre || !telefono || !fecha || !email || !numPlayer || !modo) {
      mensaje.className = "mensaje mensaje-error";
      mensaje.textContent = "Completa todos los campos obligatorios.";
      return;
    }

    if (fechasReservadas.includes(fecha)) {
      mensaje.className = "mensaje mensaje-error";
      mensaje.textContent = "Ese día ya está reservado. Elige otra fecha.";
      return;
    }

    try {
      const datos = new URLSearchParams();
      datos.append("nombre", nombre);
      datos.append("telefono", telefono);
      datos.append("fecha", fecha);
      datos.append("email", email);
      datos.append("numPlayer", numPlayer);
      datos.append("modo", modo);
      datos.append("notas", notas);

      const respuesta = await fetch("guardarReserva", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: datos.toString()
      });

      const resultado = await respuesta.text();

      if (resultado.toLowerCase().includes("correctamente")) {
        mensaje.className = "mensaje mensaje-ok";
        form.reset();
        await cargarCalendarioReservas();
        await configurarCalendarioReservaInput();
      } else {
        mensaje.className = "mensaje mensaje-error";
      }

      mensaje.textContent = resultado;

    } catch (error) {
      console.error("Error al guardar la reserva:", error);
      mensaje.className = "mensaje mensaje-error";
      mensaje.textContent = "Error al guardar la reserva.";
    }
  });
}

/**
 * Configura la fecha mínima permitida en el formulario de reserva.
 */
function configurarMinFechaReserva() {
  const fechaInput = document.getElementById("fechaReserva");

  if (fechaInput) {
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, "0");
    const dd = String(hoy.getDate()).padStart(2, "0");
    fechaInput.min = `${yyyy}-${mm}-${dd}`;
  }
}

/**
 * Configura el calendario interactivo del campo fecha usando Flatpickr.
 */
async function configurarCalendarioReservaInput() {
  const inputFecha = document.getElementById("fechaReserva");

  if (!inputFecha || typeof flatpickr === "undefined") {
    return;
  }

  try {
    const respuesta = await fetch("reservas");

    if (!respuesta.ok) {
      throw new Error("No se pudieron cargar las reservas");
    }

    const datos = await respuesta.json();
    const fechasOcupadas = normalizarFechasReservas(datos);

    flatpickr("#fechaReserva", {
      dateFormat: "Y-m-d",
      minDate: "today",
      disable: fechasOcupadas
    });

  } catch (error) {
    console.error("Error al configurar calendario:", error);
  }
}
document.addEventListener("DOMContentLoaded", function () {
  const idUsuario = localStorage.getItem("idUsuario");
  const btn = document.getElementById("btnComentario");

  if (idUsuario && btn) {
    btn.style.display = "inline-block";

    btn.addEventListener("click", function () {
      window.location.href = "comentarios.html";
    });
  }
});