package obligatorio.persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import obligatorio.logica.Mascota;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.Conexion;
import obligatorio.persistencia.IConexion;
import obligatorio.persistencia.consultas.Consultas;

public class DAOMascotas {
	private int cedDuenio;

	public DAOMascotas(int cedDuenio) throws PersistenciaException {
		super();
		this.cedDuenio = cedDuenio;
	}

	public void insback(IConexion icon, Mascota mascota) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		int cantidad = 0;
		boolean errorPersistencia = false;
		String msg = null;

		try {
			cantidad = this.largo(icon);
			cantidad += 1;
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.insertMascota());
			pstmt.setInt(1, cantidad);
			pstmt.setString(2, mascota.getApodo());
			pstmt.setString(3, mascota.getRaza());
			pstmt.setInt(4, this.cedDuenio);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = e.getMessage();
		} catch (SQLException e) {
			errorPersistencia = true;
			msg = "Error de acceso a los datos";
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

	public int largo(IConexion icon) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		ResultSet rs = null;
		String msg = null;
		boolean errorPersistencia = false;
		int largo = 0;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.cantMascotasXDuenio());
			pstmt.setInt(1, this.cedDuenio);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				largo = rs.getInt("cant");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error de acceso a los datos";
			errorPersistencia = true;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
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
		return largo;
	}

	public Mascota kEsima(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		ResultSet rs = null;
		String msg = null;
		Mascota mascota = null;
		boolean existeMascota = false;
		boolean errorPersistencia = false;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.obtenerMascota());
			pstmt.setInt(1, numInsc);
			pstmt.setInt(2, this.cedDuenio);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String apodo = rs.getString("apodo");
				String raza = rs.getString("raza");
				mascota = new Mascota(numInsc, apodo, raza);
				existeMascota = true;
			} else {
				msg = "No tiene mascota registrada";
				existeMascota = false;
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "Error de acceso a los datos";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
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
			if (!existeMascota) {
				throw new MascotaRegistradaException(msg);
			}
		}
		return mascota;
	}

	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		ResultSet rs = null;
		String msg = null;
		boolean errorPersistencia = false;
		List<VOMascotaList> lista = new ArrayList<>();

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.listarMascotasDuenio());
			pstmt.setInt(1, this.cedDuenio);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int numInscripcion = rs.getInt("numInscripcion");
				String apodo = rs.getString("apodo");
				String raza = rs.getString("raza");
				VOMascotaList mascota = new VOMascotaList(apodo, raza, numInscripcion);
				lista.add(mascota);
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "Error de acceso a los datos";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
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
		return lista;
	}

	public void borrarMascotas(IConexion icon) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		String msg = null;
		boolean errorPersistencia = false;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.borrarMascotas());
			pstmt.setInt(1, cedDuenio);
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

	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException {
		PreparedStatement pstmt = null;
		Consultas consultas = new Consultas();
		ResultSet rs = null;
		String msg = null;
		int cantidad = 0;
		boolean errorPersistencia = false;

		try {
			pstmt = ((Conexion) icon).getConnection().prepareStatement(consultas.contarMascotas());
			pstmt.setInt(1, this.cedDuenio);
			pstmt.setString(2, raza);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cantidad = rs.getInt("cantidad");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "Error de acceso a los datos";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
			}
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
		return cantidad;
	}
}