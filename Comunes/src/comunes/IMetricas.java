package comunes;

import java.time.LocalDateTime;
import java.util.Map;

public interface IMetricas {
	public void actualizarClientesAtendidos(int cantidad);
	public void actualizarClientesNoAtendidos(int cantidad);
	public void actualizarTMaxEspera(LocalDateTime fechaLlegada);
	public void actualizarTMinEspera(LocalDateTime fechaLlegada);
	public void actualizarClienteEnEspera(int cantidad);
	public void	actualizarTiempoTotalEspera(LocalDateTime fechaLlegada);
	public void actualizarTiempoPromedioEspera();
	public void actualizarCantidadClientesRegistrados(int cantidad);
	public Map<String, Object> obtenerMetricas();
	public void setMetricas(Map<String, Object> metricas);
}
