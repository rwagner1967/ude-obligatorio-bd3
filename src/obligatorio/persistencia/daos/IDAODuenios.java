package obligatorio.persistencia.daos;

import java.util.List;

import obligatorio.logica.Duenio;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.persistencia.IConexion;

public interface IDAODuenios {
	public boolean member(IConexion icon, int ced) throws PersistenciaException;
	public void insert(IConexion icon, Duenio due) throws PersistenciaException;
	public Duenio find(IConexion icon, int cedula) throws PersistenciaException;
	public void delete(IConexion icon, int ced) throws PersistenciaException;
	public List<VODuenio> listarDuenios(IConexion icon) throws PersistenciaException;
}
