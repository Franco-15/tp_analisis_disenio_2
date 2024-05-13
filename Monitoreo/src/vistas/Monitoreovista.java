package vistas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import monitoreo.Controlador_monitoreo;

public class Monitoreovista extends JFrame {
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
	private Controlador_monitoreo controlador;
	private JLabel lblNewLabel;

	public Monitoreovista() {
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
		this.btnActualizar.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.btnActualizar.setBounds(10, 354, 459, 37);
		btnActualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama al método actualizarMetricas() al presionar el botón "Actualizar"
				actualizarMetricas();
			}
		});
		getContentPane().setLayout(null);
		panel.add(btnActualizar);

		getContentPane().add(panel);
		
		this.lblNewLabel = new JLabel("Métricas globales del sistema");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		this.lblNewLabel.setBounds(10, 11, 459, 37);
		panel.add(this.lblNewLabel);
		setVisible(true);
		controlador = new Controlador_monitoreo(10, "localhost");
	}

	// Método para actualizar las métricas en la ventana
	private void actualizarMetricas() {

		// Obtener el mapa de métricas
		// Map<String, Object> metricasMap = metricas.obtenerMetricas();
		Map<String, Object> metricasMap = controlador.actualizarMetricas();

		if (metricasMap != null) {
			labelClientesAtendidos.setText("Clientes Atendidos: " + metricasMap.get("Clientes atendidos"));
			labelClientesNoAtendidos.setText("Clientes No Atendidos: " + metricasMap.get("Clientes no atendidos"));
			labelTMaxEspera.setText("Tiempo Máximo de Espera: " + metricasMap.get("Tiempo máximo de espera") + " m");
			labelTMinEspera.setText("Tiempo Mínimo de Espera: " + metricasMap.get("Tiempo mínimo de espera") + " m");
			labelClientesEnEspera.setText("Clientes en Espera: " + metricasMap.get("Clientes en espera"));
			labelTiempoPromedioEspera.setText("Tiempo Promedio de Espera: "
					+ String.format("%.2f ", metricasMap.get("Tiempo promedio de espera")) + " m");
			labelTiempoTotalEspera
					.setText("Tiempo Total de Espera: " + metricasMap.get("Tiempo total de espera") + " m");
			labelCantClientes.setText("Cantidad de Clientes: " + metricasMap.get("Cantidad total de clientes"));
		} else
			JOptionPane.showMessageDialog(this, "Error de Sistema. No pudieron obtenerse las métricas",
					"Metricas del sistema", JOptionPane.ERROR_MESSAGE);
	}
}
