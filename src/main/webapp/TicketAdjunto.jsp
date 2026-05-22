<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Adjuntos</title>

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

input{
	padding:10px;
	margin:10px;
}

button{
	padding:10px;
	color:white;
	border:none;
	border-radius:5px;
	cursor:pointer;
	margin:5px;
}

.subir{
	background:#007bff;
}

.listar{
	background:#17a2b8;
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

<h1>Subir Archivo</h1>

<!-- BOTON MENU -->

<button
class="menu"
onclick="window.location.href='menu.jsp'">

	Volver al Menu

</button>

<hr>

<input
type="number"
id="ticketId"
placeholder="ID Ticket">

<input
type="file"
id="archivo">

<button
class="subir"
onclick="subir()">

	Subir Archivo

</button>

<button
class="listar"
onclick="listar()">

	Listar Adjuntos

</button>

<table>

<thead>

<tr>

<th>ID</th>
<th>Archivo</th>
<th>Ruta</th>
<th>Ticket</th>

</tr>

</thead>

<tbody id="tabla">
</tbody>

</table>

</div>

<script>

// =====================================
// URL RENDER
// =====================================

const URL =
"/api/adjuntos";


// =====================================
// SUBIR
// =====================================

async function subir(){

	const archivo =
	document.getElementById("archivo").files[0];

	const ticketId =
	document.getElementById("ticketId").value;

	if(!archivo || ticketId == ""){

		alert("Seleccione archivo y ticket");

		return;
	}

	const formData =
	new FormData();

	formData.append(
		"archivo",
		archivo
	);

	formData.append(
		"ticketId",
		ticketId
	);

	const respuesta =
	await fetch(

		URL + "/subir",

		{
			method:"POST",
			body: formData
		}
	);

	if(respuesta.ok){

		alert("Archivo subido");

		listar();

	}else{

		const texto =
		await respuesta.text();

		alert(texto);
	}
}


// =====================================
// LISTAR
// =====================================

async function listar(){

	const respuesta =
	await fetch(URL);

	const datos =
	await respuesta.json();

	let html = "";

	datos.forEach(function(a){

		html +=

		"<tr>" +

		"<td>" + a.id + "</td>" +

		"<td>" +
		a.nombreOriginal +
		"</td>" +

		"<td>" +
		a.rutaArchivo +
		"</td>" +

		"<td>" +
		(a.ticket
			? a.ticket.codigo
			: "") +
		"</td>" +

		"</tr>";
	});

	document
	.getElementById("tabla")
	.innerHTML = html;
}

</script>

</body>
</html>