package ConjeturaDeCollatz;

import java.math.BigInteger;

public class Datos {
	private BigInteger numMaximo;
	
	public BigInteger getNumMaximo() {
		return numMaximo;
	}
	
	public synchronized void setNumMaximo(BigInteger numMaximo) {
		this.numMaximo = numMaximo;
	}
	
}//Datos
