package obligatorio.persistencia;

import java.sql.SQLException;

public class PoolConexionesArchivo implements IPoolConexiones {

	@Override
	public IConexion obtenerConexion(boolean ok) throws SQLException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void liberarConexion(IConexion con, boolean ok) throws SQLException {
		// TODO Auto-generated method stub

	}

}
