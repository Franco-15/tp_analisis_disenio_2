package main;

import archivos.EOrdenAtencion;

public class Configuracion {
	private EOrdenAtencion orden_atencion;
	private String archivo;
	

	public Configuracion() {}
	
	public EOrdenAtencion getOrden_atencion() {
		return orden_atencion;
	}

	public void setOrden_atencion(EOrdenAtencion orden_atencion) {
		this.orden_atencion = orden_atencion;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
}
