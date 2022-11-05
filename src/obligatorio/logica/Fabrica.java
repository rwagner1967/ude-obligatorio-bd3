package obligatorio.logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import obligatorio.logica.excepciones.PersistenciaException;
import obligatorio.persistencia.FabricaAbstracta;

/* 
 *  Clase auxiliar para guardar la fabrica definida
 *  en el archivo de parametros.
 *  Implementada como singleton.
 */
public class Fabrica {
	private static Fabrica fabrica;
	private FabricaAbstracta fabrica_abstracta;
	private Fabrica(FabricaAbstracta fab) {
		fabrica_abstracta=fab;
	}
	
	public static synchronized Fabrica getInstance() throws PersistenciaException {
		if (fabrica == null) {
			fabrica = leerArchivoDeconfiguracion();
		}
		return fabrica;
	}

	public FabricaAbstracta getFabrica() {
		return fabrica_abstracta;
	}
	private static Fabrica leerArchivoDeconfiguracion() throws PersistenciaException {
		Fabrica fabrica = null;
		String nomFab = null;
		Properties p = new Properties();
		String nomArchivo = "config/datos.properties";

		try {
			p.load(new FileInputStream(nomArchivo));
			nomFab = p.getProperty("nomfab");
			FabricaAbstracta fabAbstracta = (FabricaAbstracta) Class.forName(nomFab).getDeclaredConstructor().newInstance();
			fabrica = new Fabrica(fabAbstracta);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("error de acceso a datos");
		}
		return fabrica;
	}
}
