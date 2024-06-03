package archivos;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import comunes.TElementoColaEspera;

public class OrdenarColaRangoEtario implements EstrategiaLlamada {

    @Override
    public Queue<TElementoColaEspera> ordenarClientes(Queue<TElementoColaEspera> colaEspera) {
        // Crear una lista temporal para ordenar los clientes por edad
        List<TElementoColaEspera> tempList = new ArrayList<>(colaEspera);

        // Ordenar la lista temporal por edad de mayor a menor
        Collections.sort(tempList, (cliente1, cliente2) -> {
            LocalDate fechaNacimiento1 = convertirAFecha(cliente1.getCliente().getFechaNacimiento());
            LocalDate fechaNacimiento2 = convertirAFecha(cliente2.getCliente().getFechaNacimiento());
            int edadCliente1 = calcularEdad(fechaNacimiento1, LocalDate.now());
            int edadCliente2 = calcularEdad(fechaNacimiento2, LocalDate.now());
            return Integer.compare(edadCliente2, edadCliente1); // Invertir el orden para mayor a menor
        });

        // Limpiar la cola original
        colaEspera.clear();

        // Agregar los elementos ordenados de vuelta a la cola
        colaEspera.addAll(tempList);

        return colaEspera;
    }

    // Método para convertir un String a LocalDate
    private LocalDate convertirAFecha(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula o vacía");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fechaNacimiento, formatter);
    }

    // Método para calcular la edad a partir de la fecha de nacimiento
    private int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaActual) {
        if (fechaNacimiento == null || fechaActual == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        return Period.between(fechaNacimiento, fechaActual).getYears();
    }
}
