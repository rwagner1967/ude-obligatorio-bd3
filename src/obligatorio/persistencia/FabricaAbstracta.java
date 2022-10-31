package obligatorio.persistencia;

import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.persistencia.daos.IDAODuenios;
import obligatorio.persistencia.daos.IDAOMascotas;

public interface FabricaAbstracta {
	public IDAODuenios crearIDAODuenios() throws PersistenciaException;
	public IDAOMascotas crearIDAOMascotas(int cedDuenio) throws PersistenciaException;
	public IPoolConexiones crearIPoolConexiones() throws PersistenciaException;
}
