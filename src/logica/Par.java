package logica;


public class Par{

    //Clase que guarda los datos de una solucion concreta
	
    private Punto p1; //Primer punto del par mas cercano
    private Punto p2; //Segundo punto del par mas cercano
    private double distancia; //Distancia entre los dos puntos de la solucion
    private long tiempo; //Tiempo requerido para hallar la opcion

    public Par(Punto p1, Punto p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}
    
    public Par()
    {
    	this.p1 = null;
    	this.p2 = null;
    }
    
    public Punto getP1() {
        return p1;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
    }

    public Punto getP2() {
        return p2;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public long getTiempo() {
        return tiempo;
    }
    
    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
} 