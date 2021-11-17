package ConjeturaDeCollatz;

import java.math.BigInteger;

public class Hilo implements Runnable{
	private BigInteger bigRangoMinimo;
	private BigInteger bigRangoMaximo;
	private Datos datos;
	private BigInteger numMaximo = new BigInteger("0");
	
	public Hilo(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo, Datos datos) {
		this.bigRangoMinimo = bigRangoMinimo;
		this.bigRangoMaximo = bigRangoMaximo;
		this.datos = datos;
	}//Hilo()
	
	public void run() {
		System.out.println(Thread.currentThread());
		
		if (bigRangoMinimo.compareTo(BigInteger.ZERO)==0) {bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);}
		while(bigRangoMinimo.compareTo(bigRangoMaximo)<0){
			BigInteger resultado = bigRangoMinimo;
			//System.out.println(resultado);
			while (!resultado.equals(BigInteger.valueOf(1))) {
				if (resultado.remainder(BigInteger.valueOf(2))==BigInteger.valueOf(0)) {
					// par -> resultado / 2
					resultado = resultado.divide(BigInteger.valueOf(2));
					//System.out.println(resultado);
				} else {
					// impar -> (resultado * 3) + 1
					resultado = resultado.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE);
					//System.out.println(resultado);
				}
				if (resultado.compareTo(numMaximo)>0) numMaximo = resultado;
			}//while
			bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);
		}//while
		
		datos.setNumMaximo(numMaximo);
	}//run()
	
	public BigInteger getDatosNumMaximo() {
		return datos.getNumMaximo();
	}

}//Hilo


























