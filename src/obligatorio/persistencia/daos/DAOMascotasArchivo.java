package obligatorio.persistencia.daos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import obligatorio.logica.Mascota;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.IConexion;

public class DAOMascotasArchivo implements IDAOMascotas {
	private int cedDuenio;

	public DAOMascotasArchivo(int cedDuenio) throws PersistenciaException {
		super();
		this.cedDuenio = cedDuenio;
	}
	@Override
	public void insback(IConexion icon, Mascota mascota) throws PersistenciaException {
		try {
			String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
			FileOutputStream archivo = new FileOutputStream(nomArch);
			ObjectOutputStream canalDeSalida = new ObjectOutputStream(archivo);
			canalDeSalida.writeObject(mascota);
			canalDeSalida.close();
			archivo.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}
	}

	@Override
	public int largo(IConexion icon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Mascota kEsima(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException {
		ArrayList<VOMascotaList> listaMascotas = new ArrayList<>();
		//No estoy muy seguro con esta solucion
		/*String nomArch = "datos/duenio-" + cedDuenio + ".txt";
		try {
			FileInputStream archivo = new FileInputStream(nomArch);
			ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
			listaMascotas = (ArrayList<VOMascotaList>) canalDeEntrada.readObject();
			canalDeEntrada.close();
			}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}*/
		return listaMascotas;
	}

	@Override
	public void borrarMascotas(IConexion icon) throws PersistenciaException {
		String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
		Path path = (Path) FileSystems.getDefault().getPath(nomArch);
		try {
			Files.delete((java.nio.file.Path) path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}

	}

	@Override
	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException {
		return 0;
	}

}
