package vistas;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Vista_notificacion extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal; 
    public Vista_notificacion() throws HeadlessException {
    	    	
        // Configuración básica de la ventana
        setTitle("Notificación");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel principal
        panelPrincipal = new JPanel(new GridLayout(0, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir espacio alrededor del panel

        JLabel lblNewLabel_2 = new JLabel("DNI");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblNewLabel_2);

        JLabel lblNewLabel = new JLabel("BOX");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblNewLabel);

      

        // Agregar panel principal a la ventana
        getContentPane().add(panelPrincipal, BorderLayout.NORTH);

        // Centrar ventana en la pantalla
        setLocationRelativeTo(null);

        // Mostrar la ventana
        setVisible(true);
    }

    public void actualizar_pantalla(ArrayList<String[]> nuevosDatos) {
        // Limpiar el panel antes de agregar los nuevos datos
    	 // Limpiar el panel antes de agregar los nuevos datos
        panelPrincipal.removeAll();

        // Volver a agregar las etiquetas DNI y BOX
        JLabel lblNewLabel_2 = new JLabel("DNI");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblNewLabel_2);

        JLabel lblNewLabel = new JLabel("BOX");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblNewLabel);    	
        // Agregar los nuevos datos al panel
        for (String[] dato : nuevosDatos) {
            JLabel dniLabel = new JLabel(dato[0]);
            dniLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panelPrincipal.add(dniLabel);

            JLabel boxLabel = new JLabel(dato[1]);
            boxLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panelPrincipal.add(boxLabel);
        }

        // Actualizar la ventana
        revalidate();
        repaint();
    }


}
