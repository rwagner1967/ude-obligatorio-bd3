package obligatorio.persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import obligatorio.logica.Mascota;
import obligatorio.logica.excepciones.MascotaRegistradaException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VOMascota;
import obligatorio.logica.valueObjects.VOMascotaList;
import obligatorio.persistencia.IConexion;

public class DAOMascotasArchivo implements IDAOMascotas {
	private int cedDuenio;

	public DAOMascotasArchivo(int cedDuenio) throws PersistenciaException {
		super();
		this.cedDuenio = cedDuenio;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insback(IConexion icon, Mascota mascota) throws PersistenciaException {
		String nomArchivo = "datos/mascotas-" + cedDuenio + ".txt";
		VOMascota voMascota = new VOMascota(mascota.getApodo(), mascota.getRaza());
		ArrayList<VOMascota> listMascota = null;

		File file = new File(nomArchivo);
		if (file.exists()) {
			try {
				String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
				FileInputStream archivo = new FileInputStream(nomArch);
				ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
				listMascota = (ArrayList<VOMascota>) canalDeEntrada.readObject();
				canalDeEntrada.close();
				archivo.close();

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("error de acceso a los datos");
			}
		} else {
			listMascota = new ArrayList<>();
		}
		
		listMascota.add(voMascota);

		try {
			FileOutputStream archivo_out = new FileOutputStream(nomArchivo);
			ObjectOutputStream canalDeSalida = new ObjectOutputStream(archivo_out);
			canalDeSalida.writeObject(listMascota);
			canalDeSalida.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int largo(IConexion icon) throws PersistenciaException {
		String nomArchivo = "datos/mascotas-" + cedDuenio + ".txt";
		ArrayList<VOMascota> listOfVOMascota = null;
		int cantidad = 0;
		File file = new File(nomArchivo);
		if (file.exists()) {
			try {
				String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
				FileInputStream archivo = new FileInputStream(nomArch);
				ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
				listOfVOMascota = (ArrayList<VOMascota>) canalDeEntrada.readObject();
				canalDeEntrada.close();
				archivo.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("error de acceso a los datos");
			}
			cantidad = listOfVOMascota.size();
		} 
		return cantidad;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Mascota kEsima(IConexion icon, int numInsc) throws PersistenciaException, MascotaRegistradaException {
		String nomArchivo = "datos/mascotas-" + cedDuenio + ".txt";
		ArrayList<VOMascota> listOfVOMascota = null;
		int indice = numInsc - 1;
		Mascota mascota = null;
		File file = new File(nomArchivo);
		if (file.exists()) {
			try {
				String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
				FileInputStream archivo = new FileInputStream(nomArch);
				ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
				listOfVOMascota = (ArrayList<VOMascota>) canalDeEntrada.readObject();
				canalDeEntrada.close();
				archivo.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("error de acceso a los datos");
			}
		} else {
			throw new MascotaRegistradaException("No tiene mascota registrada");
		}
		if (indice >= 0 && indice < listOfVOMascota.size()) {
			VOMascota m = listOfVOMascota.get(indice);
			mascota = new Mascota(numInsc, m.getApodo(), m.getRaza());
		} else {
			throw new MascotaRegistradaException("No tiene mascota registrada");	
		}
		return mascota;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VOMascotaList> listarMascotas(IConexion icon) throws PersistenciaException {
		ArrayList<VOMascotaList> listaMascotas = new ArrayList<>();
		String nomArchivo = "datos/mascotas-" + cedDuenio + ".txt";
		ArrayList<VOMascota> listOfVOMascota = null;
		
		File file = new File(nomArchivo);
		if (file.exists()) {
			try {
				String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
				FileInputStream archivo = new FileInputStream(nomArch);
				ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
				listOfVOMascota = (ArrayList<VOMascota>) canalDeEntrada.readObject();
				canalDeEntrada.close();
				archivo.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("error de acceso a los datos");
			}
		} else {
			listOfVOMascota = new ArrayList<>();
		}
		for (int i=0; i<listOfVOMascota.size(); i++) {
			VOMascota voMascota = listOfVOMascota.get(i);
			VOMascotaList voMascotaList = new VOMascotaList(voMascota.getApodo(), voMascota.getRaza(), i+1);
			listaMascotas.add(voMascotaList);
		}
		
		return listaMascotas;
	}

	@Override
	public void borrarMascotas(IConexion icon) throws PersistenciaException {
		String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
		Path path = (Path) FileSystems.getDefault().getPath(nomArch);
		try {
			Files.delete((Path) path);
		} catch (NoSuchFileException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public int contarMascotas(IConexion icon, String raza) throws PersistenciaException {
		int cantidad = 0;
		String nomArchivo = "datos/mascotas-" + cedDuenio + ".txt";
		ArrayList<VOMascota> listOfVOMascota = null;
		
		File file = new File(nomArchivo);
		if (file.exists()) {
			try {
				String nomArch = "datos/mascotas-" + cedDuenio + ".txt";
				FileInputStream archivo = new FileInputStream(nomArch);
				ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
				listOfVOMascota = (ArrayList<VOMascota>) canalDeEntrada.readObject();
				canalDeEntrada.close();
				archivo.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("error de acceso a los datos");
			}
		} else {
			listOfVOMascota = new ArrayList<>();
		}
		for (VOMascota voMascota: listOfVOMascota) {
			if ( voMascota.getRaza().equals(raza)) 
				cantidad++;
		}
		return cantidad;
	}
}
