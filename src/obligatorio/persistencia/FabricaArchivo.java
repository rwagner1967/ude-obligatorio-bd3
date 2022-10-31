package obligatorio.persistencia;

import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.persistencia.daos.DAODueniosArchivo;
import obligatorio.persistencia.daos.DAOMascotasArchivo;
import obligatorio.persistencia.daos.IDAODuenios;
import obligatorio.persistencia.daos.IDAOMascotas;

public class FabricaArchivo implements FabricaAbstracta {

	@Override
	public IDAODuenios crearIDAODuenios() throws PersistenciaException {
		// TODO Auto-generated method stub
		return new DAODueniosArchivo();
	}

	@Override
	public IDAOMascotas crearIDAOMascotas(int cedDuenio) throws PersistenciaException {
		return new DAOMascotasArchivo(cedDuenio);
	}

	@Override
	public IPoolConexiones crearIPoolConexiones() throws PersistenciaException {
		return new PoolConexionesArchivo();
	}

}
