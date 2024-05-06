package vista;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import monitoreo.Controlador_monitoreo;
 

public class Monitoreovista extends JFrame{
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
	    
	    public Monitoreovista(){
	    	  setTitle("Estadísticas del Sistema");
	          setSize(707, 451);
	          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	          JPanel panel = new JPanel();
	          panel.setLayout(null);

	          labelClientesAtendidos = new JLabel("Clientes Atendidos: ");
	          this.labelClientesAtendidos.setBounds(10, 52, 384, 29);
	          labelClientesAtendidos.setFont(new Font("Verdana", Font.PLAIN, 12));
	          panel.add(labelClientesAtendidos);

	          labelClientesNoAtendidos = new JLabel("Clientes No Atendidos: ");
	          this.labelClientesNoAtendidos.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelClientesNoAtendidos.setBounds(10, 92, 384, 29);
	          panel.add(labelClientesNoAtendidos);

	          labelTMaxEspera = new JLabel("Tiempo Máximo de Espera: ");
	          this.labelTMaxEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelTMaxEspera.setBounds(10, 132, 384, 29);
	          panel.add(labelTMaxEspera);

	          labelTMinEspera = new JLabel("Tiempo Mínimo de Espera: ");
	          this.labelTMinEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelTMinEspera.setBounds(10, 172, 384, 29);
	          panel.add(labelTMinEspera);

	          labelClientesEnEspera = new JLabel("Clientes en Espera: ");
	          this.labelClientesEnEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelClientesEnEspera.setBounds(10, 212, 384, 29);
	          panel.add(labelClientesEnEspera);

	          labelTiempoPromedioEspera = new JLabel("Tiempo Promedio de Espera: ");
	          this.labelTiempoPromedioEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelTiempoPromedioEspera.setBounds(10, 252, 384, 29);
	          panel.add(labelTiempoPromedioEspera);

	          labelTiempoTotalEspera = new JLabel("Tiempo Total de Espera: ");
	          this.labelTiempoTotalEspera.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelTiempoTotalEspera.setBounds(10, 292, 384, 29);
	          panel.add(labelTiempoTotalEspera);

	          labelCantClientes = new JLabel("Cantidad de Clientes: ");
	          this.labelCantClientes.setFont(new Font("Verdana", Font.PLAIN, 12));
	          this.labelCantClientes.setBounds(10, 332, 384, 29);
	          panel.add(labelCantClientes);

	          btnActualizar = new JButton("Actualizar");
	          this.btnActualizar.setBounds(10, 372, 671, 29);
	          btnActualizar.addActionListener(new ActionListener() {
	              @Override
	              public void actionPerformed(ActionEvent e) {
	                  // Llama al método actualizarMetricas() al presionar el botón "Actualizar"
	                  actualizarMetricas();
	              }
	          });
	          panel.add(btnActualizar);
	          
	          getContentPane().add(panel);
	          
	          this.lblNewLabel = new JLabel("Monitoreo General del Sistema");
	          this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
	          this.lblNewLabel.setBounds(10, 11, 671, 30);
	          panel.add(this.lblNewLabel);
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
