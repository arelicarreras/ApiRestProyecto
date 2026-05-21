<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Login</title>

<style>

body{
	font-family: Arial;
	background: #f4f6f9;
	padding: 30px;
}

.container{
	width: 400px;
	margin: auto;
}

.card{
	background: white;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
}

input, select{
	width: 100%;
	padding: 10px;
	margin-top: 10px;
	margin-bottom: 15px;
	border-radius: 5px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

button{
	width: 100%;
	padding: 10px;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	margin-top: 10px;
}

.login{
	background: #007bff;
}

.registrar{
	background: #28a745;
}

#registroForm{
	display: none;
}

.tabs{
	display:flex;
	gap:10px;
	margin-bottom:20px;
}

.tab{
	flex:1;
	background:#ccc;
	color:black;
}

.active{
	background:#007bff;
	color:white;
}

</style>

</head>

<body>

<div class="container">

	<div class="card">

		<h1>Sistema Login</h1>

		<!-- BOTONES SUPERIORES -->

		<div class="tabs">

			<button
			id="btnLogin"
			class="tab active"
			onclick="mostrarLogin()">

				Iniciar Sesion

			</button>

			<button
			id="btnRegistro"
			class="tab"
			onclick="mostrarRegistro()">

				Registrarse

			</button>

		</div>


		<!-- LOGIN -->

		<div id="loginForm">

			<h2>Iniciar Sesion</h2>

			<input
			type="email"
			id="correo"
			placeholder="Correo">

			<input
			type="password"
			id="password"
			placeholder="Password">

			<button
			class="login"
			onclick="login()">

				Ingresar

			</button>

		</div>


		<!-- REGISTRO -->

		<div id="registroForm">

			<h2>Registrar Usuario</h2>

			<input
			type="text"
			id="nombreRegistro"
			placeholder="Nombre">

			<input
			type="email"
			id="correoRegistro"
			placeholder="Correo">

			<input
			type="password"
			id="passwordRegistro"
			placeholder="Password">

			<input
			type="text"
			id="departamentoRegistro"
			placeholder="Departamento">

			<select id="rolRegistro">

				<option value="TECNICO">
					TECNICO
				</option>

				<option value="SUPERVISOR">
					SUPERVISOR
				</option>

				<option value="CLIENTE">
					CLIENTE
				</option>

			</select>

			<button
			class="registrar"
			onclick="registrar()">

				Crear Usuario

			</button>

		</div>

	</div>

</div>

<script>

const URL_LOGIN =
"http://localhost:8080/ApiRestProyecto/api/usuarios/login";

const URL_USUARIOS =
"http://localhost:8080/ApiRestProyecto/api/usuarios";


// =====================================
// MOSTRAR LOGIN
// =====================================

function mostrarLogin(){

	document.getElementById("loginForm")
	.style.display = "block";

	document.getElementById("registroForm")
	.style.display = "none";

	document.getElementById("btnLogin")
	.classList.add("active");

	document.getElementById("btnRegistro")
	.classList.remove("active");
}


// =====================================
// MOSTRAR REGISTRO
// =====================================

function mostrarRegistro(){

	document.getElementById("loginForm")
	.style.display = "none";

	document.getElementById("registroForm")
	.style.display = "block";

	document.getElementById("btnRegistro")
	.classList.add("active");

	document.getElementById("btnLogin")
	.classList.remove("active");
}


// =====================================
// LOGIN
// =====================================

async function login(){

	const correo =
	document.getElementById("correo").value;

	const password =
	document.getElementById("password").value;

	const respuesta =
	await fetch(

		URL_LOGIN,

		{

			method:"POST",

			headers:{
				"Content-Type":"application/json"
			},

			body: JSON.stringify({

				correo: correo,

				password: password
			})
		}
	);

	if(respuesta.ok){

		const usuario =
		await respuesta.json();

		alert(

			"Bienvenido " +

			usuario.nombre
		);

		window.location.href =
		"menu.jsp";

	}else{

		alert(
			"Correo o password incorrectos"
		);
	}
}


// =====================================
// REGISTRAR
// =====================================

async function registrar(){

	const usuario = {

		nombre:
		document.getElementById("nombreRegistro").value,

		correo:
		document.getElementById("correoRegistro").value,

		password:
		document.getElementById("passwordRegistro").value,

		departamento:
		document.getElementById("departamentoRegistro").value,

		rol:
		document.getElementById("rolRegistro").value
	};

	const respuesta =
	await fetch(

		URL_USUARIOS,

		{

			method:"POST",

			headers:{
				"Content-Type":"application/json"
			},

			body: JSON.stringify(usuario)
		}
	);

	if(respuesta.ok){

		alert("Usuario registrado");

		mostrarLogin();

	}else{

		alert("Error registrando usuario");
	}
}

</script>

</body>
</html>