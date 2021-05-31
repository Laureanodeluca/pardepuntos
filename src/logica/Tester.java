package logica;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

@SuppressWarnings("unused")
public class Tester {
	public static void main(String args[])
	{
		Plano miPlano;
		Random r = new Random();
		long startTime;
		long endTime;
		PriorityQueue<Punto> queueX;
		PriorityQueue<Punto> queueY;
		
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
				Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9), Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9));
				miPlano.addLast(p);
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la insercion. Tiempo: "+(endTime - startTime)+"ms");
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
			
			queueX= new PriorityQueue<Punto>(i, new PuntoComparator<Punto>() {
				public int compare(Punto p1, Punto p2) {
					if (p1.getX() < p2.getX())
						return -1;
					else
						if (p1.getX() > p2.getX())
							return 1;
						else
							return 0;
				}
			});
			
			System.out.println("          Insertando puntos en el plano...");
			startTime = System.currentTimeMillis();
			for (int j = 0; j < i; j++)
			{
				Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9), Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9));
				queueX.add(p);
			}
			while (!queueX.isEmpty()) {
				miPlano.addLast(queueX.poll());
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la insercion. Tiempo: "+(endTime - startTime)+"ms");

			startTime = System.currentTimeMillis();
			Par menorDist = miPlano.menorDistanciaDivCon();
			endTime = System.currentTimeMillis();
			
			System.out.println("          Par de menor distancia: "+ toString(menorDist.getP1()) + "; " + toString(menorDist.getP2()));
			System.out.println("          Distancia: " + menorDist.getDistancia());
			System.out.println("          Tiempo: " + (endTime - startTime) + "ms\n");
			i = i*2;
		}
		
		System.out.println("============ INICIO DEL TEST: FUERZA DIVIDIR Y CONQUISTAR ORDENADO ===============");
		i = 2;
		while (i <= Math.pow(2, 20))
		{
			System.out.println("     "+i+" puntos:");
			miPlano = new Plano();
			queueX= new PriorityQueue<Punto>(i, new PuntoComparator<Punto>() {
				public int compare(Punto p1, Punto p2) {
					if (p1.getX() < p2.getX())
						return -1;
					else
						if (p1.getX() > p2.getX())
							return 1;
						else
							return 0;
				}
			});
			
			queueY= new PriorityQueue<Punto>(i, new PuntoComparator<Punto>() {
				public int compare(Punto p1, Punto p2) {
					if (p1.getY() < p2.getY())
						return -1;
					else
						if (p1.getY() > p2.getY())
							return 1;
						else
							return 0;
				}
			});
			
			System.out.println("          Insertando puntos en el plano...");
			startTime = System.currentTimeMillis();
			for (int j = 0; j < i; j++)
			{
				Punto p = new Punto(Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9), Math.abs(r.nextInt() + r.nextDouble())%Math.pow(10, 9));
				queueX.add(p);
				queueY.add(p);
			}
			int indiceY=0;
			Punto ordenadoY[]= new Punto[queueY.size()];
			while (!queueX.isEmpty()) {
				miPlano.addLast(queueX.poll());
				ordenadoY[indiceY]=queueY.poll();
				indiceY++;
			}
			endTime = System.currentTimeMillis();
			System.out.println("          Fin de la insercion. Tiempo: "+(endTime - startTime)+"ms");

			ArrayList<Punto> l = new ArrayList<Punto>(Arrays.asList(ordenadoY));
			startTime = System.currentTimeMillis();
			Par menorDist = miPlano.menorDistanciaDivConOrdenado(l);
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