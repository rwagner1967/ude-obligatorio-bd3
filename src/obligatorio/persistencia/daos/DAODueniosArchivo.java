package obligatorio.persistencia.daos;

import java.util.List;

import obligatorio.logica.Duenio;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.persistencia.IConexion;

public class DAODueniosArchivo implements IDAODuenios {

	@Override
	public boolean memeber(IConexion icon, int ced) throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(IConexion icon, Duenio due) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public Duenio find(IConexion icon, int cedula) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IConexion icon, int ced) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VODuenio> listarDuenios(IConexion icon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
