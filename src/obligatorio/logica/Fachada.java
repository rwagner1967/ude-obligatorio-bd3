package obligatorio.logica;

import java.util.ArrayList;
import java.util.List;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.logica.valueObjects.VOMascota;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.FabricaAbstracta;
import obligatorio.persistencia.IConexion;
import obligatorio.persistencia.IPoolConexiones;
import obligatorio.persistencia.daos.IDAODuenios;

public class Fachada {
	IDAODuenios dicDuenios;
	private IPoolConexiones pool;

	public Fachada() throws PersistenciaException {
		FabricaAbstracta fabrica = Fabrica.getInstance().getFabrica();
		dicDuenios = fabrica.crearIDAODuenios();
		pool = fabrica.crearIPoolConexiones();
	}

	public void nuevoDuenio(VODuenio voD) throws PersistenciaException, DuenioException {
		IConexion icon = null;
		String msg = null;
		boolean existsCed = false;
		boolean errorPersistencia = false;
		try {
			icon = pool.obtenerConexion(false);
			existsCed = dicDuenios.member(icon, voD.getCedula());
			if (!existsCed) {
				Duenio due = new Duenio(voD.getCedula(), voD.getNombre(), voD.getApellido());
				dicDuenios.insert(icon, due);
			} else {
				msg = "Dueño ya existe";
			}
			pool.liberarConexion(icon, true);
		} catch (Exception e) {
			errorPersistencia = true;
			msg = "error de acceso a los datos";
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
				throw new PersistenciaException(msg);
			}
			if (existsCed)
				throw new DuenioException(msg);
		}
	}

	public void nuevaMascota(int cedula, VOMascota voM) throws DuenioException, PersistenciaException {
		IConexion icon = null;
		String msg = null;
		boolean existsCed = false;
		boolean errorPersistencia = false;

		try {
			icon = pool.obtenerConexion(false);
			existsCed = dicDuenios.member(icon, cedula);
			if (existsCed) {
				Duenio due = dicDuenios.find(icon, cedula);
				Mascota mascota = new Mascota(0, voM.getApodo(), voM.getRaza());
				due.agregarMascota(icon, mascota);
			} else {
				msg = "No existe Dueño";
			}
			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = e.getMessage();
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
				throw new PersistenciaException(msg);
			}
			if (!existsCed)
				throw new DuenioException(msg);
		}
	}

	public void borrarDuenioMascota(int cedula) throws DuenioException, PersistenciaException {
		String msg = null;
		boolean existsCed = false;
		boolean errorPersistencia = false;
		IConexion icon = null;

		try {
			icon = pool.obtenerConexion(false);
			existsCed = dicDuenios.member(icon, cedula);
			if (existsCed) {
				Duenio due = dicDuenios.find(icon, cedula);
				due.borrarMascotas(icon);
				dicDuenios.delete(icon, cedula);
			} else {
				msg = "Dueño no existe";
			}
			pool.liberarConexion(icon, true);
		} catch (Exception e) {
			errorPersistencia = true;
			msg = "error de acceso a los datos";
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
				throw new PersistenciaException(msg);
			}
			if (!existsCed)
				throw new DuenioException(msg);
		}
	}

	public List<VODuenio> listarDuenios() throws PersistenciaException {
		IConexion icon = null;
		List<VODuenio> duenios = null;
		String msg = null;
		boolean errorPersistencia = false;

		try {
			icon = pool.obtenerConexion(true);
			duenios = dicDuenios.listarDuenios(icon);
			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = "error de acceso a los datos";
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
					errorPersistencia = true;
					msg = "error de acceso a los datos";
				}
				throw new PersistenciaException(msg);
			}
		}
		return duenios;
	}

	public List<VOMascotaList> listarMascotasDuenio(int cedula) throws DuenioException, PersistenciaException {
		IConexion icon = null;
		String msg = null;
		boolean existsCed = false;
		boolean errorPersistencia = false;
		List<VOMascotaList> mascotas = new ArrayList<>();

		try {
			icon = pool.obtenerConexion(true);
			existsCed = dicDuenios.member(icon, cedula);
			if (existsCed) {
				Duenio due = dicDuenios.find(icon, cedula);
				mascotas = due.listarMascotas(icon);
			} else {
				msg = "No existe Dueño";
			}
			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = e.getMessage();
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
				throw new PersistenciaException(msg);
			}
			if (!existsCed)
				throw new DuenioException(msg);
		}
		return mascotas;
	}

	public VOMascota obtenerMascota(int cedula, int numInscripcion)
			throws DuenioException, PersistenciaException, MascotaRegistradaException {
		IConexion icon = null;
		String msg = null;
		boolean existsCed = false;
		VOMascota voM = null;
		boolean errorPersistencia = false;
		boolean mascotaRegistrada = true;

		try {
			icon = pool.obtenerConexion(true);
			existsCed = dicDuenios.member(icon, cedula);
			if (existsCed) {
				Duenio due = dicDuenios.find(icon, cedula);
				voM = due.obtenerMascota(icon, numInscripcion);
			} else {
				msg = "No existe Dueño";
			}
			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = e.getMessage();
		} catch (MascotaRegistradaException e) {
			e.printStackTrace();
			msg = e.getMessage();
			mascotaRegistrada = false;
			try {
				pool.liberarConexion(icon, false);
			} catch (PersistenciaException e1) {
				e1.printStackTrace();
				errorPersistencia=true;
				msg="error de acceso a los datos";
			}
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
				throw new PersistenciaException(msg);
			}
			if (!existsCed)
				throw new DuenioException(msg);
			if (!mascotaRegistrada) {
				throw new MascotaRegistradaException(msg);
			}
		}

		return voM;
	}

	public int contarMascotas(int cedula, String raza) throws PersistenciaException, DuenioException {
		String msg = null;
		boolean existsCed = false;
		int cantidad = 0;
		IConexion icon = null;
		boolean errorPersistencia = false;

		try {
			icon = pool.obtenerConexion(true);
			existsCed = dicDuenios.member(icon, cedula);
			if (existsCed) {
				Duenio due = dicDuenios.find(icon, cedula);
				cantidad = due.contarMascotas(icon, raza);
			} else {
				msg = "No existe Dueño";
			}
			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			errorPersistencia = true;
			msg = e.getMessage();
		} finally {
			if (errorPersistencia) {
				try {
					pool.liberarConexion(icon, false);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
				throw new PersistenciaException(msg);
			}
			if (!existsCed)
				throw new DuenioException(msg);
		}
		return cantidad;
	}
}
