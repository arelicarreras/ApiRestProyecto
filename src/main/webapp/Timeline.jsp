<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Timeline Tickets</title>

<style>

body{
    font-family: Arial;
    background: #f4f6f9;
    margin: 0;
    padding: 30px;
}

.container{
    width: 95%;
    margin: auto;
}

.card{
    background: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
    margin-bottom: 30px;
}

h1{
    color: #333;
}

input{
    width: 250px;
    padding: 10px;
    margin-top: 5px;
    margin-bottom: 15px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

button{
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    color: white;
    margin: 3px;
}

.listar{
    background: #17a2b8;
}

.eliminar{
    background: crimson;
}

.menu{
    background:#343a40;
}

table{
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

th{
    background: #007bff;
    color: white;
}

th, td{
    padding: 10px;
    border: 1px solid #ccc;
    text-align: center;
}

</style>

</head>

<body>

<div class="container">

    <div class="card">

        <h1>Timeline Tickets</h1>

        <button
        class="menu"
        onclick="window.location.href='menu.jsp'">

            Volver al Menu

        </button>

        <hr>

        <div>

            <label>Codigo Ticket</label><br>

            <input
            type="text"
            id="codigo"
            placeholder="TK-123456">

        </div>

        <button
        class="listar"
        type="button"
        onclick="cargarTimeline()">

            Ver Timeline

        </button>

        <button
        class="eliminar"
        type="button"
        onclick="eliminarEvento()">

            Eliminar Timeline

        </button>

    </div>


    <!-- TABLA -->

    <div
    class="card"
    id="tablaContainer">

        <h2>Timeline del Ticket</h2>

        <table>

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Codigo Ticket</th>
                    <th>Estado</th>
                    <th>Actor</th>
                    <th>Observacion</th>
                    <th>Fecha Evento</th>

                </tr>

            </thead>

            <tbody id="tablaTimeline">

            </tbody>

        </table>

    </div>

</div>

<script>

// =====================================
// URL RENDER
// =====================================

const URL =
"/api/timeline";


// =====================================
// VALIDAR SESION
// =====================================

const usuario =
JSON.parse(
	localStorage.getItem("usuarioLogueado")
);

if(!usuario){

	alert("Debe iniciar sesion");

	window.location.href =
	"login.jsp";
}


// =====================================
// FORMATEAR FECHA
// =====================================

function formatearFecha(fecha){

    if(!fecha){

        return "";
    }

    return new Date(fecha)
    .toLocaleString(
        "es-GT",
        {

            year:"numeric",
            month:"2-digit",
            day:"2-digit",

            hour:"2-digit",
            minute:"2-digit",
            second:"2-digit"
        }
    );
}


// =====================================
// LISTAR TIMELINE
// =====================================

async function cargarTimeline(){

    const codigo =
    document.getElementById("codigo").value;

    if(codigo == ""){

        alert("Ingrese codigo");

        return;
    }

    try{

        const respuesta =
        await fetch(URL + "/" + codigo);

        const timeline =
        await respuesta.json();

        let html = "";

        if(timeline.length == 0){

            html =

            "<tr>" +

                "<td colspan='6'>" +

                    "No hay registros" +

                "</td>" +

            "</tr>";
        }

        timeline.forEach(function(t){

            html +=

            "<tr>" +

                "<td>" +

                    (
                        t.id != null
                        ? t.id
                        : ""
                    ) +

                "</td>" +

                "<td>" +

                    (
                        t.ticket != null &&
                        t.ticket.codigo != null

                        ? t.ticket.codigo

                        : ""
                    ) +

                "</td>" +

                "<td>" +

                    (
                        t.estado != null
                        ? t.estado
                        : ""
                    ) +

                "</td>" +

                "<td>" +

                    (
                        t.actor != null &&
                        t.actor.nombre != null

                        ? t.actor.nombre

                        : "Sin actor"
                    ) +

                "</td>" +

                "<td>" +

                    (
                        t.observacion != null
                        ? t.observacion
                        : ""
                    ) +

                "</td>" +

                "<td>" +

                    formatearFecha(
                        t.fechaEvento
                    ) +

                "</td>" +

            "</tr>";
        });

        document.getElementById("tablaTimeline")
        .innerHTML = html;

    }catch(error){

        console.log(error);

        alert("Error cargando timeline");
    }
}


// =====================================
// ELIMINAR TIMELINE
// =====================================

async function eliminarEvento(){

    const codigo =
    document.getElementById("codigo").value;

    if(codigo == ""){

        alert("Ingrese codigo");

        return;
    }

    const respuesta =
    await fetch(

        URL + "/" + codigo,

        {
            method:"DELETE"
        }
    );

    if(respuesta.ok){

        alert("Timeline eliminado");

        cargarTimeline();

    }else{

        alert("Error eliminando timeline");
    }
}

</script>

</body>
</html>