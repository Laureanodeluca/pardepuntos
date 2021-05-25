package TDALista;
import java.util.*;
public class ElementIterator<E> implements Iterator<E>
{
	PositionList<E> list;
	Position<E> cursor;
	
	public ElementIterator(PositionList<E> l)
	{
		list = l;
		
			try
			{
				cursor = list.first();
			}
			catch (EmptyListException m)
			{
				cursor = null;
			}
	}
	
	public boolean hasNext()
	{
		return cursor != null;
	}
	
	public E next() throws NoSuchElementException
	{
		if (cursor == null)
			throw new NoSuchElementException("next(): elemento invalido.");
		
		try
		{
			E aux = cursor.element();
			
			if (cursor == list.last())
				cursor = null;
			else
			{
				cursor = list.next(cursor);
			}
			
			return aux;
		}
		catch (BoundaryViolationException | InvalidPositionException | EmptyListException m)
		{
			System.out.println(m.getMessage());
			return null;
		}
	}
			
}

