package obligatorio.persistencia;

import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.persistencia.daos.DAODuenios;
import obligatorio.persistencia.daos.DAOMascotas;
import obligatorio.persistencia.daos.IDAODuenios;
import obligatorio.persistencia.daos.IDAOMascotas;

public class FabricaMySQL implements FabricaAbstracta {

	@Override
	public IDAODuenios crearIDAODuenios() throws PersistenciaException {
		return new DAODuenios();
	}

	@Override
	public IDAOMascotas crearIDAOMascotas(int cedDuenio) throws PersistenciaException {
		// TODO Auto-generated method stub
		return new DAOMascotas(cedDuenio);
	}

}
