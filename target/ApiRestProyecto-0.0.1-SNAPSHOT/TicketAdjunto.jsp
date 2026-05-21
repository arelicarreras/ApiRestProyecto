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
	background:#007bff;
	color:white;
	border:none;
	border-radius:5px;
	cursor:pointer;
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

<input
type="number"
id="ticketId"
placeholder="ID Ticket">

<input
type="file"
id="archivo">

<button onclick="subir()">

Subir Archivo

</button>

<button onclick="listar()">

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

const URL =
	"http://localhost:8080/ApiRestProyecto/api/adjuntos";

async function subir(){

	const archivo =
	document.getElementById("archivo").files[0];

	const ticketId =
	document.getElementById("ticketId").value;

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

	}else{
		const texto =
			await respuesta.text();

			alert(texto);
	}
}

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
		a.nombreArchivo +
		"</td>" +

		"<td>" +
		a.rutaArchivo +
		"</td>" +

		"<td>" +
		a.ticket.codigo +
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