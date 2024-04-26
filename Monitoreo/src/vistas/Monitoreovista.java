package vistas;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import monitoreo.Controlador_monitoreo;
 

public class Monitoreovista extends JFrame{
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
	    
	    public Monitoreovista(){
	    	  setTitle("Estadísticas del Sistema");
	          setSize(400, 300);
	          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	          JPanel panel = new JPanel();
	          panel.setLayout(new GridLayout(9, 2));

	          labelClientesAtendidos = new JLabel("Clientes Atendidos: ");
	          labelClientesAtendidos.setFont(new Font("Vazirmatn", Font.BOLD | Font.ITALIC, 12));
	          panel.add(labelClientesAtendidos);

	          labelClientesNoAtendidos = new JLabel("Clientes No Atendidos: ");
	          panel.add(labelClientesNoAtendidos);

	          labelTMaxEspera = new JLabel("Tiempo Máximo de Espera: ");
	          panel.add(labelTMaxEspera);

	          labelTMinEspera = new JLabel("Tiempo Mínimo de Espera: ");
	          panel.add(labelTMinEspera);

	          labelClientesEnEspera = new JLabel("Clientes en Espera: ");
	          panel.add(labelClientesEnEspera);

	          labelTiempoPromedioEspera = new JLabel("Tiempo Promedio de Espera: ");
	          panel.add(labelTiempoPromedioEspera);

	          labelTiempoTotalEspera = new JLabel("Tiempo Total de Espera: ");
	          panel.add(labelTiempoTotalEspera);

	          labelCantClientes = new JLabel("Cantidad de Clientes: ");
	          panel.add(labelCantClientes);

	          btnActualizar = new JButton("Actualizar");
	          btnActualizar.addActionListener(new ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  // Llama al método actualizarMetricas() al presionar el botón "Actualizar"
	                  actualizarMetricas();
	              }
	          });
	          panel.add(btnActualizar);
	          
	          getContentPane().add(panel);
	          setVisible(true);
	          controlador = new Controlador_monitoreo(3,"localhost");
	    }
	 // Método para actualizar las métricas en la ventana
	    private void actualizarMetricas() {
	    	
	    	

	        // Obtener el mapa de métricas
	       // Map<String, Object> metricasMap = metricas.obtenerMetricas();
	    	Map<String, Object> metricasMap = controlador.actualizarMetricas();
	        
	        labelClientesAtendidos.setText("Clientes Atendidos: " + metricasMap.get("Clientes atendidos"));
	        labelClientesNoAtendidos.setText("Clientes No Atendidos: " + metricasMap.get("Clientes no atendidos"));
	        labelTMaxEspera.setText("Tiempo Máximo de Espera: " + metricasMap.get("Tiempo máximo de espera"));
	        labelTMinEspera.setText("Tiempo Mínimo de Espera: " + metricasMap.get("Tiempo mínimo de espera"));
	        labelClientesEnEspera.setText("Clientes en Espera: " + metricasMap.get("Clientes en espera"));
	        labelTiempoPromedioEspera.setText("Tiempo Promedio de Espera: " + metricasMap.get("Tiempo promedio de espera"));
	        labelTiempoTotalEspera.setText("Tiempo Total de Espera: " + metricasMap.get("Tiempo total de espera"));
	        labelCantClientes.setText("Cantidad de Clientes: " + metricasMap.get("Cantidad total de clientes"));
	    }
}
