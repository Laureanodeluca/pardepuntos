package logica;

public class Tester {
	public static void main(String args[])
	{
		Plano miPlano = new Plano();
		
		Punto p1 = new Punto(1, 6);
		Punto p2 = new Punto(2, 9);
		Punto p3 = new Punto(7, 16);
		Punto p4 = new Punto(11, 4);
		Punto p5 = new Punto(5, 10);
		Punto p6 = new Punto(6, 1);
		Punto p7 = new Punto(8, 4);
		Punto p8 = new Punto(8, 13);
		Punto p9 = new Punto(12, 12);
		Punto p10 = new Punto(13, 16);

		
		miPlano.addPunto(p1);
		miPlano.addPunto(p2);
		miPlano.addPunto(p3);
		miPlano.addPunto(p4);
		miPlano.addPunto(p5);
		miPlano.addPunto(p6);
		miPlano.addPunto(p7);
		miPlano.addPunto(p8);
		miPlano.addPunto(p9);
		miPlano.addPunto(p10);

		Punto[] menorDist = miPlano.menorDistanciaTodosLosPares();
		System.out.println("Par de menor distancia: "+ toString(menorDist[0]) + "; " + toString(menorDist[1]));
		System.out.println("Distancia: " + menorDist[0].distancia(menorDist[1]));
	}
	
	public static String toString(Punto p)
	{
		return "("+p.getX()+", "+p.getY()+")";
	}
}