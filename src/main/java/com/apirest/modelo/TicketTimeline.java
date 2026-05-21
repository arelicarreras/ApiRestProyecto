package com.apirest.modelo;

import java.sql.Timestamp;

public class TicketTimeline {
	private int id;
	private Ticket ticket;
	private Usuario actor;
	private String estado;
	private String observacion;
	private Timestamp fechaEvento;
	
	public TicketTimeline() {}

	public TicketTimeline(int id, Ticket ticket, Usuario actor, String estado, String observacion,
			Timestamp fechaEvento) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.actor = actor;
		this.estado = estado;
		this.observacion = observacion;
		this.fechaEvento = fechaEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Usuario getActor() {
		return actor;
	}

	public void setActor(Usuario actor) {
		this.actor = actor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Timestamp getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Timestamp fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	
	
}
