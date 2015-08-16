package com.ucampus.recommend;

import java.util.*;

public class SortMap
{
    public static boolean ASC = true;
    public static boolean DESC = false;

    public static List<Map<String, HashMap<String, Float>>> SortMapByRelevance(Map<String, HashMap<String, Float>> responseMap){

        System.out.println("After sorting descindeng order......");
        //Map<String, HashMap<String, Float>> sortedMapDesc = sortByComparator(responseMap, DESC);
        List<Map<String, HashMap<String, Float>>> sortedList = sortByComparator(responseMap, DESC);
        //printMap(sortedMapDesc);
        return sortedList;
    }


    public static List<Map<String, HashMap<String, Object>>> SortMapByRelevanceObject(Map<String, HashMap<String, Object>> responseMap){

        System.out.println("After sorting descindeng order......");
        //Map<String, HashMap<String, Float>> sortedMapDesc = sortByComparator(responseMap, DESC);
        List<Map<String, HashMap<String, Object>>> sortedList = sortByComparatorObject(responseMap, DESC);
        //printMap(sortedMapDesc);
        return sortedList;
    }



    private static List<Map<String, HashMap<String, Object>>> sortByComparatorObject(Map<String, HashMap<String, Object>> unsortMap, final boolean order)
    {

        List<Map.Entry<String, HashMap<String, Object>>> list = new LinkedList<Map.Entry<String, HashMap<String, Object>>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap<String, Object>>>() {
            public int compare(Map.Entry<String, HashMap<String, Object>> o1,
                               Map.Entry<String, HashMap<String, Object>> o2) {
                Float floatRelevence1 = (Float) o1.getValue().get("relevance");
                Float floatRelevence2 = (Float) o2.getValue().get("relevance");
                if (order) {

                    return floatRelevence1.compareTo(floatRelevence2);
                } else {
                    return floatRelevence2.compareTo(floatRelevence1);

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, HashMap<String, Object>> sortedMap = new LinkedHashMap<String, HashMap<String, Object>>();
        for (Map.Entry<String, HashMap<String, Object>> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        //return sortedMap;


        List<Map<String, HashMap<String, Object>>> sortedList = new ArrayList<Map<String, HashMap<String, Object>>>();
        for (Map.Entry<String, HashMap<String, Object>> entry : list)
        {
            Map<String, HashMap<String, Object>> tempMap = new HashMap<String, HashMap<String, Object>>();
            tempMap.put(entry.getKey(), entry.getValue());

            sortedList.add(tempMap);
        }
        return sortedList;

    }



    private static List<Map<String, HashMap<String, Float>>> sortByComparator(Map<String, HashMap<String, Float>> unsortMap, final boolean order)
    {

        List<Map.Entry<String, HashMap<String, Float>>> list = new LinkedList<Map.Entry<String, HashMap<String, Float>>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, HashMap<String, Float>>>() {
            public int compare(Map.Entry<String, HashMap<String, Float>> o1,
                               Map.Entry<String, HashMap<String, Float>> o2) {
                if (order) {
                    return o1.getValue().get("relevance").compareTo(o2.getValue().get("relevance"));
                } else {
                    return o2.getValue().get("relevance").compareTo(o1.getValue().get("relevance"));

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, HashMap<String, Float>> sortedMap = new LinkedHashMap<String, HashMap<String, Float>>();
        for (Map.Entry<String, HashMap<String, Float>> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        //return sortedMap;


        List<Map<String, HashMap<String, Float>>> sortedList = new ArrayList<Map<String, HashMap<String, Float>>>();
        for (Map.Entry<String, HashMap<String, Float>> entry : list)
        {
            Map<String, HashMap<String, Float>> tempMap = new HashMap<String, HashMap<String, Float>>();
            tempMap.put(entry.getKey(), entry.getValue());

            sortedList.add(tempMap);
        }
        return sortedList;

    }


    public static void printMap(Map<String, HashMap<String, Float>> map)
    {
        for (Map.Entry<String, HashMap<String, Float>> entry : map.entrySet())
        {
            System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
        }
    }

}
