package TDAColaConPrioridad;
import java.util.*;

public class Heap<K,V> implements PriorityQueue<K,V>
{
	protected Entrada<K,V> [] items;
	protected Comparator<K> comp;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public Heap(Comparator<K> c)
	{
		items = (Entrada<K,V> []) new Entrada[1048576];
		comp = c;
		size = 0;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size==0;
	}
	
	public Entry<K,V> min() throws EmptyPriorityQueueException
	{
		if (size == 0)
			throw new EmptyPriorityQueueException("min(): cola vacia.");
		
		return items[1]; //no se usa el espacio 0.
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException
	{
		if (key == null)
			throw new InvalidKeyException("Llave invalida.");
		
		Entrada<K,V> entrada = new Entrada<K,V>(key, value);
		size++;
		
		if (size >= items.length)
			duplicate();
		
		items[size] = entrada;
		int i = size;
		
		boolean seguir = true;
		
		while ((i > 1) && (seguir))
		{
			Entrada<K,V> elemActual = items[i];
			Entrada<K,V> padre = items[i/2];
			if (comp.compare(elemActual.getKey(), padre.getKey()) < 0)
			{
				Entrada<K,V> aux = items[i];
				items[i] = padre;
				items[i/2] = aux;
				i = i/2;
			}
			else
				seguir = false;
		}
		return entrada;
	}
	
	private void duplicate()
	{
		@SuppressWarnings("unchecked")
		Entrada<K,V>[] aux = (Entrada<K,V>[]) new Entrada[(items.length)*2];
		
		for (int i = 1; i < items.length; i++)
		{
			aux[i] = items[i];
		}
		items = aux;
		
	}
	
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException
	{
		if (size == 0)
			throw new EmptyPriorityQueueException("removeMin(): cola vacia.");
		
		Entry<K,V> min = items[1];
		
		if (size == 1)
		{
			items[1] = null;
			size = 0;
		}
		else
		{
			items[1] = items[size];
			items[size] = null;
			size--;
			
			boolean seguir = true;
			int i = 1;
			
			while (seguir)
			{
				int hijoizq = i*2;
				int hijoder = (i*2)+1;
				
				boolean tieneizq = (hijoizq <= size);
				boolean tieneder = (hijoder <= size);
				
				if (!tieneizq)
					seguir = false;
				else
				{
					int m;
					
					if (tieneder)
					{
						if (comp.compare(items[hijoizq].getKey(), items[hijoder].getKey()) < 0)
							m = hijoizq;
						else
							m = hijoder;
						
					}
					else
					{
						m = hijoizq;
					}
					
					if (comp.compare(items[m].getKey(), items[i].getKey())<0)
					{
						Entrada<K,V> aux = items[i];
						items[i] = items[m];
						items[m] = aux;
						i = m;
					}
					else
						seguir = false;
				}
			}
		}
		return min;
	}
}