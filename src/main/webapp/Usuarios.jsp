<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Usuarios</title>

<style>

body{
	font-family: Arial;
	background:#f4f6f9;
	padding:30px;
}

.card{
	background:white;
	padding:20px;
	border-radius:10px;
	box-shadow:0px 0px 10px rgba(0,0,0,0.1);
}

input, select{
	padding:10px;
	margin:10px;
	width:220px;
}

button{
	padding:10px;
	border:none;
	border-radius:5px;
	color:white;
	cursor:pointer;
	margin:5px;
}

.listar{
	background:#17a2b8;
}

.actualizar{
	background:#28a745;
}

.eliminar{
	background:crimson;
}

.menu{
	background:#343a40;
}

table{
	width:100%;
	border-collapse:collapse;
	margin-top:20px;
}

th{
	background:#007bff;
	color:white;
}

th,td{
	border:1px solid #ccc;
	padding:10px;
	text-align:center;
}

</style>

</head>

<body>

<div class="card">

<h1>Gestion Usuarios</h1>

<!-- BOTON MENU -->

<button
class="menu"
onclick="window.location.href='menu.jsp'">

	Volver al Menu

</button>

<hr>

<input
type="number"
id="id"
placeholder="ID Usuario">

<input
type="text"
id="nombre"
placeholder="Nombre">

<input
type="email"
id="correo"
placeholder="Correo">

<input
type="text"
id="departamento"
placeholder="Departamento">

<select id="rol">

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

<br>

<button
class="listar"
onclick="listar()">

	Listar Usuarios

</button>

<button
class="actualizar"
onclick="actualizar()">

	Actualizar Usuario

</button>

<button
class="eliminar"
onclick="eliminarUsuario()">

	Eliminar Usuario

</button>

<table>

<thead>

<tr>

<th>ID</th>
<th>Nombre</th>
<th>Correo</th>
<th>Departamento</th>
<th>Rol</th>

</tr>

</thead>

<tbody id="tabla">
</tbody>

</table>

</div>

<script>

// ======================================
// URL RENDER
// ======================================

const URL =
"/api/usuarios";


// ======================================
// VALIDAR SESION
// ======================================

const usuario =
JSON.parse(
	localStorage.getItem("usuarioLogueado")
);

if(!usuario){

	alert("Debe iniciar sesion");

	window.location.href =
	"login.jsp";
}


// ======================================
// LISTAR
// ======================================

async function listar(){

	try{

		const respuesta =
		await fetch(URL);

		const datos =
		await respuesta.json();

		let html = "";

		if(datos.length == 0){

			html =

			"<tr>" +

				"<td colspan='5'>" +

					"No hay usuarios" +

				"</td>" +

			"</tr>";
		}

		datos.forEach(function(u){

			html +=

			"<tr>" +

			"<td>" + u.id + "</td>" +

			"<td>" + u.nombre + "</td>" +

			"<td>" + u.correo + "</td>" +

			"<td>" + u.departamento + "</td>" +

			"<td>" + u.rol + "</td>" +

			"</tr>";
		});

		document
		.getElementById("tabla")
		.innerHTML = html;

	}catch(error){

		console.log(error);

		alert("Error listando usuarios");
	}
}


// ======================================
// ACTUALIZAR
// ======================================

async function actualizar(){

	const id =
	document.getElementById("id").value;

	if(id == ""){

		alert("Ingrese ID");

		return;
	}

	const usuarioActualizar = {

		nombre:
		document.getElementById("nombre").value,

		correo:
		document.getElementById("correo").value,

		departamento:
		document.getElementById("departamento").value,

		rol:
		document.getElementById("rol").value
	};

	try{

		const respuesta =
		await fetch(

			URL + "/" + id,

			{
				method:"PUT",

				headers:{
					"Content-Type":"application/json"
				},

				body: JSON.stringify(usuarioActualizar)
			}
		);

		if(respuesta.ok){

			alert("Usuario actualizado");

			listar();

		}else{

			alert("Error actualizando usuario");
		}

	}catch(error){

		console.log(error);

		alert("Error del sistema");
	}
}


// ======================================
// ELIMINAR
// ======================================

async function eliminarUsuario(){

	const id =
	document.getElementById("id").value;

	if(id == ""){

		alert("Ingrese ID");

		return;
	}

	try{

		const respuesta =
		await fetch(

			URL + "/" + id,

			{
				method:"DELETE"
			}
		);

		if(respuesta.ok){

			alert("Usuario eliminado");

			listar();

		}else{

			alert("Error eliminando usuario");
		}

	}catch(error){

		console.log(error);

		alert("Error del sistema");
	}
}

</script>

</body>
</html>