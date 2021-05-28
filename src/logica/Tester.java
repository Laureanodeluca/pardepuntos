package logica;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import TDAColaConPrioridad.*;


@SuppressWarnings("unused")
public class Tester {
	public static void main(String args[])
	{
		Plano miPlano;
		Random r = new Random();
		long startTime;
		long endTime;
		PriorityQueue<Double,Punto> queueX;
		PriorityQueue<Double,Punto> queueY;
		
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
				miPlano.addLast(p);
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la inserci�n. Tiempo: "+(endTime - startTime)+"ms");
			startTime = System.currentTimeMillis();
			Par menorDist = miPlano.menorDistanciaTodosLosPares();
			endTime = System.currentTimeMillis();
			
			System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
			System.out.println("          Distancia: " + menorDist.getDistancia());
			System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
			i = i*2;
		}
		
		System.out.println("============ INICIO DEL TEST: FUERZA DIVIDIR Y CONQUISTAR ORDENADO ===============");
		i = 2;
		try 
		{
			while (i <= Math.pow(2, 20))
			{
				System.out.println("     "+i+" puntos:");
				miPlano = new Plano();
				queueX= new Heap<Double, Punto>(new DefaultComparator<Double>());
				queueY= new Heap<Double, Punto>(new DefaultComparator<Double>());
				System.out.println("          Insertando puntos en el plano...");
				startTime = System.currentTimeMillis();
				for (int j = 0; j < i; j++)
				{
					Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble()), Math.abs(r.nextInt() + r.nextDouble()));
					queueX.insert(p.getX(), p);
					queueY.insert(p.getY(), p);
				}
				int indiceY=0;
				Punto ordenadoY[]= new Punto[queueY.size()];
				while (!queueX.isEmpty()) {
					miPlano.addLast(queueX.removeMin().getValue());
					ordenadoY[indiceY]=queueX.removeMin().getValue();
					indiceY++;
				}
				endTime = System.currentTimeMillis();
				System.out.println("          Fin de la inserci�n. Tiempo: "+(endTime - startTime)+"ms");
	
				startTime = System.currentTimeMillis();
				Par menorDist = miPlano.menorDistanciaDivConOrdenado(ordenadoY);
				endTime = System.currentTimeMillis();
				
				System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
				System.out.println("          Distancia: " + menorDist.getDistancia());
				System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
				i = i*2;
			}
		}
		catch (InvalidKeyException | EmptyPriorityQueueException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		System.out.println("============ INICIO DEL TEST: FUERZA DIVIDIR Y CONQUISTAR ===============");
		i = 2;
		try 
		{
			while (i <= Math.pow(2, 20))
			{
				System.out.println("     "+i+" puntos:");
				miPlano = new Plano();
				queueX= new Heap<Double, Punto>(new DefaultComparator<Double>());
				System.out.println("          Insertando puntos en el plano...");
				startTime = System.currentTimeMillis();
				for (int j = 0; j < i; j++)
				{
					Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble()), Math.abs(r.nextInt() + r.nextDouble()));
					queueX.insert(p.getX(), p);
				}
				while (!queueX.isEmpty()) {
					miPlano.addLast(queueX.removeMin().getValue());
				}
				endTime = System.currentTimeMillis();
				System.out.println("          Fin de la inserci�n. Tiempo: "+(endTime - startTime)+"ms");
	
				startTime = System.currentTimeMillis();
				Par menorDist = miPlano.menorDistanciaDivCon();
				endTime = System.currentTimeMillis();
				
				System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
				System.out.println("          Distancia: " + menorDist.getDistancia());
				System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
				i = i*2;
			}
		}
		catch (InvalidKeyException | EmptyPriorityQueueException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
	}
	
	public static String toString(Punto p)
	{
		return "("+p.getX()+", "+p.getY()+")";
	}
}