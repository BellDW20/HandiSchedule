package hs.math;
import java.util.ArrayList;

public class CartesianProduct {
	
	public static <T> ArrayList<ArrayList<T>> convertToSingletons(ArrayList<T> a) {
		ArrayList<ArrayList<T>> singletons = new ArrayList<ArrayList<T>>();
		for(int i=0; i<a.size(); i++) {
			ArrayList<T> singleton = new ArrayList<T>();
			singleton.add(a.get(i));
			singletons.add(singleton);
		}
		return singletons;
	}
	
	public static <T> ArrayList<ArrayList<T>> cartesianProduct(ArrayList<ArrayList<T>> a, ArrayList<T> b) {
		ArrayList<ArrayList<T>> sets = new ArrayList<ArrayList<T>>();
		
		for(int i=0; i<a.size(); i++) {
			for(int j=0; j<b.size(); j++) {
				ArrayList<T> set = new ArrayList<T>();
				set.addAll(a.get(i));
				set.add(b.get(j));
				sets.add(set);
			}
		}
		
		return sets;
	}
	
	public static <T> ArrayList<ArrayList<T>> cartesianProduct(ArrayList<ArrayList<T>> a) {
		ArrayList<ArrayList<T>> sets = convertToSingletons(a.get(0));
		
		for(int i=1; i<a.size(); i++) {
			sets = cartesianProduct(sets, a.get(i));
		}
		
		return sets;
	}

}
