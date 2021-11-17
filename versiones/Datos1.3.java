package ConjeturaDeCollatz;

import java.math.BigInteger;

public class Datos {
	private BigInteger numMasAlto = new BigInteger("0"), numQueGeneraNumMasAlto = new BigInteger("0");
	private String numEncontrados = "";
	private BigInteger contadorSecuencia = new BigInteger("0"), numQueGeneraSecuencia = new BigInteger("0");
	private String contenidoSecuencia;
	
	//public Datos() {}
	
	public String getInfoNumMasAlto() {
		return numQueGeneraNumMasAlto+" -> "+numMasAlto;
	}
	
	public synchronized void setInfoNumMasAlto(BigInteger numMasAlto, BigInteger numQueGeneraNumMasAlto) {
		if (numMasAlto.compareTo(this.numMasAlto)>0) {
			this.numMasAlto = numMasAlto;
			this.numQueGeneraNumMasAlto = numQueGeneraNumMasAlto;
		}
	}
	
	public String getNumEncontrados() {
		if (numEncontrados.equals("")) return "No se ha encontrado ningún número";
		return numEncontrados;
	}
	
	public synchronized void setNumEncontrados(BigInteger numEncontrados) {
		//si encuentra algún numero que lo vaya añadiendo
		this.numEncontrados+= numEncontrados.toString()+", ";
	}
	
	public synchronized void setInfoSecuencia(BigInteger contadorSecuencia, BigInteger numQueGeneraSecuencia, String contenidoSecuencia) {
		if (contadorSecuencia.compareTo(this.contadorSecuencia)>0) {
			this.contadorSecuencia = contadorSecuencia;
			this.numQueGeneraSecuencia = numQueGeneraSecuencia;
			this.contenidoSecuencia = contenidoSecuencia;
		}
	}
	
	public String getInfoSecuencia() {
		return "Número "+numQueGeneraSecuencia+": Su secuencia es "+contadorSecuencia+" de larga.\n"+contenidoSecuencia.length();
	}
	
}//Datos
