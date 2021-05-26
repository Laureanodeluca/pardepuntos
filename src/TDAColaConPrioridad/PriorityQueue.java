package TDAColaConPrioridad;

public interface PriorityQueue<K,V>
{
	/**
	 * Consulta la cantidad de elementos de la Cola con prioridad.
	 * @return cantidad de elementos de la cola con prioridad.
	 */
	public int size();
	
	/**
	 * Verifica que la cola este vacia.
	 * @return verdadero si la cola esta vacia. Falso si no.
	 */
	public boolean isEmpty();
	
	/**
	 * Devuelve la entrada con menor prioridad de la cola.
	 * @return Entrada con menor prioridad.
	 * @throws EmptyPriorityQueueException si la cola esta vacia.
	 */
	public Entry<K,V> min() throws EmptyPriorityQueueException;
	
	/**
	 * Inserta un par clave-valor y devuelve la entrada creada.
	 * @param key Clave de la entrada a insertar.
	 * @param value Valor de la entrada a insertar.
	 * @return nueva entrada creada.
	 * @throws InvalidKeyException si la clave es invalida.
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException;
	
	/**
	 * Remueve y devuelve la entrada con menor prioridad de la cola.
	 * @return Entrada con menor prioridad.
	 * @throws EmptyPriorityQueueException si la cola esta vacia.
	 */
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException;
}
