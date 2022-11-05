package obligatorio.persistencia;

public class ConexionArchivo implements IConexion {
	private boolean conDeLectura; // true es de lectura, false es de escritura;
	
	public ConexionArchivo(boolean esLectura) {
		conDeLectura = esLectura;
	}
	
	public boolean esLectura() {
		return conDeLectura;
	}
}
