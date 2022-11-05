package obligatorio.logica;

import java.util.List;

import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascota;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.FabricaAbstracta;
import obligatorio.persistencia.IConexion;
import obligatorio.persistencia.daos.IDAOMascotas;

public class Duenio {
	private int cedula;
	private String nombre;
	private String apellido;
	private IDAOMascotas secuencia;
	
	public Duenio(int cedula, String nombre, String apellido) throws PersistenciaException {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		
		FabricaAbstracta fabrica = Fabrica.getInstance().getFabrica();
		this.secuencia = fabrica.crearIDAOMascotas(cedula);
	}

	public int getCedula() {
		return cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}
	
	public boolean tieneMascota(IConexion icon, int numInsc) throws PersistenciaException {
		boolean existeMascota = false;
		try {
			secuencia.kEsima(icon, numInsc);
			existeMascota = true;
		} catch (MascotaRegistradaException e) {
			existeMascota = false;
		}
		return existeMascota;
	}
	
	public int cantidadMascotas( ) {
		return 0;
	}
	
	public void agregarMascota(IConexion icon, Mascota masc) throws PersistenciaException {
		secuencia.insback(icon, masc);
	}
	
	public VOMascota obtenerMascota(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException {
		Mascota mascota = null;
		VOMascota voM = null;
		
		mascota = secuencia.kEsima(icon, numInsc);
		voM = new VOMascota(mascota.getApodo(), mascota.getRaza());
		return voM;
	}
	
	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException {
		return secuencia.listarMascotas(icon);
	}
	
	public void borrarMascotas(IConexion icon) throws PersistenciaException {
		secuencia.borrarMascotas(icon);
	}
	
	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException {
		return secuencia.contarMascotas(icon, raza);
	}
}
