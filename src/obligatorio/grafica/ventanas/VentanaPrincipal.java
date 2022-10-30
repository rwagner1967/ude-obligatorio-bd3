package obligatorio.grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import obligatorio.grafica.controladores.ControladorBorrarDuenio;
import obligatorio.grafica.controladores.ControladorContarMascotas;
import obligatorio.grafica.controladores.ControladorListadoDeMascotas;
import obligatorio.grafica.controladores.ControladorListadoDuenios;
import obligatorio.grafica.controladores.ControladorNuevaMascota;
import obligatorio.grafica.controladores.ControladorNuevoDuenio;
import obligatorio.grafica.controladores.ControladorObtenerMascota;
import obligatorio.logica.ICertamenes;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 7988368683410415113L;
	public static final String nomArch = "config/cliente-gui.properties";
	public ICertamenes certamenes = null;
	private JDesktopPane desk;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal("Certamenes");
					// window.pack();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal(String title) {
		super(title);
		connetToServer();

		intialize();
	}

	private void connetToServer() {
		Properties props = new Properties();
		String ruta = null;
		String msg = null;
		boolean hayError = false;
		try {
			props.load(new FileInputStream(nomArch));
			ruta = props.getProperty("ruta");
			this.certamenes = (ICertamenes) Naming.lookup(ruta);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			msg = "Error en la comunicación con el servidor";
			hayError = true;
		} catch (IOException e) {
			msg = "No se pudo leer archivo de propiedades";
			hayError = true;
		}
		if (hayError) {
			JOptionPane.showMessageDialog(null, msg, "Certamenes", JOptionPane.ERROR_MESSAGE);
			System.exit(ERROR);
		}

	}

	private void intialize() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		desk = new JDesktopPane();
		this.setContentPane(desk);
		this.setSize(600, 500);

		createMenuBar();
	}

	private void createMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Acciones");
		JMenuItem nuevoDuenio = new JMenuItem("Nuevo Dueño");
		JMenuItem nuevaMascota = new JMenuItem("Nueva Mascota");
		JMenuItem borrarDuenio = new JMenuItem("Borrar Dueño");
		JMenuItem listarDuenios = new JMenuItem("Listar Duenios");
		JMenuItem listarMascotasDuenio = new JMenuItem("Listar mascotas de un dueño");
		JMenuItem obtenerMascota = new JMenuItem("Obtener Mascota");
		JMenuItem contarMascotas = new JMenuItem("Contar Mascotas");
		this.setJMenuBar(mb);
		mb.add(menu);
		menu.add(nuevoDuenio);
		menu.add(nuevaMascota);
		menu.add(borrarDuenio);
		menu.add(listarDuenios);
		menu.add(listarMascotasDuenio);
		menu.add(contarMascotas);
		menu.add(obtenerMascota);

		nuevoDuenio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				NuevoDuenio nd = new NuevoDuenio();
				ControladorNuevoDuenio controlador = new ControladorNuevoDuenio(nd,certamenes);
				nd.setControlador(controlador);
				nd.setClosable(true);
				//nd.pack();
				nd.setVisible(true);
				desk.add(nd);
			}
		});
		
		nuevaMascota.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				NuevaMascota nm = new NuevaMascota();
				ControladorNuevaMascota controlador = new ControladorNuevaMascota(nm,certamenes);
				nm.setControlador(controlador);
				nm.setClosable(true);
				nm.setVisible(true);
				desk.add(nm);
			}
		});
		
		listarDuenios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ListarDuenios ld = new ListarDuenios();
				ControladorListadoDuenios controlador = new ControladorListadoDuenios(ld, certamenes);
				ld.setControlador(controlador);
				ld.setClosable(true);
				ld.setVisible(true);
				ld.setResizable(true);
				ld.setMaximizable(true);
				desk.add(ld);
			}
			
		});
		
		listarMascotasDuenio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ListarMascotaDeUnDuenio mascotas = new ListarMascotaDeUnDuenio();
				ControladorListadoDeMascotas controlador = new ControladorListadoDeMascotas(mascotas, certamenes);
				mascotas.setControlador(controlador);
				mascotas.setClosable(true);
				mascotas.setVisible(true);
				mascotas.setResizable(true);
				mascotas.setMaximizable(true);
				desk.add(mascotas);
			}
		});
		
		obtenerMascota.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ObtenerMascota mascota = new ObtenerMascota();
				ControladorObtenerMascota controlador = new ControladorObtenerMascota(mascota, certamenes);
				mascota.setControlador(controlador);
				mascota.setClosable(true);
				mascota.setVisible(true);
				desk.add(mascota);
			}
		});
		
		contarMascotas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ContarMascotas contar = new ContarMascotas();
				ControladorContarMascotas controlador = new ControladorContarMascotas(contar, certamenes);
				contar.setControlador(controlador);
				contar.setClosable(true);
				contar.setVisible(true);
				desk.add(contar);
			}
		});
		
		borrarDuenio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BorrarDuenio ventana = new BorrarDuenio();
				ControladorBorrarDuenio controlador = new ControladorBorrarDuenio(ventana, certamenes);
				ventana.setControlador(controlador);
				ventana.setClosable(true);
				ventana.setVisible(true);
				desk.add(ventana);
			}
			
		});
	}
}