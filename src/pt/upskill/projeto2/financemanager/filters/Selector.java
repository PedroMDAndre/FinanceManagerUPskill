package pt.upskill.projeto2.financemanager.filters;

/**
 * @author POO 2014
 * <p>
 * ...
 */
public interface Selector<T> {
	public boolean isSelected(T item);
}
