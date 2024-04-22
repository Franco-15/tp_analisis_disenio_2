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

public class VentanaMainEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lbl_numero_box;
	private JButton btnLlamarCliente;
	private JList<String> list_clientes_atendidos;
	private JScrollPane scrollPane;
	private String numeroBox;
	private DefaultListModel<String> modeloLista;

	public VentanaMainEmpleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 409);
		this.contentPane = new JPanel();
		this.contentPane.setToolTipText("");
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.lblNewLabel = new JLabel("Número de Box: ");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		this.lblNewLabel.setBounds(10, 11, 154, 33);
		this.contentPane.add(this.lblNewLabel);

		this.lbl_numero_box = new JLabel("12");
		this.lbl_numero_box.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.lbl_numero_box.setBounds(154, 20, 446, 14);
		this.contentPane.add(this.lbl_numero_box);

		this.btnLlamarCliente = new JButton("Llamar al próximo cliente");
		this.btnLlamarCliente.setActionCommand("LLAMAR CLIENTE");
		this.btnLlamarCliente.setFont(new Font("Verdana", Font.PLAIN, 13));
		this.btnLlamarCliente.setBounds(349, 316, 241, 43);
		this.contentPane.add(this.btnLlamarCliente);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Clientes Atendidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.scrollPane.setBounds(10, 55, 580, 243);
		this.contentPane.add(this.scrollPane);

		this.list_clientes_atendidos = new JList<String>();
		this.scrollPane.setViewportView(this.list_clientes_atendidos);
		this.list_clientes_atendidos.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.modeloLista = new DefaultListModel<String>();
		this.list_clientes_atendidos.setModel(modeloLista);
	}

	public void setNumeroBox(String numeroBox) {
		this.numeroBox = numeroBox;
		this.lbl_numero_box.setText(numeroBox);
	}

	public String getNumeroBox() {
		return this.numeroBox;
	}

	public void agregarCliente(String cliente) {
		this.modeloLista.addElement(cliente);
	}

	public void setActionListener(ActionListener actionListener) {
		this.btnLlamarCliente.addActionListener(actionListener);
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
