import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		Map<Object, Integer> aMap = new HashMap<>();
		Map<Object, Integer> bMap = new HashMap<>();
		countElementsFrequency(aMap, a);
		countElementsFrequency(bMap, b);
		int result = 0;
		for(Object o: aMap.keySet()){
			if(aMap.get(o).equals(bMap.get(o))){
				result++;
			}
		}
		return result;
	}

	private static void countElementsFrequency(Map<Object, Integer> map, Collection a){
		for (Object curr : a){
			if(!map.containsKey(curr)){
				map.put(curr, 0);
			}
			map.put(curr, map.get(curr) + 1);
		}
	}
}
