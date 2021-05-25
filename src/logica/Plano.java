package logica;

import java.util.*;
import TDALista.*;

public class Plano {

	private PositionList<Punto> puntos;

	public Plano() {
		puntos = new DoubleLinkedList<Punto>();
	}

	/**
	 * Inserta un punto en la lista de puntos, ordenado por el eje x.
	 * 
	 * @param p Punto a insertar.
	 */
	public void addPunto(Punto p) {
		insertarOrdenado(p);
	}

	private void insertarOrdenado(Punto p) {
		boolean insertado = false;
		for (Position<Punto> q : puntos.positions()) {
			try {
				if (q.element().getX() >= p.getX()) {
					insertado = true;
					puntos.addBefore(q, p);
					break;
				}
			} catch (InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
		}
		if (!insertado)
			puntos.addLast(p);
	}

	public PositionList<Punto> getPuntos() {
		return puntos;
	}

	public void setPuntos(PositionList<Punto> listaPuntos) {
		puntos = listaPuntos;
	}

	/**
	 * Obtiene el par de menor distancia. Chequea todos los pares de puntos.
	 * 
	 * @return Par de puntos de menor distancia.
	 */
	public Par menorDistanciaTodosLosPares() {
		Par toReturn = new Par();
		long ini, fin;
		double menorDistancia = Double.MAX_VALUE;

		ini = new Date().getTime();

		for (Position<Punto> p : puntos.positions()) {
			for (Position<Punto> q : puntos.positions()) {
				if ((p.element() != q.element()) && (p.element().distancia(q.element()) < menorDistancia)) {
					menorDistancia = p.element().distancia(q.element());
					toReturn.setP1(p.element());
					toReturn.setP2(q.element());
					toReturn.setDistancia(menorDistancia);
				}
			}
		}
		fin = new Date().getTime();
		toReturn.setTiempo(fin - ini);

		return toReturn;
	}

	/**
	 * Obtiene el par de menor distancia. Divide el plano en dos.
	 * 
	 * @return Par de puntos de menor distancia.
	 */
	public Par menorDistanciaDivCon() {
		Par toReturn = new Par();
		long ini, fin;
		double menorDistancia = Double.MAX_VALUE;
		int cantPuntos = puntos.size();

		Punto p1 = new Punto();
		Punto p2 = new Punto();

		ini = new Date().getTime();

		if (cantPuntos < 2) { // Si tenemos menos de dos puntos no podemos obtener un par de puntos, por lo
								// que se retorna la distancia máxima
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(Double.MAX_VALUE);
		} else if (cantPuntos == 2) { // Si solo tenemos dos puntos, esa es la solucion
			p1 = puntos.first().element();
			p2 = puntos.last().element();
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(p1.distancia(p2));
		} else { // Si hay 3 o más puntos
			Par solIzq = new Par();
			Par solDer = new Par();
			Plano planoIzquierdo = new Plano();
			Plano planoDerecho = new Plano();

			int mitad = puntos.size() / 2; // Se divide el plano en dos mitades y se encuentra solucion al plano
											// izquierdo y al plano derecho
			planoIzquierdo.setPuntos(puntos.subList(0, mitad));
			planoDerecho.setPuntos(puntos.subList(mitad + 1, cantPuntos));
			// int i = 1;
			// for (Position<Punto> p: puntos.positions())
			// {
			// if (i <= puntos.size()/2)
			// ladoIzquierdo.addLast(p.element());
			// else
			// ladoDerecho.addLast(p.element());
			// i++;
			// }

			solIzq = planoIzquierdo.menorDistanciaDivCon();
			solDer = planoDerecho.menorDistanciaDivCon();

			if (solIzq.getDistancia() < solDer.getDistancia()) { // Se guarda la menor distancia entre las soluciuones
																	// obtenidas
				toReturn = solIzq;
			} else {
				toReturn = solDer;
			}

			// Creamos el plano central a partir de la franja entre los dos planos
			// anteriores
			Plano planoCentral = new Plano();
			Punto pMedio = planoIzquierdo.getPuntos().last().element();

			// HAY QUE ORDENAR LA LISTA EN BASE A LAS COORDENADAS Y

			// NO ENTIENDO BIEN EL SENTIDO DE ORDENAR POR Y, VOY A VER MAÑANA

			// Creamos una nueva lista que contendra aquellos puntos que cumplan que la
			// diferencia en valor
			// absoluto de su componente x y la componente x del punto medio sea menor a la
			// distancia minima
			/*
			for (Position<Punto> p : puntos.positions()) {
				if (Math.abs(p.element().getX() - pMedio.getX()) < toReturn.getDistancia()) {
					planoCentral.addPunto(p.element());
				}
			}
			*/

		}

		fin = new Date().getTime();
		toReturn.setTiempo(fin - ini);

		return toReturn;

	}
}
