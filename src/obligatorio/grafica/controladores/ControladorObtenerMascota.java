package obligatorio.grafica.controladores;

import java.rmi.RemoteException;

import obligatorio.grafica.ventanas.ObtenerMascota;
import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascota;

public class ControladorObtenerMascota {
	private ObtenerMascota obtenerMascota;
	private ICertamenes certamenes;
	
	public ControladorObtenerMascota(ObtenerMascota obtenerMascota, ICertamenes certamenes) {
		super();
		this.obtenerMascota = obtenerMascota;
		this.certamenes = certamenes;
	}
	
	public VOMascota obtenerMascota(int cedula, int numInscripcion) {
		VOMascota mascota = new VOMascota("","");
		boolean hayError = false;
		String msg = null;
		
		try {
			mascota = certamenes.obtenerMascota(cedula, numInscripcion);
		} catch (RemoteException e) {
			hayError = true;
			msg = "Error de comunicación";
		} catch (DuenioException | PersistenciaException | MascotaRegistradaException e) {
			hayError = true;
			msg = e.getMessage();
		} 
		if (hayError) {
			obtenerMascota.setMensaje(hayError, msg);
		}
		return mascota;
	}
}
