package obligatorio.persistencia.daos;

import java.util.List;

import obligatorio.logica.Mascota;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.IConexion;

public class DAOMascotasArchivo implements IDAOMascotas {
	private int cedDuenio;

	public DAOMascotasArchivo(int cedDuenio) throws PersistenciaException {
		super();
		this.cedDuenio = cedDuenio;
	}
	@Override
	public void insback(IConexion icon, Mascota mascota) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public int largo(IConexion icon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Mascota kEsima(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarMascotas(IConexion icon) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

}
