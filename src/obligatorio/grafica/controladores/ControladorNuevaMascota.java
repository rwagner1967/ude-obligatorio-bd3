package obligatorio.grafica.controladores;

import java.rmi.RemoteException;

import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascota;
import obligatrio.grafica.ventanas.NuevaMascota;

public class ControladorNuevaMascota {
	private NuevaMascota nm;
	private ICertamenes certamenes;
	
	public ControladorNuevaMascota(NuevaMascota nm, ICertamenes certamenes) {
		super();
		this.nm = nm;
		this.certamenes = certamenes;
	}
	
	public void nuevaMascota(int cedula, String apodo, String raza) {
		VOMascota voM = new VOMascota(apodo, raza);
		boolean hayError = false;
		String msg = "Registro de mascota exitoso";
		try {
			certamenes.nuevaMascota(cedula, voM);
		} catch (RemoteException e) {
			hayError = true;
			msg = "Error de comunicación";
		} catch ( PersistenciaException | DuenioException e) {
			hayError = true;
			msg = e.getMessage();
		}
		nm.setMensaje(hayError, msg);
	}
}
