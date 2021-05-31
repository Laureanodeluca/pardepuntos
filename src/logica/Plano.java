package logica;

import java.util.ArrayList;
import java.util.List;


public class Plano {

	private List<Punto> puntos;

	public Plano() {
		puntos = new ArrayList<Punto>();
	}

	public void addLast(Punto p) {
		puntos.add(p);
	}

	public List<Punto> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<Punto> listaPuntos) {
		puntos = listaPuntos;
	}

	public void mergesort(Punto a[], int izq, int der) {
		if (izq < der) {
			int m = (izq + der) / 2;
			mergesort(a, izq, m);
			mergesort(a, m + 1, der);
			merge(a, izq, m, der);
		}
	}

	public void merge(Punto A[], int izq, int m, int der) {
		int i, j, k;
		Punto[] B = new Punto[A.length]; // array auxiliar
		for (i = izq; i <= der; i++) // copia ambas mitades en el array auxiliar
			B[i] = A[i];

		i = izq;
		j = m + 1;
		k = izq;

		while (i <= m && j <= der) // copia el siguiente elemento mÃ¡s chico
			if (B[i].getY() <= B[j].getY())
				A[k++] = B[i++];
			else
				A[k++] = B[j++];

		while (i <= m) // copia los elementos que quedan de la
			A[k++] = B[i++]; // primera mitad (si los hay)
	}

	/**
	 * Obtiene el par de menor distancia. Chequea todos los pares de puntos.
	 * 
	 * @return Par de puntos de menor distancia.
	 */
	public Par menorDistanciaTodosLosPares() {
		Par toReturn = new Par();
		double menorDistancia = Double.MAX_VALUE;

		for (Punto p : puntos) {
			for (Punto q : puntos) {
				if ((p != q) && (p.distancia(q) < menorDistancia)) {
					menorDistancia = p.distancia(q);
					toReturn.setP1(p);
					toReturn.setP2(q);
					toReturn.setDistancia(menorDistancia);
				}
			}
		}
		
		return toReturn;
	}

	/**
	 * Obtiene el par de menor distancia. Divide el plano en dos.
	 * 
	 * @return Par de puntos de menor distancia.
	 */
	public Par menorDistanciaDivCon() {
		Par toReturn = new Par();
		int cantPuntos = puntos.size();

		Punto p1 = new Punto();
		Punto p2 = new Punto();

		if (cantPuntos < 2) { // Si tenemos menos de dos puntos no podemos obtener un par de puntos, por lo
								// que se retorna la distancia mÃ¡xima
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(Double.MAX_VALUE);
		} else if (cantPuntos == 2) { // Si solo tenemos dos puntos, esa es la solucion
			try{
				p1 = puntos.get(0); //primer elemento de la lista
				p2 = puntos.get(1); //ultimo elemento de la lista
				toReturn.setP1(p1);
				toReturn.setP2(p2);
				toReturn.setDistancia(p1.distancia(p2));
			}
			catch (IndexOutOfBoundsException e){
				System.out.println(e.getMessage());
			}
		} else { // Si hay 3 o mas puntos
			Par solIzq = new Par();
			Par solDer = new Par();
			Plano planoIzquierdo = new Plano();
			Plano planoDerecho = new Plano();

			subList(planoIzquierdo, planoDerecho);
			
			solIzq = planoIzquierdo.menorDistanciaDivCon();
			solDer = planoDerecho.menorDistanciaDivCon();
			
			// Se guarda la menor distancia entre las soluciuones obtenidas
			if (solIzq.getDistancia() < solDer.getDistancia()) 
				toReturn = solIzq;
			else 
				toReturn = solDer;
			

			// Creamos el plano central a partir de la franja entre los dos planos
			// anteriores
			Punto pMedio = null;
			try {
				pMedio = planoIzquierdo.getPuntos().get(planoIzquierdo.getPuntos().size()-1);
			} 
			catch (IndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
			}

			// Ordenamos los puntos respecto a la coordenada Y
			Punto a[] = obtenerArray();
			mergesort(a, 0, a.length-1);
			int i, j, count = 0, indPlanoCentral=0;
			Punto planoCentral[] = new Punto[a.length];
			
			for (i = 0; i < a.length; i++) {
				if (Math.abs(a[i].getX() - pMedio.getX()) < toReturn.getDistancia()) {
					planoCentral[indPlanoCentral] = a[i];
					indPlanoCentral++;
				}
			}
			
			// Calculamos las distancias
			double dist;
			for (i = 0; i < planoCentral.length && planoCentral[i] != null; i++) {
				count = 0;
				for (j = i + 1; j < planoCentral.length && planoCentral[j] != null && count < 7; j++) {
					dist = planoCentral[i].distancia(planoCentral[j]);
					if (dist < toReturn.getDistancia()) {
						toReturn.setDistancia(dist);
						toReturn.setP1(planoCentral[i]);
						toReturn.setP2(planoCentral[j]);
					}
					count++;
				}
			}
		}
		return toReturn;
	}

	private Punto[] obtenerArray() {
		Punto a[] = new Punto[puntos.size()];
		int i = 0;
		for (Punto p : puntos) {
				a[i] = p;
				i++;
		}
		return a;
	}

	private void subList(Plano planoIzquierdo, Plano planoDerecho) {
		planoIzquierdo.setPuntos(puntos.subList(0, Math.round(puntos.size()/2)));
		planoDerecho.setPuntos(puntos.subList(Math.round(puntos.size()/2)+1, puntos.size()));
		
	}

	/**
	 * Obtiene el par de menor distancia. Divide el plano en dos. Con ejes X e Y ordenados
	 * 
	 * @return Par de puntos de menor distancia.
	 */
	public Par menorDistanciaDivConOrdenado(ArrayList<Punto> puntosY) {
		Par toReturn = new Par();
		int cantPuntos = puntos.size();

		Punto p1 = new Punto();
		Punto p2 = new Punto();


			if (cantPuntos < 2) { 							// Si tenemos menos de dos puntos no podemos obtener un par de puntos, por lo
															// que se retorna la distancia mÃ¡xima
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(Double.MAX_VALUE);
		} else if (cantPuntos == 2) { 						// Si solo tenemos dos puntos, esa es la solucion
			p1 = puntos.get(0);
			p2 = puntos.get(1);
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(p1.distancia(p2));
		} else { 											// Si hay 3 o mas puntos
			Par solIzq = new Par();
			Par solDer = new Par();
			Plano planoIzquierdo = new Plano();
			Plano planoDerecho = new Plano();

			subList(planoIzquierdo, planoDerecho);
			
			ArrayList<Punto> ordenadoIzqY = new ArrayList<Punto>(puntosY);
			ArrayList<Punto> ordenadoDerY = new ArrayList<Punto>(puntosY);
			
			ordenadoIzqY.removeAll(planoDerecho.getPuntos());
			ordenadoDerY.removeAll(planoIzquierdo.getPuntos());
			
			solIzq = planoIzquierdo.menorDistanciaDivConOrdenado(ordenadoIzqY);
			solDer = planoDerecho.menorDistanciaDivConOrdenado(ordenadoDerY);
			
			// Se guarda la menor distancia entre las soluciuones obtenidas
			if (solIzq.getDistancia() < solDer.getDistancia()) 
				toReturn = solIzq;
			else 
				toReturn = solDer;
			
			// Creamos el plano central a partir de la franja entre los dos planos
			// anteriores
			Punto pMedio = null;
			try {
				pMedio = planoIzquierdo.getPuntos().get(planoIzquierdo.getPuntos().size()-1);
			} 
			catch (IndexOutOfBoundsException e) {System.out.println(e.getMessage());}

			

			// Creamos un nuevo arreglo que contendra aquellos puntos que cumplan que la
			// diferencia en valor
			// absoluto de su componente x y la componente x del punto medio sea menor a la
			// distancia minima
			int i, j, count = 0, indPlanoCentral=0;;
			Punto planoCentral[] = new Punto[puntosY.size()];
			for (i = 0; i < puntosY.size(); i++) {
				if ((puntosY.get(i) != null) && (Math.abs(puntosY.get(i).getX() - pMedio.getX()) < toReturn.getDistancia())) {
					planoCentral[indPlanoCentral] = puntosY.get(i);
					indPlanoCentral++;
				}
			}

			// Calculamos las distancias
			double dist;
			for (i = 0; i < planoCentral.length && planoCentral[i] != null; i++) {
				count = 0;
				for (j = i + 1; j < planoCentral.length && planoCentral[j] != null && count < 7; j++) {
					dist = planoCentral[i].distancia(planoCentral[j]);
					if (dist < toReturn.getDistancia()) {
						toReturn.setDistancia(dist);
						toReturn.setP1(planoCentral[i]);
						toReturn.setP2(planoCentral[j]);
					}
					count++;
				}
			}
		}
		return toReturn;
	}
}