package com.apirest.modelo;

import java.sql.Timestamp;

public class Ticket {
	private int id;
	private String codigo;
	private String descripcion;
	private String departamento;
	private String estado;
	private Usuario creadoPor;
	private Usuario asignadoA;
	private Timestamp fechaCreacion;
	private Timestamp fechaCierre;
	
	public Ticket() {}

	public Ticket(int id, String codigo, String descripcion, String departamento, String estado, Usuario creadoPor,
			Usuario asignadoA, Timestamp fechaCreacion, Timestamp fechaCierre) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.departamento = departamento;
		this.estado = estado;
		this.creadoPor = creadoPor;
		this.asignadoA = asignadoA;
		this.fechaCreacion = fechaCreacion;
		this.fechaCierre = fechaCierre;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Usuario getAsignadoA() {
		return asignadoA;
	}

	public void setAsignadoA(Usuario asignadoA) {
		this.asignadoA = asignadoA;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	
}
