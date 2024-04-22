package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfirmationRegistro extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel confirmationLabel;
	private JButton confirmButton;
	private JButton cancelButton;

	public ConfirmationRegistro(JFrame parent, String document) {
		super(parent, "Confirmar Documento", true);
		setSize(458, 236);
		setLocationRelativeTo(parent);

		confirmationLabel = new JLabel("Desea registrarse con el documento " + document + "?");
		this.confirmationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.confirmationLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		confirmButton = new JButton("Confirmar");
		this.confirmButton.setActionCommand("CONFIRMAR");
		this.confirmButton.setForeground(new Color(0, 170, 0));
		this.confirmButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.confirmButton.setBounds(320, 43, 112, 44);

		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel buttonPanel = new JPanel();

		panel.add(confirmationLabel);
		cancelButton = new JButton("Cancelar");
		this.cancelButton.setActionCommand("CANCELAR");
		this.cancelButton.setForeground(new Color(255, 70, 70));
		this.cancelButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		this.cancelButton.setBounds(10, 43, 106, 44);
		
		buttonPanel.setLayout(null);
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
		panel.add(buttonPanel);

		getContentPane().add(panel);
	}

	public void setActionListener(ActionListener actionListener) {
		this.cancelButton.addActionListener(actionListener);
		this.confirmButton.addActionListener(actionListener);
	}
}
