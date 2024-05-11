package modelo.metricas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import comunes.IMetricas;

public class Metricas implements IMetricas {

	private static Metricas instance = null;

	private int clientesAtendidos;
	private int clientesNoAtendidos;
	private int tMaxEspera;
	private int tMinEspera;
	private int clientesEnEspera;
	private float tiempoPromedioEspera;
	private float tiempoTotalEspera;
	private int cantTotalClientesRegistrados;
	private Map<String, Object> metricasMap;

	// Constructor
	private Metricas() {
		// Inicializar variables aleatoriamente para simular datos dinámicos
		this.clientesAtendidos = 0;
		this.clientesNoAtendidos = 0;
		this.tMaxEspera = -1;
		this.tMinEspera = -1;
		this.clientesEnEspera = 0;
		this.tiempoTotalEspera = 0;
		this.tiempoPromedioEspera = 0;// (float) tiempoTotalEspera / clientesAtendidos; // Cálculo del promedio
		this.cantTotalClientesRegistrados = 0;// clientesAtendidos + clientesNoAtendidos;
		this.metricasMap = new HashMap<>();
	}

	public static Metricas getInstance() {
		if (instance == null)
			instance = new Metricas();

		return instance;
	}

	// Getters y setters
	public int getClientesAtendidos() {
		return this.clientesAtendidos;
	}

	public int getClientesNoAtendidos() {
		return this.clientesNoAtendidos;
	}

	public int gettMaxEspera() {
		return this.tMaxEspera;
	}

	public int gettMinEspera() {
		return this.tMinEspera;
	}

	public int getClientesEnEspera() {
		return this.clientesEnEspera;
	}

	public float getTiempoPromedioEspera() {
		return this.tiempoPromedioEspera;
	}

	public float getTiempoTotalEspera() {
		return this.tiempoTotalEspera;
	}

	public int getcantTotalClientesRegistrados() {
		return this.cantTotalClientesRegistrados;
	}

	public void actualizarClientesAtendidos(int cantidad) {
		this.clientesAtendidos = cantidad;
	}

	public void actualizarClientesNoAtendidos(int cantidad) {
		this.clientesNoAtendidos = cantidad;
	}

	public void actualizarTMaxEspera(LocalDateTime fechaLlegada) {
		LocalDateTime horaActual = LocalDateTime.now();
		Duration duracion = Duration.between(fechaLlegada, horaActual);
		int esperaEnMinutos = (int) duracion.toMinutes();

		if (this.tMaxEspera < esperaEnMinutos)
			this.tMaxEspera = esperaEnMinutos;

	}

	public void actualizarTMinEspera(LocalDateTime fechaLlegada) {

		LocalDateTime horaActual = LocalDateTime.now();
		Duration duracion = Duration.between(fechaLlegada, horaActual);
		int esperaEnMinutos = (int) duracion.toMinutes();

		if (this.tMinEspera == -1 || this.tMinEspera > esperaEnMinutos)
			this.tMinEspera = esperaEnMinutos;
	}

	public void actualizarClienteEnEspera(int cantidad) {
		this.clientesEnEspera = cantidad;
	}

	public void actualizarTiempoTotalEspera(LocalDateTime fechaLlegada) {
		LocalDateTime horaActual = LocalDateTime.now();
		Duration duracion = Duration.between(fechaLlegada, horaActual);
		int esperaEnMinutos = (int) duracion.toMinutes();

		this.tiempoTotalEspera += esperaEnMinutos;

	}

	public void actualizarTiempoPromedioEspera() {
		if (this.clientesAtendidos > 0)
			this.tiempoPromedioEspera = this.tiempoTotalEspera / this.clientesAtendidos;
	}

	public void actualizarCantidadClientesRegistrados(int cantidad) {
		this.cantTotalClientesRegistrados = cantidad;
	}

	public Map<String, Object> obtenerMetricas() {
		this.metricasMap.put("Clientes atendidos", this.clientesAtendidos);
		this.metricasMap.put("Clientes no atendidos", this.clientesNoAtendidos);
		this.metricasMap.put("Tiempo máximo de espera", this.tMaxEspera);
		this.metricasMap.put("Tiempo mínimo de espera", this.tMinEspera);
		this.metricasMap.put("Clientes en espera", this.clientesEnEspera);
		this.metricasMap.put("Tiempo promedio de espera", this.tiempoPromedioEspera); // decimales
		this.metricasMap.put("Tiempo total de espera", this.tiempoTotalEspera);
		this.metricasMap.put("Cantidad total de clientes", this.cantTotalClientesRegistrados);

		return metricasMap;
	}

	public void setMetricas(Map<String, Object> metricas) {
		System.out.println(metricas.get("Clientes atendidos"));
		System.out.println(metricas.get("Clientes no atendidos"));
		System.out.println(metricas.get("Tiempo máximo de espera"));
		System.out.println(metricas.get("Tiempo mínimo de espera"));
		System.out.println(metricas.get("Clientes en espera"));
		System.out.println(metricas.get("Tiempo promedio de espera"));
		System.out.println(metricas.get("Tiempo total de espera"));
		System.out.println(metricas.get("Cantidad total de clientes"));

		this.clientesAtendidos = (int) metricas.get("Clientes atendidos");
		this.clientesNoAtendidos = (int) metricas.get("Clientes no atendidos");
		this.tMaxEspera = (int) metricas.get("Tiempo máximo de espera");
		this.tMinEspera = (int) metricas.get("Tiempo mínimo de espera");
		this.clientesEnEspera = (int) metricas.get("Clientes en espera");
		this.tiempoPromedioEspera = (float) metricas.get("Tiempo promedio de espera"); // decimales
		this.tiempoTotalEspera = (float) metricas.get("Tiempo total de espera");
		this.cantTotalClientesRegistrados = (int) metricas.get("Cantidad total de clientes");
	}
}