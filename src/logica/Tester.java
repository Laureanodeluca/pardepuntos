package logica;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("unused")
public class Tester {
	public static void main(String args[])
	{
		Plano miPlano;
		Random r = new Random();
		long startTime;
		long endTime;
		
		System.out.println("============ INICIO DEL TEST: FUERZA BRUTA ==============");
		int i = 2;
		while (i <= Math.pow(2, 13))
		{
			System.out.println("     "+i+" puntos:");
			miPlano = new Plano();
			System.out.println("          Insertando puntos en el plano...");
			startTime = System.currentTimeMillis();
			for (int j = 0; j < i; j++)
			{
				Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble()), Math.abs(r.nextInt() + r.nextDouble()));
				miPlano.addPunto(p);
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la inserción. Tiempo: "+(endTime - startTime)+"ms");
			startTime = System.currentTimeMillis();
			Par menorDist = miPlano.menorDistanciaTodosLosPares();
			endTime = System.currentTimeMillis();
			
			System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
			System.out.println("          Distancia: " + menorDist.getDistancia());
			System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
			i = i*2;
		}
		
		System.out.println("============ INICIO DEL TEST: FUERZA DIVIDIR Y CONQUISTAR ===============");
		i = 2;
		while (i <= Math.pow(2, 20))
		{
			System.out.println("     "+i+" puntos:");
			miPlano = new Plano();
			System.out.println("          Insertando puntos en el plano...");
			startTime = System.currentTimeMillis();
			for (int j = 0; j < i; j++)
			{
				Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble()), Math.abs(r.nextInt() + r.nextDouble()));
				miPlano.addPunto(p);
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la inserción. Tiempo: "+(endTime - startTime)+"ms");

			startTime = System.currentTimeMillis();
			Par menorDist = miPlano.menorDistanciaDivCon();
			endTime = System.currentTimeMillis();
			
			System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
			System.out.println("          Distancia: " + menorDist.getDistancia());
			System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
			i = i*2;
		}		
	}
	
	public static String toString(Punto p)
	{
		return "("+p.getX()+", "+p.getY()+")";
	}
}