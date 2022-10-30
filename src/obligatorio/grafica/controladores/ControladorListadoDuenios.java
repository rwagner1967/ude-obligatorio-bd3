package obligatorio.grafica.controladores;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import obligatorio.grafica.ventanas.ListarDuenios;
import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;

public class ControladorListadoDuenios {
	ListarDuenios ld;
	private ICertamenes certamenes;
	
	public ControladorListadoDuenios(ListarDuenios ld, ICertamenes certamenes) {
		super();
		this.ld = ld;
		this.certamenes = certamenes;
	}
	
	public List<VODuenio> listarDuenios() {
		List<VODuenio> duenios = new ArrayList<VODuenio>();
		boolean hayError = false;
		String msg = null;
		try {
			duenios = certamenes.listarDuenios();
		} catch (RemoteException e) {
			msg = "Error de comunicación";
			hayError = true;
		} catch (PersistenciaException e) {
			hayError = true;
			msg = e.getMessage();
		}
		if (hayError) {
			ld.setMensaje(hayError, msg);
		}
		return duenios;
	}
}
