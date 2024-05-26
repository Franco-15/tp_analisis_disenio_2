package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import comunes.TElementoNotificacion;

public class VistaNotificacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JPanel panelBox;
	private JPanel panelDni;
	private JSeparator separator;
	private JLabel lblDNI;
	private JLabel lblBOX;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JList<String> listDNI;
	private JList<Integer> listBOX;
	private DefaultListModel<String> modelListDNI;
	private DefaultListModel<Integer> modelListBOX;

	public VistaNotificacion() throws HeadlessException {

		// Configuración básica de la ventana
		setTitle("Notificación");
		setSize(708, 326);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Crear panel principal
		panelPrincipal = new JPanel();
		this.panelPrincipal.setBounds(0, 0, 692, 287);
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Agregar panel principal a la ventana
		getContentPane().add(panelPrincipal);
		this.panelPrincipal.setLayout(null);

		this.panelBox = new JPanel();
		this.panelBox.setBounds(368, 0, 314, 287);
		this.panelPrincipal.add(this.panelBox);
		this.panelBox.setLayout(null);

		this.lblBOX = new JLabel("BOX");
		this.lblBOX.setBounds(131, 5, 47, 26);
		this.lblBOX.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblBOX.setFont(new Font("Verdana", Font.BOLD, 20));
		this.panelBox.add(this.lblBOX);

		this.separator_2 = new JSeparator();
		this.separator_2.setBounds(183, 17, 0, 2);
		this.panelBox.add(this.separator_2);

		this.separator_3 = new JSeparator();
		this.separator_3.setBackground(Color.BLACK);
		this.separator_3.setBounds(0, 42, 314, 17);
		this.panelBox.add(this.separator_3);

		this.listBOX = new JList<Integer>();
		this.listBOX.setFont(new Font("Verdana", Font.PLAIN, 17));
		this.listBOX.setBounds(10, 54, 294, 222);
		this.panelBox.add(this.listBOX);

		this.panelDni = new JPanel();
		this.panelDni.setBounds(10, 0, 335, 287);
		this.panelPrincipal.add(this.panelDni);
		this.panelDni.setLayout(null);

		this.lblDNI = new JLabel("DNI");
		this.lblDNI.setFont(new Font("Verdana", Font.BOLD, 20));
		this.lblDNI.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblDNI.setBounds(10, 11, 315, 21);
		this.panelDni.add(this.lblDNI);

		this.separator_1 = new JSeparator();
		this.separator_1.setBackground(Color.BLACK);
		this.separator_1.setBounds(0, 43, 335, 8);
		this.panelDni.add(this.separator_1);

		this.listDNI = new JList<String>();
		this.listDNI.setFont(new Font("Verdana", Font.PLAIN, 17));
		this.listDNI.setBounds(10, 54, 315, 222);
		this.panelDni.add(this.listDNI);

		this.separator = new JSeparator();
		this.separator.setOrientation(SwingConstants.VERTICAL);
		this.separator.setBackground(Color.BLACK);
		this.separator.setBounds(355, 0, 11, 287);
		this.panelPrincipal.add(this.separator);
		this.modelListBOX = new DefaultListModel<Integer>();
		this.listBOX.setModel(modelListBOX);
		this.modelListDNI = new DefaultListModel<String>();
		this.listDNI.setModel(modelListDNI);
		// Centrar ventana en la pantalla
		setLocationRelativeTo(null);
		// Mostrar la ventana
		setVisible(true);
		
		listDNI.setCellRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == 0) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                } else {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                
                return label;
            }
        });
		
		listBOX.setCellRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == 0) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                } else {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                
                return label;
            }
        });
	}

	public void actualizarPantalla(TElementoNotificacion elemento) {
		if(modelListDNI.size() >= 5) {
			modelListBOX.removeElement(modelListBOX.lastElement());
			modelListDNI.removeElement(modelListDNI.lastElement());
		}
    	modelListDNI.add(0,elemento.getDni());
        modelListBOX.add(0,elemento.getBox());
    }
}
