package obligatorio.grafica.controladores;

import java.rmi.RemoteException;

import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatrio.grafica.ventanas.NuevoDuenio;

public class ControladorNuevoDuenio {
	private NuevoDuenio nd;
	private ICertamenes certamenes;
	
	public ControladorNuevoDuenio(NuevoDuenio nd, ICertamenes certamenes) {
		super();
		this.nd = nd;
		this.certamenes = certamenes;
	}
	
	public void nuevoDuenio(int cedula, String nombre, String apellido) {
		boolean hayError = false;
		String msg = "Dueño registrado correctamente";
		VODuenio vod = new VODuenio(cedula, nombre, apellido);
		try {
			certamenes.nuevoDuenio(vod);
		} catch (RemoteException e) {
			hayError = true;
			msg = "Error de comunicación";
		} catch ( PersistenciaException | DuenioException e) {
			hayError = true;
			msg = e.getMessage();
		}
		nd.setMensaje(hayError, msg);
	}
}
