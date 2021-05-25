package TDALista;
import java.util.*;

public class DoubleLinkedList<E> implements PositionList<E>
{
	protected int tam;
	protected LNodo<E> first;
	protected LNodo<E> last;
	
	public DoubleLinkedList()
	{
		tam = 0;
		first = null;
		last = null;
	}
	
	private LNodo<E> checkPosition(Position<E> p) throws InvalidPositionException
	{
		if ((tam == 0) || (p == null))
			throw new InvalidPositionException("checkPosition(Position<E> p): Posicion Invalida o lista vacia. ");
		
		try
		{
			LNodo<E> n = (LNodo<E>) p;
			
			return n;
		}
		catch(ClassCastException e)
		{
			throw new InvalidPositionException("checkPosition(): la posicion no es un nodo.");
		}
	}
	
	public boolean isEmpty()
	{
		return (tam == 0);
	}
	
	public int size()
	{
		return tam;
	}
	
	public Position<E> first() throws EmptyListException
	{
		if (tam == 0)
			throw new EmptyListException("first(): lista vacia.");
		
		return first;
	}
	
	public Position<E> last() throws EmptyListException
	{
		if (tam == 0)
			throw new EmptyListException("last(): lista vacia.");
		
		return last;
	}
	
	public Position<E> next(Position<E> p) throws BoundaryViolationException, InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		if (n == last)
			throw new BoundaryViolationException("next(): la posicion pasada por parametro es la ultima de la lista.");

		return n.getNext();
	}
	
	public Position<E> prev(Position<E> p) throws BoundaryViolationException, InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		if (n == first)
			throw new BoundaryViolationException("prev(): la posicion pasada por parametro es la primera de la lista.");
		
		return n.getPrev();
	}
	
	public void addFirst(E p)
	{
		LNodo<E> nuevo = new LNodo<E> (p, null, null);
		
		if (tam == 0)
		{
			last = nuevo;
		}
		else
		{
			nuevo.setNext(first);
			first.setPrev(nuevo);
		}
		first = nuevo;
		tam++;
	}
	
	public void addLast(E p)
	{
		LNodo<E> nuevo = new LNodo<E>(p, null, null);
		if (tam == 0)
		{
			first = nuevo;
		}
		else
		{
			nuevo.setPrev(last);
			last.setNext(nuevo);
		}
		last = nuevo;
		tam++;
	}
	
	public void addBefore(Position<E> p, E item) throws InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		if (n == first)
		{
			LNodo<E> nuevo = new LNodo<E>(item, n, null);
			n.setPrev(nuevo);
			first = nuevo;
		}
		else
		{
			LNodo<E> anterior = n.getPrev();
			LNodo<E> nuevo = new LNodo<E>(item, n, anterior);
			anterior.setNext(nuevo);
			n.setPrev(nuevo);
		}
		tam++;
	}
	
	public void addAfter(Position<E> p, E item) throws InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		LNodo<E> nuevo = new LNodo<E>(item, null, null);
		
		if (n == last)
		{
			n.setNext(nuevo);
			nuevo.setPrev(n);
			last = nuevo;
		}
		else
		{
			LNodo<E> siguiente = n.getNext();
			siguiente.setPrev(nuevo);
			n.setNext(nuevo);
			nuevo.setPrev(n);
			nuevo.setNext(siguiente);
		}
		tam++;
	}

	public E remove(Position<E> p) throws InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		E aux = n.element();
		if (n == first)
		{
			
			first = n.getNext();
		}
		else
		{
			if (n == last)
			{
				last = n.getPrev();
			}
			else
			{
				LNodo<E> anterior = n.getPrev();
				LNodo<E> siguiente = n.getNext();
				
				anterior.setNext(siguiente);
				siguiente.setPrev(anterior);
			}
		}
		n = null;
		tam--;
		return aux;
	}
	
	
	
	public E set(Position<E> p, E item) throws InvalidPositionException
	{
		LNodo<E> n = checkPosition(p);
		E aux = n.element();
		n.setItem(item);
		return aux;
	}
	
	public Iterator<E> iterator()
	{
		return new ElementIterator<E>(this);
	}
	
	public Iterable<Position<E>> positions()
	{
		PositionList<Position<E>> aux = new DoubleLinkedList<Position<E>>();
		
		if(tam != 0)
		{
			Position<E> p = first;
			aux.addLast(p);
			try
			{
				while (p != last)
				{
					p = next(p);
					aux.addLast(p);
				}
			}
			catch (BoundaryViolationException | InvalidPositionException m)
			{
				System.out.println(m.getMessage());
			}
		}
		return aux;
	}
	
	public String toString()
	{
		String res = "";
		LNodo<E> n = first;
		
		if (n != null)
		{
			res = res + n.element();
			while (n != last)
			{
				n = n.getNext();
				res = res + n.element();
			}
		}
		return res;
	}
}