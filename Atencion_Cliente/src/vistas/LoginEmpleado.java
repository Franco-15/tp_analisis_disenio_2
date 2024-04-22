package vistas;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoginEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textField_numero_box;
	private JLabel lblNewLabel_2;
	private JButton btnIngresar;

	/**
	 * Create the frame.
	 */
	public LoginEmpleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 323);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.lblNewLabel = new JLabel("Bienvenido");
		this.lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 25));
		this.lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel.setBounds(10, 11, 551, 47);
		this.contentPane.add(this.lblNewLabel);

		this.lblNewLabel_1 = new JLabel("Número box:");
		this.lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.lblNewLabel_1.setBounds(10, 143, 112, 29);
		this.contentPane.add(this.lblNewLabel_1);

		this.textField_numero_box = new JTextField();
		this.textField_numero_box.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.textField_numero_box.setBounds(132, 143, 429, 29);
		this.contentPane.add(this.textField_numero_box);
		this.textField_numero_box.setColumns(10);

		this.lblNewLabel_2 = new JLabel("Ingrese el número de box");
		this.lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel_2.setBounds(10, 87, 551, 29);
		this.contentPane.add(this.lblNewLabel_2);

		this.btnIngresar = new JButton("Ingresar");
		this.btnIngresar.setActionCommand("INGRESAR");
		this.btnIngresar.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.btnIngresar.setBounds(433, 231, 128, 42);
		this.contentPane.add(this.btnIngresar);
	}

	public void setActionListener(ActionListener actionListener) {
		this.btnIngresar.addActionListener(actionListener);
	}

	public String getNumeroBox() {
		return this.textField_numero_box.getText();
	}

	public void numeroBoxInvalido() {
		JOptionPane.showMessageDialog(this, "El número de box ingresado no es válido.", "Número de box inválido",
				JOptionPane.ERROR_MESSAGE);
	}

	public void cerrarVentana() {
		this.dispose();
	}
}
