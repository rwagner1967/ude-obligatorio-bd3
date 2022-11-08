package obligatorio.persistencia;

import obligatorio.logica.excepciones.PersistenciaException;

public interface IPoolConexiones {
	public IConexion obtenerConexion(boolean ok) throws PersistenciaException;
	public void liberarConexion(IConexion con, boolean ok) throws PersistenciaException;
}
