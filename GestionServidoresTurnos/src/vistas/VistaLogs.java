package vistas;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class VistaLogs extends JFrame {
	private static VistaLogs instance = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JList<String> list;
	private DefaultListModel<String> modeloLista;

	/**
	 * Create the frame.
	 */
	private VistaLogs() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		this.lblNewLabel = new JLabel("Logs monitor de servidores de gestión de turnos");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		this.lblNewLabel.setBounds(10, 11, 414, 30);
		this.contentPane.add(this.lblNewLabel);
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(10, 47, 414, 203);
		this.contentPane.add(this.scrollPane);
		
		this.list = new JList<String>();
		this.modeloLista = new DefaultListModel<String>();
		this.list.setModel(modeloLista);
		this.scrollPane.setViewportView(this.list);
	}
	
	public static VistaLogs getInstance() {
		if(instance ==  null)
			instance = new VistaLogs();
		
		return instance;
	}

	public void agregarElemento(String elemento) {
		this.modeloLista.addElement(elemento);
		if(modeloLista.getSize()>50) {
			modeloLista.remove(0);
		}
	}
}