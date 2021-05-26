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

	public void mergesort(Punto a[],int izq, int der){
		if (izq < der){
				int m=(izq+der)/2;
				mergesort(a, izq, m);
				mergesort(a, m+1, der);                                                                                
				merge(a, izq, m, der);                                                                                 
		}
	}

	public void merge(Punto A[], int izq, int m, int der){
		int i, j, k;
		Punto [] B = new Punto[A.length]; //array auxiliar
		for (i=izq; i<=der; i++)      //copia ambas mitades en el array auxiliar                                       
			 B[i]=A[i];
	 
		i=izq; j=m+1; k=izq;
		  
		while (i<=m && j<=der) //copia el siguiente elemento mÃ¡s chico                                      
			   if (B[i].getY()<=B[j].getY())
				   A[k++]=B[i++];
			   else
				   A[k++]=B[j++];
			 
		while (i<=m)         //copia los elementos que quedan de la
			  A[k++]=B[i++]; //primera mitad (si los hay)
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
								// que se retorna la distancia mÃ¡xima
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(Double.MAX_VALUE);
		} else if (cantPuntos == 2) { // Si solo tenemos dos puntos, esa es la solucion
			p1 = puntos.first().element();
			p2 = puntos.last().element();
			toReturn.setP1(p1);
			toReturn.setP2(p2);
			toReturn.setDistancia(p1.distancia(p2));
		} else { // Si hay 3 o mÃ¡s puntos
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
			// Plano planoCentral = new Plano(); ESTO SI LO HACEMOS CON LISTA
			Punto pMedio = planoIzquierdo.getPuntos().last().element();

			// HAY QUE ORDENAR LA LISTA EN BASE A LAS COORDENADAS Y EN T(n log n)
			// HICE EL ARREGLO PARA HACER MERGE PERO PODEMOS VER SI HAY UNA FORMA MÃ�S EFICIENTE 
			// ORDENARIA LA LISTA DIRECTAMENTE PERO NO ESTABA CON MANEJOS DE TDA PARA HACERLO
			// ACLARACION: NO ESTOY SEGURA DEL ORDENAMIENTO. LO HICE QUE SE FIJE SOLAMENTE EN EL EJE Y. 
			// VER SI ESTA BIEN QUE SOLO SE FIJE EN Y (YA QUE PREVIAMENTE LOS PUNTOS ESTABAN OREDENADOS SEGUN EL EJE X)
			Punto a[]=obtenerArray();
			mergesort(a, 0, a.length);
			int i, j, count=0;
			Punto planoCentral[]= new Punto[a.length];
			

			// Creamos una nueva lista que contendra aquellos puntos que cumplan que la
			// diferencia en valor
			// absoluto de su componente x y la componente x del punto medio sea menor a la
			// distancia minima
			/*for (Position<Punto> p : puntos.positions()) {
				if (Math.abs(p.element().getX() - pMedio.getX()) < toReturn.getDistancia()) {
					planoCentral.addPunto(p.element());
				}
			}*/
			for(i=0; i<a.length;i++){
				if (Math.abs(a[i].getX() - pMedio.getX()) < toReturn.getDistancia()) {
					planoCentral[i]=a[i];
				}
			}

			//Calculamos las distancias
			double dist;
			for(i=0; i<planoCentral.length; i++){
				count=0;
				for(j=i+1; j<planoCentral.length && planoCentral[j]!=null &&count<7; j++){
					dist=planoCentral[i].distancia(planoCentral[j]);
					if(dist<toReturn.getDistancia()){
						toReturn.setDistancia(dist);
						toReturn.setP1(planoCentral[i]);
						toReturn.setP2(planoCentral[j]);
					}
					count++;
				}
			}
		}

		fin = new Date().getTime();
		toReturn.setTiempo(fin - ini);

		return toReturn;

	}

	private Punto[] obtenerArray(){
		Punto a[]=new Punto[puntos.size()];
		int i=0;
		for (Position<Punto> p : puntos.positions()) {
			a[i]=p.element();
			i++;
		}
		return a;
	}
}
