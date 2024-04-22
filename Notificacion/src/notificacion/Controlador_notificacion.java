package notificacion;
import java.util.ArrayList;

import notificacion.TElementoNotificacion;
import vista.Vista_notificacion;

public class Controlador_notificacion {
	
    private ArrayList<String[]> datos;
    private ArrayList<TElementoNotificacion> objetos;
    private Vista_notificacion interfaz;
    private Temporizador tiempo;
	
	public Controlador_notificacion() {
		this.datos = null;
		this.interfaz = new Vista_notificacion();
		this.tiempo = new Temporizador(); 
		this.objetos = null;
	}
	

	
	public Temporizador getTiempo() {
		return tiempo;
	}



	public ArrayList<String[]> getDatos() {
		return datos;
	}


	public void setDatos(ArrayList<String[]> datos) {
		this.datos = datos;
	}


	public void mostrar() {
		// un string dni y un int box ---> muestro 
		// metodo notificar (muestra elemento de la lista de hasta 9)
		// metodo agregar notificacion ()
		// puede no ser llamado
		// timer para mostrar cierto tiempo la notificacion.
	}
	
	
	public  void agregar_cliente(String dni, String box) {
	    if (datos == null) {
	        this.datos = new ArrayList<>();
	    }
	    this.datos.add(new String[]{dni, box});
	    
	    this.actualizar_pantalla();
	    Temporizador tiempo = new Temporizador();
	    tiempo.esperar5Segundos();
	    this.eliminar_cliente(dni);
	}
	
	void eliminar_cliente(String dni) {
	    if (datos != null) {
	        // Iterar sobre la lista de datos
	        for (int i = 0; i < datos.size(); i++) {
	            // Obtener el DNI del cliente actual
	            String[] cliente = datos.get(i);
	            String dniCliente = cliente[0];
	            // Si el DNI coincide con el proporcionado, eliminar el cliente
	            if (dniCliente.equals(dni)) {
	                datos.remove(i);
	                // Terminar el bucle después de eliminar el cliente
	                break;
	            }
	        }
	    }
	    this.actualizar_pantalla();
	}
	public void actualizar_pantalla() {
		
		
        interfaz.actualizar_pantalla(this.datos);
	}
	
	public static void main(String[] args) {
        Controlador_notificacion controlador = new Controlador_notificacion();
        
        // Agregar algunos clientes
        controlador.agregar_cliente("12345678", "Box 1");
        controlador.agregar_cliente("12345678", "Box 2");
        controlador.agregar_cliente("12345678", "Box 3");
        controlador.agregar_cliente("12345678", "Box 4");
        controlador.agregar_cliente("12345678", "Box 5");
        
        
        
        
        // Actualizar y mostrar la pantalla
        controlador.getTiempo().esperar5Segundos();
     // Eliminar un cliente
        controlador.eliminar_cliente("12345678");
        
    }
	
}
