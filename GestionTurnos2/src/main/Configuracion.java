package main;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import archivos.EstrategiaLlamada;
import archivos.FactoryArchivoTexto;
import archivos.FactoryArchivosJSON;
import archivos.FactoryArchivosXML;
import archivos.IFactoryArchivo;
import archivos.OrdenPorAfinidad;
import archivos.OrdenarColaRangoEtario;

public class Configuracion {
	private static Configuracion instance = null;
	private EstrategiaLlamada estrategiaLlamada = null;
	private IFactoryArchivo factoryArchivos = null;

	private Configuracion() {
	}

	public static Configuracion getInstance() {
		if (instance == null)
			instance = new Configuracion();

		return instance;
	}

	public void configurar(String archivoConfiguracion) {
		JsonObject parametrosConfiguracion = leerArchivoConfiguracion(archivoConfiguracion);
		if (parametrosConfiguracion != null) {
			aplicarEstrategiaLlamada(parametrosConfiguracion.get("ordenAtencion").getAsString());
			setearFactoryArchivos(parametrosConfiguracion.get("archivo").getAsString());
		}else
			System.out.println("Error al configurar servidor gestion de turnos");
	}

	private JsonObject leerArchivoConfiguracion(String archivo) {

		JsonObject jsonObject = null;
		try (FileReader reader = new FileReader(archivo)) {
			// Parsea el archivo JSON a un JsonElement
			JsonElement jsonElement = JsonParser.parseReader(reader);
			// Convierte el JsonElement a un JsonObject
			jsonObject = jsonElement.getAsJsonObject();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("No pudo leerse el archivo de configuracion");
		}

		return jsonObject;
	}

	private void aplicarEstrategiaLlamada(String ordenAtencion) {
		switch (ordenAtencion) {
		case "grupo etario":
			this.estrategiaLlamada = new OrdenarColaRangoEtario();
			break;
		case "grupo afinidad":
			this.estrategiaLlamada = new OrdenPorAfinidad();
			break;
		}
	}

	private void setearFactoryArchivos(String archivoClientes) {
		String extensionArchivo = archivoClientes.substring(archivoClientes.lastIndexOf('.') + 1);
		switch (extensionArchivo.toLowerCase()) {
		case "json":
			this.factoryArchivos = new FactoryArchivosJSON();
			break;
		case "xml":
			this.factoryArchivos = new FactoryArchivosXML();
			break;
		case "txt":
			this.factoryArchivos = new FactoryArchivoTexto();
			break;
		default:
			System.out.println("Tipo de archivo no soportado: " + extensionArchivo);
		}
	}

	public EstrategiaLlamada getEstrategiaLlamada() {
		return estrategiaLlamada;
	}

	public IFactoryArchivo getFactoryArchivos() {
		return factoryArchivos;
	}
}
