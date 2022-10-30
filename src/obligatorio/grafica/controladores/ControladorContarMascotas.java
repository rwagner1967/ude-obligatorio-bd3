package obligatorio.grafica.controladores;

import java.rmi.RemoteException;

import obligatorio.grafica.ventanas.ContarMascotas;
import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.PersistenciaException;

public class ControladorContarMascotas {
	private ContarMascotas ventana;
	private ICertamenes certamenes;
	
	public ControladorContarMascotas(ContarMascotas ventana, ICertamenes certamenes) {
		super();
		this.ventana = ventana;
		this.certamenes = certamenes;
	}
	
	public int contarMascotas(int cedula, String raza) {
		int cant = 0;
		String msg = null;
		boolean hayError = false;
		
		try {
			cant = certamenes.contarMascotas(cedula, raza);
		} catch (RemoteException e) {
			hayError = true;
			msg = "Error de comunicación";
		} catch (DuenioException | PersistenciaException  e) {
			hayError = true;
			msg = e.getMessage();
		} 
		if (hayError) {
			ventana.setMensaje(hayError, msg);
		}
		
		return cant;
	}
}
