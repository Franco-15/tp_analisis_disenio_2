package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import monitoreo.Monitoreo;
import vistas.IVista;
import vistas.VistaMonitoreo;

public class Controlador implements ActionListener{
	
	private IVista VistaMonitoreo;
	private Monitoreo monitoreo;
	
	public Controlador() {}
	
	public void run() {
		this.VistaMonitoreo = new VistaMonitoreo();
		this.VistaMonitoreo.setActionListener(this);
		this.VistaMonitoreo.setVisibilidad(true);
		this.monitoreo =  new Monitoreo();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ACTUALIZAR")) {
			Map<String, Object> metricas = monitoreo.actualizarMetricas();
			this.VistaMonitoreo.actualizarMetricas(metricas);
		}
		
	}
}
