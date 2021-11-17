
/* 1. El usuario introducir� la semilla m�nima y la m�xima que han de ser exploradas (que han de ser n�meros enteros positivos),
 * 	  y el n�mero de hilos que han de emplearse para probar todos los n�meros entre ambas.
 *    
 * 2. El programa crear� todos los hilos solicitados por el usuario, y �stos empezar�n a probar todos los n�meros del intervalo introducido
 *    para ver si desembocan en el ciclo 4 � 2 � 1 o no. Todos los n�meros que no terminan en dicho ciclo (no deber�a salir ninguno).
 *    Al terminar de examinarse todos, el programa muestra el tiempo de proceso y los n�meros que no desembocan en el n�mero 1.
 *    La salida ha de ser similar a lo siguiente:
 *    Tiempo de proceso: XXXX milisegundos.
 *    N�meros que no terminan en el n�mero 1: <ninguno>
 *    
 * 3. Al terminar de examinarse todas las semillas, el programa muestra, adem�s, el n�mero m�s alto alcanzado y las secuencias m�s largas registradas.
 *    La salida ha de ser similar a lo siguiente:
 *    Tiempo de proceso: XXXX milisegundos.
 *    Secuencias m�s largas:
 *    XX, XX, XX, XX, XX, XX, 4, 2, 1
 *    XX, XX, XX, XX, XX, XX, 4, 2, 1
 *    Su longitud es: XX
 *    El n�mero m�s alto alcanzado es: XXXXX
 *    N�meros que no terminan en el n�mero 1: <ninguno>
 *    
 * 4. El usuario puede elegir entre dos modos de reparto de tarea entre los hilos: o bien cada hilo solicita un n�mero a la clase de datos cada vez,
 *    o bien cada hilo recibe inicialmente un subintervalo del total de semillas y ya no vuelve a solicitar m�s.
 *    
 * 5. El programa implementa un sistema para que, si un n�mero ya ha sido explorado con anterioridad, no se sigue haciendo. Por ejemplo, si un hilo
 *    ya obtuvo la secuencia del 11 y a otro le toca la semilla 17, no tendr� que calcular la secuencia porque la secuencia del 17 es un subconjunto de
 *    la del 11.
 *    Indicaci�n: ser�a una buena idea, siguiendo el ejemplo anterior, guardar un array en el que el contenido es el siguiente al �ndice. As�, en dicho
 *    array el �ndice 17 ser�a el n�mero 52, que es el siguiente. El �ndice 52 ser�a el n�mero 26, y as� sucesivamente. El problema es que un array solamente
 *    puede tener �ndices de tipo int, y necesitaremos guardar long. Por esa raz�n, conviene utilizar la clase HashMap para manejar claves de tipo long.
 *    
 * 6. Realiza al menos 32 ejecuciones del programa (al menos 4 ejecuciones con cada combinaci�n de par�metros), explorando al menos 100000 n�meros, variando
 *    los siguientes par�metros:
 *    a) Revisando y no revisando los n�meros para no seguir secuencias que ya han sido exploradas.
 *    b) Pidiendo las semillas de una en una o pidi�ndolas en grupos.
 *    c) Usando 1 hilo y usando 4 hilos.
 *    Aseg�rate de que los resultados son los mismos y analiza las diferencias en tiempos,  explic�ndolas. Guarda el an�lisis en un archivo PDF.
 *    
 * 7. En lugar de long, se utiliza la clase BigInteger, que permite manejar n�meros m�s altos que 2^63
 */

package ConjeturaDeCollatz;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Principal {
    private static Datos datos = new Datos();

    public static void main(String[] args) {
            iniciarPrograma();
    }//main()

    private static void iniciarPrograma() {
        long tiempoInicial = System.currentTimeMillis();
        BigInteger bigRangoMinimo = new BigInteger("0");
        BigInteger bigRangoMaximo = new BigInteger("100000");
        int numHilos = 4;

        Hilo   h   = null;
        Thread t[] = new Thread[numHilos];

        int modo = 0; // 0 = por rango, 1 = uno a uno
        if(modo == 0) {
            System.out.println("METODO = por rangos");
            calcularYMandarRangosACadaHilo(bigRangoMinimo, bigRangoMaximo, numHilos,h , t);
        }
    // como en la clase Hilo modo = 0 a no ser que sea modo = 1 no hace falta que se lo especifiquemos
        else if (modo == 1) {
            System.out.println("METODO = n�mero a n�mero");
            pedirNumeroPorNumero(bigRangoMinimo, bigRangoMaximo, numHilos, modo, h, t);
        }
        else System.out.println("Elecion erronea...");

        System.out.println("\nN�mero m�s alto------------------> "+datos.getInfoNumMasAlto()+"\n");
        System.out.println("N�mero que no terminan en 4-2-1--> "+datos.getNumQueNoTerminanEn421()+"\n");
        System.out.println(datos.getInfoSecuencia());
        System.out.println("\nTiempo de proceso----------------> "+(System.currentTimeMillis() - tiempoInicial)+" milisegundos");
        //System.out.println("\n"+datos.getHashMap());
    }//iniciarPrograma()

    private static void pedirNumeroPorNumero(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo, int numHilos, int modo, Hilo h, Thread t[]){
        datos.setRangoMinimoyMaximo(bigRangoMinimo, bigRangoMaximo);
        for (BigInteger i = new  BigInteger("0"); i.compareTo(BigInteger.valueOf(numHilos))<0 ; i = i.add(BigInteger.valueOf(1))){
            h = new Hilo(modo, datos);
            t[i.intValue()] = new Thread(h);
            t[i.intValue()].start();
        }//for
        esperarHilos(t);

}// pedirNumeroPorNumero()

    private static void calcularYMandarRangosACadaHilo(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo, int numHilos, Hilo h, Thread t[]){
        //Esto en general lo que hace es enviar los rangos de cada hilo.
        BigInteger RangoMinimoProvisional = bigRangoMinimo;
        BigInteger numeroPorHilos = NumeroPorHilos(bigRangoMinimo, bigRangoMaximo, numHilos);
        for (BigInteger i = new  BigInteger("0"); i.compareTo(BigInteger.valueOf(numHilos))<0 ; i = i.add(BigInteger.valueOf(1))){
            //Si i = al ultimo hilo asignamos directamente el rango maximo, si no asignamos el rango maximo provisional calculado por el metodo
            if (i.compareTo(BigInteger.valueOf(numHilos-1))==0){
                System.out.println(RangoMinimoProvisional+"  "+bigRangoMaximo);
                h = new Hilo(RangoMinimoProvisional, bigRangoMaximo, datos);
            }else{
                BigInteger RangoMaximoProvisional = RangoMaximoDeCadaHilo(RangoMinimoProvisional, bigRangoMaximo, numeroPorHilos);
                System.out.println(RangoMinimoProvisional+"  "+RangoMaximoProvisional);
                h = new Hilo(RangoMinimoProvisional, RangoMaximoProvisional, datos);
                RangoMinimoProvisional = RangoMaximoProvisional.add(BigInteger.valueOf(1));
            }
            t[i.intValue()] = new Thread(h);
            t[i.intValue()].start();
        }//for
        esperarHilos(t);
    }// introducirRango()

    private static BigInteger NumeroPorHilos(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo,int numHilos){
        //Este metodo devuelve un biginteger del numero de hilos que se administrara a cadea hilo. Es decir si tebgo 100 numeros y 10 Hilos, devolvera 10. Que son 10 numeros por cada Hilo
        BigInteger cantidadNumeros =bigRangoMaximo.subtract(bigRangoMinimo);
        cantidadNumeros.add(BigInteger.valueOf(1));
        BigDecimal dividendo = new BigDecimal(cantidadNumeros);
        BigDecimal divisor = new BigDecimal(numHilos);
        // Aqui dividimos el dividendo entre el divisor y guardamos en un BigInteger aproximando por HALF_UP que viene siendo al numero mas cercano. Ej 5,5, -> 6 5,2 -> 5
        BigDecimal resultadoDivision = dividendo.divide(divisor,2,RoundingMode.CEILING);
        resultadoDivision = resultadoDivision.setScale(0, RoundingMode.HALF_UP);
        BigInteger numeroPorHilos = resultadoDivision.toBigInteger();

        return numeroPorHilos;
    }// BigInteger()

    private static BigInteger RangoMaximoDeCadaHilo(BigInteger bigRangoMinimo, BigInteger bigRangoMaximo,BigInteger numeroPorHilos){
        BigInteger RangoMaximoProvisional = bigRangoMinimo;
        //Hacemos un for hasta la cantidad de numeros por hilo y vamos sumandoselo al rango maximo provisional
        for(BigInteger i = new  BigInteger("1");i.compareTo(numeroPorHilos)<0 ; i = i.add(BigInteger.valueOf(1))){
            if(RangoMaximoProvisional.compareTo(bigRangoMaximo)< 0)
                RangoMaximoProvisional = RangoMaximoProvisional.add(BigInteger.valueOf(1));
        } 
        return RangoMaximoProvisional;
     }// rangosDeCadaHilo()

    private static void esperarHilos(Thread[] t) {
        for (int i = 0; i < t.length; ++i) {
            //esperamos a que termine la ejecuci�n de los hilos
            try {
                    t[i].join();
            } catch (InterruptedException e) {System.out.println(e.getMessage()+" Interrumpido");}
        }
    }//esperarHilos()
}//Principal