package obligatorio.persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import obligatorio.logica.Duenio;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;
import obligatorio.persistencia.IConexion;

public class DAODueniosArchivo implements IDAODuenios {

	@Override
	public boolean member(IConexion icon, int ced) throws PersistenciaException {
		String nomArch = "datos/duenio-" + ced + ".txt";
		File file = new File(nomArch);
		// Si el archivo no existe, se crea!
		return file.exists();
	}

	@Override
	public void insert(IConexion icon, Duenio due) throws PersistenciaException {
		try {
			String nomArch = "datos/duenio-" + due.getCedula() + ".txt";
			VODuenio voDuenioArch = new VODuenio(due.getCedula(), due.getNombre(), due.getApellido());

			FileOutputStream archivo = new FileOutputStream(nomArch);
			ObjectOutputStream canalDeSalida = new ObjectOutputStream(archivo);
			canalDeSalida.writeObject(voDuenioArch);
			canalDeSalida.close();
			archivo.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}
	}

	@Override
	public Duenio find(IConexion icon, int cedula) throws PersistenciaException {
		Duenio due = null;

		String nomArch = "datos/duenio-" + cedula + ".txt";

		FileInputStream archivo;
		try {
			archivo = new FileInputStream(nomArch);
			ObjectInputStream canalDeEntrada = new ObjectInputStream(archivo);
			VODuenio voDuenioArchivo = (VODuenio) canalDeEntrada.readObject();
			canalDeEntrada.close();
			archivo.close();

			due = new Duenio(voDuenioArchivo.getCedula(), voDuenioArchivo.getNombre(), voDuenioArchivo.getApellido());
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}
		return due;
	}

	@Override
	public void delete(IConexion icon, int ced) throws PersistenciaException {
		String nomArch = "datos/duenio-" + ced + ".txt";
		Path path = (Path) FileSystems.getDefault().getPath(nomArch);
		try {
			Files.delete((java.nio.file.Path) path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a los datos");
		}	
	}

	@Override
	public List<VODuenio> listarDuenios(IConexion icon) throws PersistenciaException {
		
		File directorio = new File("datos");
		File[] listaArchivos = directorio.listFiles();

		for (int i = 0; i < listaArchivos.length; i++) {
		  if (listaArchivos[i].isFile()) {
		    System.out.println("File " + listaArchivos[i].getName());
		  } else if (listaArchivos[i].isDirectory()) {
		    System.out.println("Directory " + listaArchivos[i].getName());
		  }
		}
							
		return null;
	}

}
