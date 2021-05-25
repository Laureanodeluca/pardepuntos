package logica;
import TDALista.*;

public class Plano 
{
	private PositionList<Punto> puntos;
	
	public Plano() {
		puntos = new DoubleLinkedList<Punto>();
	}
	
	/**
	 * Inserta un punto en la lista de puntos, ordenado por el eje x. 
	 * @param p Punto a insertar.
	 */
	public void addPunto(Punto p) {
		insertarOrdenado(p);
	}
	
	private void insertarOrdenado(Punto p) {
		boolean insertado = false;
		for (Position<Punto> q: puntos.positions())
		{
			try {
				if (q.element().getX() >= p.getX())
				{
					insertado = true;
					puntos.addBefore(q, p);
					break;
				}
			}
			catch (InvalidPositionException e)
			{
				System.out.println(e.getMessage());
			}
		}
		if (!insertado)
			puntos.addLast(p);
	}
	
	public PositionList<Punto> getPuntos() {
		return puntos;
	}
	
	/**
	 * Obtiene el par de menor distancia. Chequea todos los pares de puntos.
	 * @return Par de puntos de menor distancia.
	 */
	public Punto[] menorDistanciaTodosLosPares() {
		Punto[] toReturn = (Punto []) new Punto[2]; 
		double menorDistancia = Double.MAX_VALUE;
		for (Position<Punto> p: puntos.positions())
		{
			for (Position<Punto> q: puntos.positions())
			{
				if ((p.element() != q.element()) && (p.element().distancia(q.element()) < menorDistancia))
				{
					menorDistancia = p.element().distancia(q.element());
					toReturn[0] = p.element();
					toReturn[1] = q.element();
				}	
			}
		}
		return toReturn;
	}
	
	/**
	 * Obtiene el par de menor distancia. Divide el plano en dos. 
	 * @return Par de puntos de menor distancia.
	 */
	public Punto[] menorDistanciaDividirPlano() {
		PositionList<Punto> ladoIzquierdo = new DoubleLinkedList<Punto>();
		PositionList<Punto> ladoDerecho = new DoubleLinkedList<Punto>();
		int i = 1;
		for (Position<Punto> p: puntos.positions())
		{
			if (i <= puntos.size()/2)
				ladoIzquierdo.addLast(p.element());
			else
				ladoDerecho.addLast(p.element());
			i++;
		}
		return MenorDistanciaDividirPlano(ladoIzquierdo, ladoDerecho);
	}
	
	private Punto[] MenorDistanciaDividirPlano(PositionList<Punto> ladoIzquierdo, PositionList<Punto> ladoDerecho) {
		return null;
	}
}
