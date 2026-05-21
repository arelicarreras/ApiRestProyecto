<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sistema Tickets</title>

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

input, select{
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

.crear{
    background: #007bff;
}

.listar{
    background: #17a2b8;
}

.eliminar{
    background: crimson;
}

.actualizar{
    background: green;
}

table{
    width: 100%;
    border-collapse: collapse;
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

        <h1>Sistema de Tickets</h1>

        <form id="formTicket">

            <div>
                <label>Codigo Ticket</label><br>
                <input type="text" id="codigo">
            </div>

            <div>
                <label>Descripcion</label><br>
                <input type="text" id="descripcion">
            </div>

            <div>
                <label>Departamento</label><br>
                <input type="text" id="departamento">
            </div>

            <div>

                <label>Estado</label><br>

                <select id="estado">

                    <option value="CREADO">
                        CREADO
                    </option>

                    <option value="ASIGNADO">
                        ASIGNADO
                    </option>

                    <option value="VALIDACION">
                        VALIDACION
                    </option>

                    <option value="DEVUELTO">
                        DEVUELTO
                    </option>

                    <option value="RECHAZADO">
                        RECHAZADO
                    </option>

                    <option value="FINALIZADO">
                        FINALIZADO
                    </option>

                </select>

            </div>

            <button class="crear" type="submit">
                Crear Ticket
            </button>

            <button
            class="listar"
            type="button"
            onclick="cargarTickets()">

                Listar Tickets

            </button>

            <button
            class="actualizar"
            type="button"
            onclick="actualizarEstado()">

                Actualizar Estado

            </button>

            <button
            class="eliminar"
            type="button"
            onclick="eliminarManual()">

                Eliminar Ticket

            </button>

        </form>

    </div>


    <!-- TABLA -->

    <div
    class="card"
    id="tablaContainer"
    style="display:none;">

        <h2>Lista de Tickets</h2>

        <table>

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Codigo</th>
                    <th>Descripcion</th>
                    <th>Departamento</th>
                    <th>Estado</th>
                    <th>Creado Por</th>
                    <th>Correo</th>
                    <th>Rol</th>
                    <th>Asignado A</th>
                    <th>Fecha Creacion</th>
                    <th>Fecha Cierre</th>

                </tr>

            </thead>

            <tbody id="tablaTickets">

            </tbody>

        </table>

    </div>

</div>

<script>

const URL =
"/api/tickets";


// =====================================
// CREAR
// =====================================

document.getElementById("formTicket")
.addEventListener("submit", async function(e){

    e.preventDefault();

    const ticket = {

        descripcion:
            document.getElementById("descripcion").value,

        departamento:
            document.getElementById("departamento").value,

        estado:
            document.getElementById("estado").value,

        creadoPor:{
            id:1
        }
    };

    await fetch(URL, {

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(ticket)
    });

    alert("Ticket creado");
});

//=====================================
//LISTAR
//=====================================

async function cargarTickets(){

 const respuesta = await fetch(URL);

 const tickets = await respuesta.json();

 console.log(tickets);

 let html = "";

 tickets.forEach(function(t){

     html +=
     "<tr>" +

         "<td>" + t.id + "</td>" +

         "<td>" + t.codigo + "</td>" +

         "<td>" + t.descripcion + "</td>" +

         "<td>" + t.departamento + "</td>" +

         "<td>" + t.estado + "</td>" +

         "<td>" +
             (t.creadoPor
                 ? t.creadoPor.nombre
                 : "") +
         "</td>" +

         "<td>" +
             (t.creadoPor
                 ? t.creadoPor.correo
                 : "") +
         "</td>" +

         "<td>" +
             (t.creadoPor
                 ? t.creadoPor.rol
                 : "") +
         "</td>" +

         "<td>" +
             (t.asignadoA
                 ? t.asignadoA.nombre
                 : "Sin asignar") +
         "</td>" +

         "<td>" +
             t.fechaCreacion +
         "</td>" +

         "<td>" +
             (t.fechaCierre
                 ? t.fechaCierre
                 : "Pendiente") +
         "</td>" +

     "</tr>";
 });

 document.getElementById("tablaTickets")
 .innerHTML = html;

 document.getElementById("tablaContainer")
 .style.display = "block";
}


// =====================================
// ACTUALIZAR ESTADO
// =====================================

async function actualizarEstado(){

    const codigo =
        document.getElementById("codigo").value;

    const estado =
        document.getElementById("estado").value;

    if(codigo == ""){

        alert("Ingrese codigo");

        return;
    }

    let endpoint = "";

    if(estado == "ASIGNADO"){

        endpoint = "aceptar";
    }

    else if(estado == "RECHAZADO"){

        endpoint = "rechazar";
    }

    else if(estado == "VALIDACION"){

        endpoint = "validacion";
    }

    else if(estado == "DEVUELTO"){

        endpoint = "devolver";
    }

    else if(estado == "FINALIZADO"){

        endpoint = "finalizar";
    }

    else{

        alert("CREADO solo se usa al crear");

        return;
    }

    const respuesta = await fetch(

        URL + "/" + codigo + "/" + endpoint,

        {

            method:"PUT",

            headers:{
                "Content-Type":"application/json"
            },

            body: JSON.stringify({

                descripcion:"Cambio de estado",

                asignadoA:{
                    id:1
                },

                creadoPor:{
                    nombre:"Tecnico"
                }
            })
        }
    );

    if(respuesta.ok){

        alert("Estado actualizado");

    }else{

        const error = await respuesta.text();

        alert(error);
    }
}


// =====================================
// ELIMINAR MANUAL
// =====================================

async function eliminarManual(){

    const codigo =
        document.getElementById("codigo").value;

    if(codigo == ""){

        alert("Ingrese codigo");

        return;
    }

    await fetch(
        URL + "/" + codigo,
        {
            method:"DELETE"
        }
    );

    alert("Ticket eliminado");
}

</script>

</body>
</html>