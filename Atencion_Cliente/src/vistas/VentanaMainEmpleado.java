package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import comunes.MensajeAtencionCliente;


public class VentanaMainEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JLabel lbl_numero_box;
    private JButton btnLlamarCliente;
    private JList<String> list_clientes_atendidos;
    private JList<String> list_clientes_en_camino;

    private JScrollPane scrollPane1; // JScrollPane para la lista de clientes atendidos
    private JScrollPane scrollPane2; // JScrollPane para la lista de clientes en camino
    private String numeroBox;
    private DefaultListModel<String> modeloLista;
    private JButton btnaceptar;
    private JButton btnnollego;
    private DefaultListModel<String> modeloLista_en_camino;


	public VentanaMainEmpleado() {
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 626, 409);
	        contentPane = new JPanel();
	        contentPane.setToolTipText("");
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);

	        lblNewLabel = new JLabel("Número de Box: ");
	        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 15));
	        lblNewLabel.setBounds(10, 11, 154, 33);
	        contentPane.add(lblNewLabel);

	        lbl_numero_box = new JLabel("12");
	        lbl_numero_box.setFont(new Font("Verdana", Font.PLAIN, 15));
	        lbl_numero_box.setBounds(168, 20, 446, 14);
	        contentPane.add(lbl_numero_box);

	        btnLlamarCliente = new JButton("Llamar al próximo cliente");
	        btnLlamarCliente.setActionCommand("LLAMAR CLIENTE");
	        btnLlamarCliente.setFont(new Font("Verdana", Font.PLAIN, 13));
	        btnLlamarCliente.setBounds(12, 311, 241, 43);
	        contentPane.add(btnLlamarCliente);

	        scrollPane1 = new JScrollPane();
	        scrollPane1.setToolTipText("");
	        scrollPane1.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes Atendidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	        scrollPane1.setBounds(10, 46, 298, 243);
	        contentPane.add(scrollPane1);

	        list_clientes_atendidos = new JList<String>();
	        list_clientes_atendidos.setFont(new Font("Verdana", Font.PLAIN, 15));
	        scrollPane1.setViewportView(list_clientes_atendidos);
	        modeloLista = new DefaultListModel<String>();
	        list_clientes_atendidos.setModel(modeloLista);

	        scrollPane2 = new JScrollPane();
	        scrollPane2.setToolTipText("");
	        scrollPane2.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes en camino", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	        scrollPane2.setBounds(303, 46, 298, 243);
	        contentPane.add(scrollPane2);

	        list_clientes_en_camino = new JList<String>();
	        list_clientes_en_camino.setFont(new Font("Dialog", Font.PLAIN, 15));
	        scrollPane2.setViewportView(list_clientes_en_camino);
	        modeloLista_en_camino = new DefaultListModel<String>();

	        btnaceptar = new JButton("Aceptado");
	        btnaceptar.setFont(new Font("Dialog", Font.PLAIN, 13));
	        btnaceptar.setActionCommand("aceptar");
	        btnaceptar.setBounds(265, 311, 178, 43);
	        contentPane.add(btnaceptar);

	        btnnollego = new JButton("No llego cliente");
	        btnnollego.setFont(new Font("Dialog", Font.PLAIN, 13));
	        btnnollego.setActionCommand("no llego");
	        btnnollego.setBounds(455, 311, 146, 43);
	        contentPane.add(btnnollego);
	    
	}
	
	public String get_cliente() {
	    if (!this.modeloLista_en_camino.isEmpty()) {
	        return this.modeloLista_en_camino.get(0);
	    } else {
	        return ""; // O puedes devolver cualquier otro valor predeterminado en lugar de una cadena vacía
	    }
	}


	public void setNumeroBox(String numeroBox) {
		this.numeroBox = numeroBox;
		this.lbl_numero_box.setText(numeroBox);
	}

	public String getNumeroBox() {
		return this.numeroBox;
	}

	public void agregarCliente(String dni) {
		this.modeloLista_en_camino.addElement(dni);
		this.list_clientes_en_camino.setModel(modeloLista_en_camino);
	  
	}
	
	public void aceptar_cliente() {
		
		if(this.modeloLista_en_camino.size() > 0) {
			// Obtener el cliente como un String de la lista original
			String cliente = this.modeloLista_en_camino.get(0);
	
			// Eliminar el cliente de la lista original
			this.modeloLista_en_camino.remove(0);
	
			// Agregar el cliente a la nueva lista
			this.modeloLista.addElement(cliente);
		}
	}
	
	public void cliente_no_llego() {
		// Eliminar el cliente de la lista original
			if(this.modeloLista_en_camino.size() > 0)
				this.modeloLista_en_camino.remove(0);
	}
	

	public void setActionListener(ActionListener actionListener) {
		this.btnLlamarCliente.addActionListener(actionListener);
		this.btnaceptar.addActionListener(actionListener);
		this.btnnollego.addActionListener(actionListener);
	}
	
	public void errorServidor() {
		JOptionPane.showMessageDialog(this, "Error con el servidor. Intente nuevamente", "Error de servidor",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public void noHayClienteParaAtender() {
		JOptionPane.showMessageDialog(this, "No hay clientes para atender", "Atencion Clientes",
				JOptionPane.WARNING_MESSAGE);
	}
}

