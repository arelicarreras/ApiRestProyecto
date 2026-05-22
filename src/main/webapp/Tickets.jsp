<script>

const URL =
"/api/tickets";


// =====================================
// USUARIO LOGUEADO
// =====================================

const usuarioLogueado =
JSON.parse(
    localStorage.getItem("usuarioLogueado")
);


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
            id: usuarioLogueado.id
        }
    };

    const respuesta = await fetch(URL, {

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(ticket)
    });

    if(respuesta.ok){

        alert("Ticket creado");

        cargarTickets();

    }else{

        alert("Error creando ticket");
    }
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
             (t.fechaCreacion
                 ? new Date(t.fechaCreacion)
                    .toLocaleString("es-GT")
                 : "") +
         "</td>" +

         "<td>" +
             (t.fechaCierre
                 ? new Date(t.fechaCierre)
                    .toLocaleString("es-GT")
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
                    id: usuarioLogueado.id
                }
            })
        }
    );

    if(respuesta.ok){

        alert("Estado actualizado");

        cargarTickets();

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

    cargarTickets();
}

</script>