package obligatorio.persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import obligatorio.logica.excepciones.PersistenciaException;

public class PoolConexiones implements IPoolConexiones {
	private static final int MAX_CANT_CONEXIONES = 10;
	private String driver;
	private String url;
	private String password;
	private String user;
	private int nivelTransaccionalidad;
	private Conexion conexiones[];
	private int creadas;
	private int tope;

	public PoolConexiones() throws PersistenciaException {
		super();
		conexiones = new Conexion[MAX_CANT_CONEXIONES];
		nivelTransaccionalidad = Connection.TRANSACTION_SERIALIZABLE;
		Properties p = new Properties();
		String nomArchivo = "config/datos.properties";

		try {
			p.load(new FileInputStream(nomArchivo));
			this.driver = p.getProperty("driver");
			this.url = p.getProperty("url");
			this.user = p.getProperty("user");
			this.password = p.getProperty("password");
			Class.forName(driver);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("No pudo abrir archivo de propiedades");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("No pudo obtener driver");
		}
	}

	@Override
	public synchronized IConexion obtenerConexion(boolean ok) throws SQLException, InterruptedException {
		Conexion con = null;
		boolean fin = false;

		while (!fin) {
			if (tope > 0) {
				con = conexiones[tope - 1];
				tope--;
				fin = true;
			} else if (creadas < MAX_CANT_CONEXIONES) {
				Connection c = null;
				c = DriverManager.getConnection(url, user, password);
				con = new Conexion(c);
				creadas++;
				fin=true;
			} else {
				wait();
			}
		}
		con.getConnection().setTransactionIsolation(nivelTransaccionalidad);
		con.getConnection().setAutoCommit(false);
		return con;
	}

	@Override
	public synchronized void liberarConexion(IConexion con, boolean ok) throws SQLException {
		if (ok) {
			((Conexion) con).getConnection().commit();
		}
		else {
			((Conexion) con).getConnection().rollback();
		}
		conexiones[tope] = (Conexion) con;
		tope++;
		notify();
	}
}
