package ConjeturaDeCollatz;

import java.math.BigInteger;

public class Hilo implements Runnable{
	private BigInteger bigRangoMinimo, bigRangoMaximo;
	private BigInteger numMasAlto        = new BigInteger("0"), numQueGeneraNumMasAlto = new BigInteger("0");
	private BigInteger contadorSecuencia = new BigInteger("0"), numQueGeneraSecuencia = new BigInteger("0");
	private String contenidoSecuencia = "";
	private Datos datos;
	
	public Hilo(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo, Datos datos) {
		this.bigRangoMinimo = bigRangoMinimo;
		this.bigRangoMaximo = bigRangoMaximo;
		this.datos = datos;
	}//Hilo()
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread());
		
		if (bigRangoMinimo.compareTo(BigInteger.ZERO)==0) {bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);}
		while(bigRangoMinimo.compareTo(bigRangoMaximo)<=0){
			BigInteger resultado  = bigRangoMinimo;
			BigInteger resultado2 = bigRangoMinimo;
			BigInteger contadorSecuenciaTemporal = new BigInteger("0");
			String contenidoSecuenciaTemporal = resultado+", ";
			//System.out.println("->"+resultado);
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
				if (resultado.compareTo(numMasAlto)>0) {
					numMasAlto = resultado;
					numQueGeneraNumMasAlto = resultado2;
				}
				contadorSecuenciaTemporal = contadorSecuenciaTemporal.add(BigInteger.ONE);
				contenidoSecuenciaTemporal += resultado+", ";
			}//while
			if (contadorSecuenciaTemporal.compareTo(contadorSecuencia)>0) {
				contadorSecuencia = contadorSecuenciaTemporal;
				numQueGeneraSecuencia = resultado2;
				contenidoSecuencia = contenidoSecuenciaTemporal;
			}else {
				contenidoSecuenciaTemporal = "";
			}
			//si encuentra un número que no termina en 1 se ejecuta el if, resultado guarda el ultimo número (siempre es 1)
			if (resultado.compareTo(BigInteger.ONE)!=0) datos.setNumEncontrados(bigRangoMinimo);;
			
			bigRangoMinimo = bigRangoMinimo.add(BigInteger.ONE);
		}//while
		
		datos.setInfoNumMasAlto(numMasAlto, numQueGeneraNumMasAlto);
		datos.setInfoSecuencia(contadorSecuencia, numQueGeneraSecuencia, contenidoSecuencia);
	}//run()
	
}//Hilo


























