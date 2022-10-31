package obligatorio.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import obligatorio.logica.Duenio;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.persistencia.Conexion;
import obligatorio.persistencia.IConexion;
import obligatorio.persistencia.consultas.Consultas;

public class DAODuenios implements IDAODuenios {

	public DAODuenios() throws PersistenciaException {
	}

	public boolean memeber(IConexion icon, int ced) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		ResultSet rs = null;
		boolean existsDuenio = false;
		boolean errorPersistencia = false;
		String msg = null;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.existeDuenio());
			pstmt.setInt(1, ced);
			rs = pstmt.executeQuery();
			if (rs.next())
				existsDuenio = true;
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error de acceso a los datos";
			errorPersistencia = true;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
			if (errorPersistencia) {
				throw new PersistenciaException(msg);
			}
		}
		return existsDuenio;
	}

	public void insert(IConexion icon, Duenio due) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		String msg = null;
		boolean errorPersistencia = false;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.insertDuenio());
			pstmt.setInt(1, due.getCedula());
			pstmt.setString(2, due.getNombre());
			pstmt.setString(3, due.getApellido());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "Error de acceso a los datos";
			errorPersistencia = true;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
			if (errorPersistencia) {
				throw new PersistenciaException(msg);
			}
		}

	}

	public Duenio find(IConexion icon, int cedula) throws PersistenciaException {
		Duenio due = null;		
		String msg = null;
		boolean errorPersistencia = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Consultas consultas = new Consultas();

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.obtenerDuenio());
			pstmt.setInt(1,cedula);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				due = new Duenio(cedula, nombre, apellido);
			}
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "Error de acceso a los datos";
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					errorPersistencia = true;
					msg = "Error de acceso a los datos";				
				}
			}
			if (errorPersistencia) {
				throw new PersistenciaException(msg);
			}
		}	
		return due;
	}
	
	public void delete(IConexion icon, int ced) throws PersistenciaException {		
		String msg = null;
		boolean errorPersistencia = false;
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();	
		
		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.borrarDuenio());
			pstmt.setInt(1, ced);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "error de acceso a los datos";
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
			if (errorPersistencia) {
				throw new PersistenciaException(msg);
			}
		}
	}

	public List<VODuenio> listarDuenios(IConexion icon) throws PersistenciaException {
		String msg = null;
		boolean errorPersistencia = false;
		ArrayList<VODuenio> duenios = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Consultas consultas = new Consultas();

		try {
			stmt = ((Conexion) icon).getConnection().createStatement();
			rs = stmt.executeQuery(consultas.listDuenios());
			while (rs.next()) {
				int cedula = rs.getInt("cedula");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				VODuenio voD = new VODuenio(cedula, nombre, apellido);
				duenios.add(voD);
			}
			rs.close();
			stmt.close();
			stmt = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = "Error de acceso a los datos";
			errorPersistencia = true;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
			if (errorPersistencia) {
				throw new PersistenciaException(msg);
			}
		}
		return duenios;
	}
}
