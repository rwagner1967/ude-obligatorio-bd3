package obligatorio.logica.excepciones;

public class PersistenciaException extends Exception {

	private static final long serialVersionUID = 3701794063557774359L;

	public PersistenciaException(String msg) {
		super(msg);
	}
}
