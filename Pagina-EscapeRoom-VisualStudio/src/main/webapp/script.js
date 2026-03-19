const formRegistro = document.getElementById("formRegistro");
const mensajeRegistro = document.getElementById("mensajeRegistro");

formRegistro.addEventListener("submit", async function (e) {
  e.preventDefault();

  const nombreInput = document.getElementById("nombreRegistro");
  const emailInput = document.getElementById("emailRegistro");
  const passwordInput = document.getElementById("passwordRegistro");

  console.log("Nombre input:", nombreInput);
  console.log("Email input:", emailInput);
  console.log("Password input:", passwordInput);

  const nombre = nombreInput.value.trim();
  const email = emailInput.value.trim();
  const password = passwordInput.value.trim();

  console.log("Nombre:", nombre);
  console.log("Email:", email);
  console.log("Password:", password);

  if (nombre === "" || email === "" || password === "") {
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
    mensajeRegistro.textContent = resultado;

    if (resultado.includes("correctamente")) {
      formRegistro.reset();
    }

  } catch (error) {
    console.error(error);
    mensajeRegistro.textContent = "Error al conectar con el servidor.";
  }
});