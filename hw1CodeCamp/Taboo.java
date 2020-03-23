
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {

	private Map<T, Set<T>> map = new HashMap<>();
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		for (int i = 0; i < rules.size() - 1; i++) {
			T curr = rules.get(i);
			T next = rules.get(i + 1);
			addElemToMap(curr, next);
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(map.containsKey(elem)){
			return map.get(elem);
		}
		 return Collections.emptySet();
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for(int i = 0; i < list.size() - 1; i++){
			T curr = list.get(i);
			T next = list.get(i + 1);
			if(curr != null && next != null) {
				if(map.get(curr) != null) {
					if (map.get(curr).contains(next)) {
						list.remove(i + 1);
						i--;
					}
				}
			} else{
				if(curr == null){
					list.remove(i);
					i--;
				}
			}
		}
	}

	private void addElemToMap(T key, T value){
		if(!map.containsKey(key)){
			map.put(key, new HashSet<>());
		}
		map.get(key).add(value);
	}
}
