package obligatorio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Properties;

import obligatorio.logica.ICertamenes;
import obligatorio.logica.excepciones.DuenioException;
import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.logica.valueObjects.VODuenio;

public class CargarDatosDuenio {
	
	public static void main(String[] args) {
		ICertamenes certamenes = null;
		String nomArch = "config/cliente-gui.properties";
		Properties props = new Properties();
		String ruta = null;
		try {
			props.load(new FileInputStream(nomArch));
			ruta = props.getProperty("ruta");
			certamenes = (ICertamenes) Naming.lookup(ruta);
			
			cargarDatosDuenio(certamenes);
		} catch (NotBoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void cargarDatosDuenio(ICertamenes certamenes) {

		try {
			File archivo = new File("duenios.txt");	
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			String line;
			line = br.readLine();
			while (line != null) {
				String[] a = line.split(",");
				int cedula = Integer.parseInt(a[0]);
				String nombre = a[2];
				String apellido = a[1];
				VODuenio due = new VODuenio(cedula,nombre,apellido);
				try {
					certamenes.nuevoDuenio(due);
					System.out.println("INFO: SE AGREGO DUEÑO CON CEDULA: " + due.getCedula());
				} catch (DuenioException | PersistenciaException e) {
					e.printStackTrace();
				}
				line = br.readLine();
			}
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
