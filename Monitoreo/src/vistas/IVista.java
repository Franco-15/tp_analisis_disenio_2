package vistas;

import java.awt.event.ActionListener;
import java.util.Map;

public interface IVista {
	public void setActionListener(ActionListener al);
	public void actualizarMetricas(Map<String, Object> metricas );
	public void setVisibilidad(boolean visible);
}
