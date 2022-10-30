package obligatorio.logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.logica.valueObjects.VOMascota;
import obligatorio.logica.valueObjects.VOMascotaList;


public interface ICertamenes extends Remote {
	public void nuevoDuenio(VODuenio voD) throws RemoteException, PersistenciaException, DuenioException;
	public List<VODuenio> listarDuenios() throws RemoteException, PersistenciaException;
	public void nuevaMascota(int cedula, VOMascota voM) throws RemoteException, DuenioException, PersistenciaException;
	public List<VOMascotaList> listarMascotasDuenio(int cedula) throws RemoteException, DuenioException, PersistenciaException;
	public VOMascota obtenerMascota(int cedula, int numInscripcion) throws RemoteException, DuenioException, PersistenciaException, MascotaRegistradaException;
	public int contarMascotas(int cedula, String raza) throws RemoteException, PersistenciaException, DuenioException;
	public void borrarDuenioMascota(int cedula) throws RemoteException, DuenioException, PersistenciaException;
}
