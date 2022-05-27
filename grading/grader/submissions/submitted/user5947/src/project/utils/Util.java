package project.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import project.airport_container.Airport;

public class Util {

	public static double findDistance(Airport thisAirport, Airport toAirport) {
		return Math.sqrt(Math.pow(thisAirport.getX() - toAirport.getX(), 2) + Math.pow(thisAirport.getY() - toAirport.getY(), 2));
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }  
}
