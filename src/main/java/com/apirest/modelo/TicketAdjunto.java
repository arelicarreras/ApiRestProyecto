package com.apirest.modelo;

public class TicketAdjunto {
	private int id;
	private Ticket ticket;
	private String nombreOriginal;
	private String rutaArchivo;
	private String tipoMime;
	private long tamanio;
	
	public TicketAdjunto() {}

	public TicketAdjunto(int id, Ticket ticket, String nombreOriginal, String rutaArchivo, String tipoMime,
			long tamanio) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.nombreOriginal = nombreOriginal;
		this.rutaArchivo = rutaArchivo;
		this.tipoMime = tipoMime;
		this.tamanio = tamanio;
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

	public String getNombreOriginal() {
		return nombreOriginal;
	}

	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	public long getTamanio() {
		return tamanio;
	}

	public void setTamanio(long tamanio) {
		this.tamanio = tamanio;
	}

	
}
