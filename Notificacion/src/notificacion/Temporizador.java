package notificacion;

public class Temporizador {
	
	
    public Temporizador() {
	}

	public void esperar5Segundos() {
        try {
            Thread.sleep(5000); // 5000 milisegundos = 5 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
