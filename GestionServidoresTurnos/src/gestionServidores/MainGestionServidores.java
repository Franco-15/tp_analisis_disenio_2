package gestionServidores;

public class MainGestionServidores {
	public static void main(String[] args) {
		
		Servidores servidores = new Servidores();
		
		servidores.agregarServidor(new Servidor("Gestion_Turnos_1", "localhost", 1, 3, 2, 16, 15));
		servidores.agregarServidor(new Servidor("Gestion_Turnos_2", "localhost", 7, 5, 8, 18, 13));
		
		GestionServidoresFacade gestionServidores = new GestionServidoresFacade(servidores);
		
		gestionServidores.iniciarServidores();
		
	}
}
