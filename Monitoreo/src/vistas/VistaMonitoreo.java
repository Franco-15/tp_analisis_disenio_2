package vistas;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class VistaMonitoreo extends JFrame implements IVista{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private JLabel labelClientesAtendidos;
	private JLabel labelClientesNoAtendidos;
	private JLabel labelTMaxEspera;
	private JLabel labelTMinEspera;
	private JLabel labelClientesEnEspera;
	private JLabel labelTiempoPromedioEspera;
	private JLabel labelTiempoTotalEspera;
	private JLabel labelCantClientes;
	private JButton btnActualizar;
	private JLabel lblNewLabel;

	public VistaMonitoreo() {
		setResizable(false);
		setTitle("Estadísticas del Sistema");
		setSize(495, 441);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 479, 402);
		panel.setLayout(null);

		labelClientesAtendidos = new JLabel("Clientes Atendidos: ");
		this.labelClientesAtendidos.setBounds(10, 66, 459, 25);
		labelClientesAtendidos.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(labelClientesAtendidos);

		labelClientesNoAtendidos = new JLabel("Clientes No Atendidos: ");
		this.labelClientesNoAtendidos.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelClientesNoAtendidos.setBounds(10, 102, 459, 25);
		panel.add(labelClientesNoAtendidos);

		labelTMaxEspera = new JLabel("Tiempo Máximo de Espera: ");
		this.labelTMaxEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelTMaxEspera.setBounds(10, 138, 459, 25);
		panel.add(labelTMaxEspera);

		labelTMinEspera = new JLabel("Tiempo Mínimo de Espera: ");
		this.labelTMinEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelTMinEspera.setBounds(10, 174, 459, 25);
		panel.add(labelTMinEspera);

		labelClientesEnEspera = new JLabel("Clientes en Espera: ");
		this.labelClientesEnEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelClientesEnEspera.setBounds(10, 210, 459, 25);
		panel.add(labelClientesEnEspera);

		labelTiempoPromedioEspera = new JLabel("Tiempo Promedio de Espera: ");
		this.labelTiempoPromedioEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelTiempoPromedioEspera.setBounds(10, 246, 459, 25);
		panel.add(labelTiempoPromedioEspera);

		labelTiempoTotalEspera = new JLabel("Tiempo Total de Espera: ");
		this.labelTiempoTotalEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelTiempoTotalEspera.setBounds(10, 282, 459, 25);
		panel.add(labelTiempoTotalEspera);

		labelCantClientes = new JLabel("Cantidad de Clientes: ");
		this.labelCantClientes.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.labelCantClientes.setBounds(10, 318, 459, 25);
		panel.add(labelCantClientes);

		btnActualizar = new JButton("Actualizar");
		this.btnActualizar.setActionCommand("ACTUALIZAR");
		this.btnActualizar.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.btnActualizar.setBounds(10, 354, 459, 37);
		getContentPane().setLayout(null);
		panel.add(btnActualizar);

		getContentPane().add(panel);
		
		this.lblNewLabel = new JLabel("Métricas globales del sistema");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		this.lblNewLabel.setBounds(10, 11, 459, 37);
		panel.add(this.lblNewLabel);
	}
	
	// Método para actualizar las métricas en la ventana
	public void actualizarMetricas(Map<String, Object> metricas) {

		if (metricas != null) {
			labelClientesAtendidos.setText("Clientes Atendidos: " + metricas.get("Clientes atendidos"));
			labelClientesNoAtendidos.setText("Clientes No Atendidos: " + metricas.get("Clientes no atendidos"));
			labelTMaxEspera.setText("Tiempo Máximo de Espera: " + metricas.get("Tiempo máximo de espera") + " m");
			labelTMinEspera.setText("Tiempo Mínimo de Espera: " + metricas.get("Tiempo mínimo de espera") + " m");
			labelClientesEnEspera.setText("Clientes en Espera: " + metricas.get("Clientes en espera"));
			labelTiempoPromedioEspera.setText("Tiempo Promedio de Espera: "
					+ String.format("%.2f ", metricas.get("Tiempo promedio de espera")) + " m");
			labelTiempoTotalEspera
					.setText("Tiempo Total de Espera: " + metricas.get("Tiempo total de espera") + " m");
			labelCantClientes.setText("Cantidad de Clientes: " + metricas.get("Cantidad total de clientes"));
		} else
			JOptionPane.showMessageDialog(this, "Error de Sistema. No pudieron obtenerse las métricas",
					"Metricas del sistema", JOptionPane.ERROR_MESSAGE);
	}

	public void setActionListener(ActionListener al) {
		this.btnActualizar.addActionListener(al);
	}

	@Override
	public void setVisibilidad(boolean visible) {
		this.setVisible(visible);
	}
}
