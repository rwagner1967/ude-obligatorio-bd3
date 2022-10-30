package obligatorio.persistencia;

import java.sql.SQLException;

public interface IPoolConexiones {
	public IConexion obtenerConexion(boolean ok) throws SQLException, InterruptedException;
	public void liberarConexion(IConexion con, boolean ok) throws SQLException;
}
