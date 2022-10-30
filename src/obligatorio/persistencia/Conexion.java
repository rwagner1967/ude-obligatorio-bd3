package obligatorio.persistencia;

import java.sql.Connection;

public class Conexion implements IConexion {
	private Connection con;

	public Conexion(Connection con) {
		super();
		this.con = con;
	}

	public Connection getConnection() {
		return con;
	}
}
