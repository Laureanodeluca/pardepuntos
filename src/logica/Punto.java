package logica;
import java.lang.Math;

public class Punto implements Comparable<Punto> 
{
	private double x;
	private double y;
	
	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Punto(){
		x=0;
		y=0;
	}

	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double distancia(Punto p)
	{
		return Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
	}

	@Override
	public int compareTo(Punto p2) {
		return 0;
	}
}
