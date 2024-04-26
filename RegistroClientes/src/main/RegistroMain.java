package main;

import vistas.RegistroVistaMain;

public class RegistroMain {

	public static void main(String[] args) {
		try {
			RegistroVistaMain frame = new RegistroVistaMain();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
