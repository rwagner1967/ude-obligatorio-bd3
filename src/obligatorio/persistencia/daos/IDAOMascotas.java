package obligatorio.persistencia.daos;

import java.util.List;

import obligatorio.logica.Mascota;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.IConexion;

public interface IDAOMascotas {
	public void insback(IConexion icon, Mascota mascota) throws PersistenciaException;
	public int largo(IConexion icon) throws PersistenciaException;
	public Mascota kEsima(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException;
	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException;
	public void borrarMascotas(IConexion icon) throws PersistenciaException;
	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException;
}
