package obligatorio.persistencia.consultas;

public class Consultas {
	public String existeDuenio() {
		return "SELECT cedula FROM duenios WHERE cedula = ?";
	}
	
	public String insertDuenio() {
		return "INSERT INTO duenios (cedula, nombre, apellido) VALUES (?,?,?)";
	}
	
	public String listDuenios() {
		return "SELECT cedula, nombre, apellido from duenios";
	}
	
	public String cantMascotasXDuenio() {
		return "SELECT count(cedDuenio) as cant FROM mascotas WHERE cedDuenio = ?";
	}
	
	public String insertMascota() {
		return "INSERT INTO mascotas (numInscripcion, apodo, raza, cedDuenio) VALUES (?,?,?,?)";
	}
	
	public String listarMascotasDuenio() {
		return "SELECT numInscripcion, apodo, raza FROM mascotas WHERE cedDuenio = ? ORDER BY numInscripcion";
	}
	
	public String obtenerMascota() {
		return "SELECT apodo, raza FROM mascotas WHERE numInscripcion = ? AND cedDuenio = ?";
	}
	
	public String mascotaRegistrada() {
		return "SELECT numInscripcion FROM mascotas WHERE numInscripcion = ? AND cedDuenio = ?";
	}
	
	public String contarMascotas() {
		return "SELECT COUNT(*) AS cantidad FROM mascotas WHERE cedDuenio=? AND raza=?";
	}
	
	public String borrarDuenio() {
		return "DELETE FROM duenios WHERE cedula = ?";
	}
	
	public String borrarMascotas() {
		return "DELETE FROM mascotas WHERE cedDuenio = ?";
	}
	
	public String obtenerDuenio() {
		return "SELECT cedula, nombre, apellido FROM duenios WHERE cedula = ?";
	}
}
