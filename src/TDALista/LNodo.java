package TDALista;

public class LNodo<E> implements Position<E>
{
	LNodo<E> prev;
	LNodo<E> next;
	E item;
	
	public LNodo(E elem, LNodo<E> prox, LNodo<E> ant)
	{
		prev = ant;
		next = prox;
		item = elem;
	}
	
	public E element()
	{
		return item;
	}
	
	public LNodo<E> getNext()
	{
		return next;
	}
	
	public LNodo<E> getPrev()
	{
		return prev;
	}
	
	public void setNext(LNodo<E> n)
	{
		next = n;
	}
	
	public void setPrev(LNodo<E> p)
	{
		prev = p;
	}
	
	public void setItem(E elem)
	{
		item = elem;
	}
}
