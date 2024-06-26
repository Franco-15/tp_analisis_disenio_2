package vistas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import registro.RegistroControlador;
import registro.Response;

public class RegistroVistaMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblRegistreseConSu;
	private JLabel lblNewLabel_2;
	private JComboBox<String> comboBox_tipo_documento;
	private JLabel lblNewLabel_3;
	private JTextField textField_numero_dni;
	private JButton btn_registrarse;
	private ConfirmationRegistro dialog;
	private RegistroControlador controlador = new RegistroControlador();
	
	/**
	 * Create the frame.
	 */
	public RegistroVistaMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);
		
		this.lblNewLabel = new JLabel("Bienvenido");
		this.lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 25));
		this.lblNewLabel.setBounds(10, 11, 664, 41);
		this.contentPane.add(this.lblNewLabel);
		
		this.lblRegistreseConSu = new JLabel("Registrese con su DNI para ser atendido");
		this.lblRegistreseConSu.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblRegistreseConSu.setFont(new Font("Verdana", Font.PLAIN, 17));
		this.lblRegistreseConSu.setBounds(10, 84, 664, 41);
		this.contentPane.add(this.lblRegistreseConSu);
		
		this.lblNewLabel_2 = new JLabel("Tipo de documento:");
		this.lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel_2.setBounds(10, 153, 164, 34);
		this.contentPane.add(this.lblNewLabel_2);
		
		this.comboBox_tipo_documento = new JComboBox<String>();
		this.comboBox_tipo_documento.setModel(new DefaultComboBoxModel<String>(new String[] {"DNI"}));
		this.comboBox_tipo_documento.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.comboBox_tipo_documento.setBounds(184, 155, 490, 34);
		this.contentPane.add(this.comboBox_tipo_documento);
		
		this.lblNewLabel_3 = new JLabel("Número documento:");
		this.lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel_3.setBounds(20, 212, 157, 34);
		this.contentPane.add(this.lblNewLabel_3);
		
		this.textField_numero_dni = new JTextField();
		this.textField_numero_dni.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.textField_numero_dni.setBounds(184, 219, 490, 29);
		this.contentPane.add(this.textField_numero_dni);
		this.textField_numero_dni.setColumns(10);
		
		this.btn_registrarse = new JButton("Registrarse");
		this.btn_registrarse.setActionCommand("REGISTRARSE");
		this.btn_registrarse.addActionListener(this);
		this.btn_registrarse.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.btn_registrarse.setBounds(523, 296, 139, 41);
		this.contentPane.add(this.btn_registrarse);
	}
	public void actionPerformed(ActionEvent e) {
		String numeroDocumento = this.textField_numero_dni.getText();
		
       if(e.getActionCommand().equals("CONFIRMAR")) {
			this.dialog.dispose();
        	String resultado = this.controlador.registrar(numeroDocumento);
    		
        	if(resultado.equals("0"))
                JOptionPane.showMessageDialog(this, Response.RegistroExistoso, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        	else if(resultado.equals("1"))
                JOptionPane.showMessageDialog(this, Response.DocumentoYaRegistro, "Operacion Inválida", JOptionPane.WARNING_MESSAGE);
        	else if(resultado.equals("2"))
                JOptionPane.showMessageDialog(this, Response.ErrorDeSistema, "Error de Sistema", JOptionPane.ERROR_MESSAGE);

		}

		else if(e.getActionCommand().equals("CANCELAR")) {
			this.dialog.dispose();
		}
		else if(e.getActionCommand().equals("REGISTRARSE")) {		
			if (numeroDocumento.matches("[0-9]+")) {
				this.dialog = new ConfirmationRegistro(this, numeroDocumento);
		        this.dialog.setActionListener(this);
		        this.dialog.setVisible(true);
			} else {
                JOptionPane.showMessageDialog(this, "El documento ingresado no es válido.", "Documento inválido", JOptionPane.ERROR_MESSAGE);
            }
			this.textField_numero_dni.setText("");
		}
	}
}
