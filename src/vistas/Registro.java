package vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.CardLayout;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelTexto;
	private JPanel panelFormulario;
	private JPanel panelBtnRegistrar;
	private JPanel panel_Bienvenido;
	private JPanel panel_6;
	private JPanel panelBtn;
	private JLabel lblBienvenido;
	private JPanel panel_tipo_documento;
	private JPanel panel_lbl_tipo_documento;
	private JPanel panel_input_tipo_documento;
	private JTextField textField_tipo_documento;
	private JLabel lblNewLabel;
	private JButton btnRegistrar;
	private JPanel panel_1;
	private JPanel panel_I‌ndicaciones;
	private JLabel lblNewLabel_1;
	private JPanel panel_4;
	private JPanel panel_numero_documento;
	private JPanel panel_lbl_numero_documento;
	private JLabel lbl_numero_dni;
	private JPanel panel_input_numero_documento;
	private JTextField textField_numero_documento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 517);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(new GridLayout(3, 1, 5, 5));
		
		this.panelTexto = new JPanel();
		this.contentPane.add(this.panelTexto);
		this.panelTexto.setLayout(new GridLayout(4, 1, 0, 0));
		
		this.panel_Bienvenido = new JPanel();
		this.panelTexto.add(this.panel_Bienvenido);
		
		this.lblBienvenido = new JLabel("Bienvenido");
		this.lblBienvenido.setFont(new Font("Verdana", Font.BOLD, 25));
		this.panel_Bienvenido.add(this.lblBienvenido);
		
		this.panel_1 = new JPanel();
		this.panelTexto.add(this.panel_1);
		
		this.panel_I‌ndicaciones = new JPanel();
		this.panelTexto.add(this.panel_I‌ndicaciones);
		this.panel_I‌ndicaciones.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.lblNewLabel_1 = new JLabel("Registrese con su DNI para ser atendido");
		this.lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		this.lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		this.panel_I‌ndicaciones.add(this.lblNewLabel_1);
		
		this.panelFormulario = new JPanel();
		this.contentPane.add(this.panelFormulario);
		this.panelFormulario.setLayout(new GridLayout(3, 1, 0, 0));
		
		this.panel_tipo_documento = new JPanel();
		this.panelFormulario.add(this.panel_tipo_documento);
		this.panel_tipo_documento.setLayout(new GridLayout(1, 2, 0, 0));
		
		this.panel_lbl_tipo_documento = new JPanel();
		this.panel_tipo_documento.add(this.panel_lbl_tipo_documento);
		this.panel_lbl_tipo_documento.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.lblNewLabel = new JLabel("Tipo documento: ");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 13));
		this.lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.panel_lbl_tipo_documento.add(this.lblNewLabel);
		
		this.panel_input_tipo_documento = new JPanel();
		this.panel_tipo_documento.add(this.panel_input_tipo_documento);
		this.panel_input_tipo_documento.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.textField_tipo_documento = new JTextField();
		this.panel_input_tipo_documento.add(this.textField_tipo_documento);
		this.textField_tipo_documento.setColumns(10);
		
		this.panel_4 = new JPanel();
		this.panelFormulario.add(this.panel_4);
		
		this.panel_numero_documento = new JPanel();
		this.panelFormulario.add(this.panel_numero_documento);
		this.panel_numero_documento.setLayout(new GridLayout(1, 2, 0, 0));
		
		this.panel_lbl_numero_documento = new JPanel();
		this.panel_numero_documento.add(this.panel_lbl_numero_documento);
		this.panel_lbl_numero_documento.setLayout(new GridLayout(1, 1, 0, 0));
		
		this.lbl_numero_dni = new JLabel("Número Documento:");
		this.lbl_numero_dni.setHorizontalAlignment(SwingConstants.CENTER);
		this.lbl_numero_dni.setFont(new Font("Verdana", Font.BOLD, 13));
		this.panel_lbl_numero_documento.add(this.lbl_numero_dni);
		
		this.panel_input_numero_documento = new JPanel();
		this.panel_numero_documento.add(this.panel_input_numero_documento);
		this.panel_input_numero_documento.setLayout(new GridLayout(1, 1, 0, 0));
		
		this.textField_numero_documento = new JTextField();
		this.textField_numero_documento.setColumns(10);
		this.panel_input_numero_documento.add(this.textField_numero_documento);
		
		this.panelBtnRegistrar = new JPanel();
		this.contentPane.add(this.panelBtnRegistrar);
		this.panelBtnRegistrar.setLayout(new GridLayout(1, 2, 0, 0));
		
		this.panel_6 = new JPanel();
		this.panelBtnRegistrar.add(this.panel_6);
		this.panel_6.setLayout(new GridLayout(1, 0, 0, 0));
		
		this.panelBtn = new JPanel();
		this.panelBtnRegistrar.add(this.panelBtn);
		this.panelBtn.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.btnRegistrar = new JButton("Registrarse");
		this.panelBtn.add(this.btnRegistrar);
	}

}
