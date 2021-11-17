package ConjeturaDeCollatz;

import java.math.BigInteger;

public class Datos {
	private BigInteger numMasAltoAlcanzado = new BigInteger("0"), numQueGeneraNumMasAltoAlcanzado = new BigInteger("0");
	private String numQueNoTerminanEn421 = "";
	private BigInteger contadorSecuencia = new BigInteger("0"), numQueGeneraSecuencia = new BigInteger("0");
	private String contenidoSecuencia;
	
	//public Datos() {}
	
	public String getInfoNumMasAlto() {
		return numQueGeneraNumMasAltoAlcanzado+" -> "+numMasAltoAlcanzado;
	}
	
	public synchronized void setInfoNumMasAlto(BigInteger numMasAltoAlcanzado, BigInteger numQueGeneraNumMasAltoAlcanzado) {
		if (numMasAltoAlcanzado.compareTo(this.numMasAltoAlcanzado)>0) {
			this.numMasAltoAlcanzado = numMasAltoAlcanzado;
			this.numQueGeneraNumMasAltoAlcanzado = numQueGeneraNumMasAltoAlcanzado;
		}
	}
	
	public String getNumQueNoTerminanEn421() {
		if (numQueNoTerminanEn421.equals("")) return "No se ha encontrado ningún número";
		return numQueNoTerminanEn421;
	}
	
	public synchronized void setNumQueNoTerminanEn421(BigInteger numEncontrados) {
		//si encuentra algún numero que lo vaya añadiendo
		this.numQueNoTerminanEn421+= numEncontrados.toString()+", ";
	}
	
	public synchronized void setInfoSecuencia(BigInteger contadorSecuencia, BigInteger numQueGeneraSecuencia, String contenidoSecuencia) {
		if (contadorSecuencia.compareTo(this.contadorSecuencia)>0) {
			this.contadorSecuencia = contadorSecuencia;
			this.numQueGeneraSecuencia = numQueGeneraSecuencia;
			this.contenidoSecuencia = contenidoSecuencia;
		}
	}
	
	public String getInfoSecuencia() {
		return "Número "+numQueGeneraSecuencia+": Su secuencia es "+contadorSecuencia+" de larga.\n"+contenidoSecuencia;
	}
	
}//Datos
