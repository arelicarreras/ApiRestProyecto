<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Menu Principal</title>

<style>

body{
	font-family: Arial;
	background:#f4f6f9;
	padding:30px;
}

.container{
	width:800px;
	margin:auto;
}

.card{
	background:white;
	padding:30px;
	border-radius:10px;
	box-shadow:0px 0px 10px rgba(0,0,0,0.1);
}

h1{
	text-align:center;
	color:#333;
	margin-bottom:30px;
}

.usuarioInfo{
	background:#e9ecef;
	padding:15px;
	border-radius:10px;
	margin-bottom:25px;
	font-size:16px;
}

.grid{
	display:grid;
	grid-template-columns:1fr 1fr;
	gap:20px;
}

button{
	padding:25px;
	font-size:18px;
	border:none;
	border-radius:10px;
	cursor:pointer;
	color:white;
	font-weight:bold;
	transition:0.3s;
}

button:hover{
	transform:scale(1.03);
}

.ticket{
	background:#007bff;
}

.timeline{
	background:#17a2b8;
}

.adjunto{
	background:#28a745;
}

.usuario{
	background:#6f42c1;
}

.logout{
	background:crimson;
	width:100%;
	margin-top:25px;
}

</style>

</head>

<body>

<div class="container">

	<div class="card">

		<h1>Menu Principal</h1>

		<div class="usuarioInfo">

			<b>Usuario:</b>
			<span id="nombreUsuario"></span>

			<br><br>

			<b>Correo:</b>
			<span id="correoUsuario"></span>

			<br><br>

			<b>Rol:</b>
			<span id="rolUsuario"></span>

		</div>

		<div class="grid">

			<!-- TICKETS -->

			<button
			class="ticket"
			onclick="window.location.href='Tickets.jsp'">

				Tickets

			</button>


			<!-- TIMELINE -->

			<button
			class="timeline"
			onclick="window.location.href='Timeline.jsp'">

				Timeline

			</button>


			<!-- ADJUNTOS -->

			<button
			class="adjunto"
			onclick="window.location.href='TicketAdjunto.jsp'">

				Adjuntos

			</button>


			<!-- USUARIOS -->

			<button
			class="usuario"
			onclick="window.location.href='Usuarios.jsp'">

				Usuarios

			</button>

		</div>


		<!-- CERRAR SESION -->

		<button
		class="logout"
		onclick="logout()">

			Cerrar Sesion

		</button>

	</div>

</div>

<script>

// =====================================
// USUARIO LOGUEADO
// =====================================

const usuario =
JSON.parse(
	localStorage.getItem("usuarioLogueado")
);


// =====================================
// VALIDAR SESION
// =====================================

if(!usuario){

	alert("Debe iniciar sesion");

	window.location.href =
	"login.jsp";
}


// =====================================
// MOSTRAR DATOS
// =====================================

document.getElementById(
	"nombreUsuario"
).innerHTML =
usuario.nombre;

document.getElementById(
	"correoUsuario"
).innerHTML =
usuario.correo;

document.getElementById(
	"rolUsuario"
).innerHTML =
usuario.rol;


// =====================================
// LOGOUT
// =====================================

function logout(){

	// ELIMINAR SESION
	localStorage.removeItem(
		"usuarioLogueado"
	);

	// REDIRECCIONAR
	window.location.href =
	"login.jsp";
}

</script>

</body>
</html>