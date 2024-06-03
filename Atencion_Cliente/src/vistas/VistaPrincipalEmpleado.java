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

import comunes.Cliente;

public class VistaPrincipalEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lbl_numero_box;
	private JButton btnLlamarCliente;
	private JList<String> list_clientes_atendidos;
	private JScrollPane scrollPane;
	private String numeroBox;
	private DefaultListModel<String> modeloListaClientesAtendidos;
	private DefaultListModel<String> modeloListaProximoCliente;
	private DefaultListModel<String> modeloListaClientesNoAtendidos;
	private JButton btnAtender;
	private JButton btnNoRespondio;
	private JScrollPane scrollPane_1;
	private JList<String> list_proximo_cliente;
	private JScrollPane scrollPane_2;
	private JList<String> list_clientes_no_atendidos;

	public VistaPrincipalEmpleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 430);
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
		this.btnLlamarCliente.setBounds(10, 337, 220, 43);
		this.contentPane.add(this.btnLlamarCliente);

		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Clientes Atendidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.scrollPane.setBounds(10, 55, 220, 243);
		this.contentPane.add(this.scrollPane);

		this.list_clientes_atendidos = new JList<String>();
		this.scrollPane.setViewportView(this.list_clientes_atendidos);
		this.list_clientes_atendidos.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.modeloListaClientesAtendidos = new DefaultListModel<String>();
		this.list_clientes_atendidos.setModel(modeloListaClientesAtendidos);
		
		this.btnAtender = new JButton("Atender");
		this.btnAtender.setForeground(new Color(0, 100, 0));
		this.btnAtender.setFont(new Font("Verdana", Font.PLAIN, 13));
		this.btnAtender.setActionCommand("ATENDER");
		this.btnAtender.setBounds(414, 337, 93, 43);
		this.contentPane.add(this.btnAtender);
		
		this.btnNoRespondio = new JButton("No respondió al llamado");
		this.btnNoRespondio.setForeground(new Color(255, 0, 0));
		this.btnNoRespondio.setFont(new Font("Verdana", Font.PLAIN, 13));
		this.btnNoRespondio.setActionCommand("NO RESPONDIO");
		this.btnNoRespondio.setBounds(517, 337, 195, 43);
		this.contentPane.add(this.btnNoRespondio);
		
		this.scrollPane_1 = new JScrollPane();
		this.scrollPane_1.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Pr\u00F3ximo Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.scrollPane_1.setBounds(251, 55, 220, 243);
		this.contentPane.add(this.scrollPane_1);
		
		this.list_proximo_cliente = new JList<String>();
		this.list_proximo_cliente.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.scrollPane_1.setViewportView(this.list_proximo_cliente);
		this.modeloListaProximoCliente = new DefaultListModel<String>();
		this.list_proximo_cliente.setModel(modeloListaProximoCliente);
		
		this.scrollPane_2 = new JScrollPane();
		this.scrollPane_2.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes No Atendidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.scrollPane_2.setBounds(492, 55, 220, 243);
		this.contentPane.add(this.scrollPane_2);
		
		this.list_clientes_no_atendidos = new JList<String>();
		this.list_clientes_no_atendidos.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.scrollPane_2.setViewportView(this.list_clientes_no_atendidos);
		this.modeloListaClientesNoAtendidos = new DefaultListModel<String>();
		this.list_clientes_no_atendidos.setModel(this.modeloListaClientesNoAtendidos);
		this.btnAtender.setEnabled(false);
		this.btnNoRespondio.setEnabled(false);
	}

	public void setNumeroBox(String numeroBox) {
		this.numeroBox = numeroBox;
		this.lbl_numero_box.setText(numeroBox);
	}

	public String getNumeroBox() {
		return this.numeroBox;
	}

	public void agregarProximoCliente(Cliente cliente) {
		System.out.println("Proximo Cliente: " + cliente.getDni());
		this.modeloListaProximoCliente.addElement(cliente.getDni());
		this.btnLlamarCliente.setEnabled(false);
		this.btnAtender.setEnabled(true);
		this.btnNoRespondio.setEnabled(true);
	}
	
	public void agregarClienteAtendido(Cliente cliente) {
		this.modeloListaProximoCliente.clear();
		this.modeloListaClientesAtendidos.addElement(cliente.getDni());
		this.btnLlamarCliente.setEnabled(true);
		this.btnAtender.setEnabled(false);
		this.btnNoRespondio.setEnabled(false);
	}
	
	public void agregarClienteNoAtendido(Cliente cliente) {
		this.modeloListaProximoCliente.clear();
		this.modeloListaClientesNoAtendidos.addElement(cliente.getDni());
		this.btnLlamarCliente.setEnabled(true);
		this.btnAtender.setEnabled(false);
		this.btnNoRespondio.setEnabled(false);
	}
	
	public void setActionListener(ActionListener actionListener) {
		this.btnLlamarCliente.addActionListener(actionListener);
		this.btnAtender.addActionListener(actionListener);
		this.btnNoRespondio.addActionListener(actionListener);
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
