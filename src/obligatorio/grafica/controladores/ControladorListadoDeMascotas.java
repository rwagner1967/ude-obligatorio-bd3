package obligatorio.grafica.controladores;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import obligatorio.grafica.ventanas.ListarMascotaDeUnDuenio;
import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascotaList;

public class ControladorListadoDeMascotas {
	ListarMascotaDeUnDuenio ventana;
	private ICertamenes certamenes;
	
	public ControladorListadoDeMascotas(ListarMascotaDeUnDuenio ventana, ICertamenes certamenes) {
		super();
		this.ventana = ventana;
		this.certamenes = certamenes;
	}
	
	public List<VOMascotaList> listarMascotasDuenio(int cedula) {
		List<VOMascotaList> mascotas = new ArrayList<>();
		boolean hayError = false;
		String msg = null;
		
		try {
			mascotas = certamenes.listarMascotasDuenio(cedula);
		} catch (RemoteException e) {
			hayError = true;
			msg = "Error de comunicación";
		} catch (DuenioException | PersistenciaException e) {
			hayError = true;
			msg = e.getMessage();
		} 
		if (hayError) {
			ventana.setMensaje(hayError, msg);
		}
		return mascotas;
	}
	
}
