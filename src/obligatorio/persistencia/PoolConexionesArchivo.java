package obligatorio.persistencia;

import java.sql.SQLException;

public class PoolConexionesArchivo implements IPoolConexiones {
	private int cantidadDeLectores = 0;
	private boolean escribiendo = false;

	@Override
	public synchronized IConexion obtenerConexion(boolean ok) throws SQLException, InterruptedException {

		IConexion con = new ConexionArchivo(ok);
		if (ok) { //lectores
			while (escribiendo) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			cantidadDeLectores++;
			notifyAll();
		} else { //escritores
			while (escribiendo || (cantidadDeLectores != 0)) {
				try {
					wait();
				} 
				catch (InterruptedException e) {}
			}
			escribiendo = true;
		}
		return con;
	}

	@Override
	public synchronized void liberarConexion(IConexion icon, boolean ok) throws SQLException {
		ConexionArchivo con = (ConexionArchivo) icon;
		if (con.esLectura()) {
			cantidadDeLectores--;
			if (cantidadDeLectores == 0)
				this.notifyAll();
		} else {
			escribiendo = false;
			notifyAll();
		}
	}
}
