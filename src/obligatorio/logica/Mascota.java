package obligatorio.logica;

public class Mascota {
	private int numIsnc;
	private String apodo;
	private String raza;
	
	public Mascota(int numIsnc, String apodo, String raza) {
		super();
		this.numIsnc = numIsnc;
		this.apodo = apodo;
		this.raza = raza;
	}

	public int getNumIsnc() {
		return numIsnc;
	}

	public String getApodo() {
		return apodo;
	}

	public String getRaza() {
		return raza;
	}
}
