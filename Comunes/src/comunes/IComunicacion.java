package comunes;

import java.io.IOException;

public interface IComunicacion {
	public void publicar(MensajeComunicacion mensaje)  throws IOException;
	public MensajeComunicacion recibir();
}
