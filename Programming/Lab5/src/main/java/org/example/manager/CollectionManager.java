package org.example.manager;

import org.example.model.Route;
import org.example.util.IdGenerator;

import java.time.LocalDate;
import java.util.*;

public class CollectionManager {
    private LinkedHashMap<Integer, Route> collection;
    private LocalDate initializationDate;

    public CollectionManager(){
        this.collection = new LinkedHashMap<>();
        this.initializationDate = LocalDate.now();
    }

    public boolean checkKey(Integer id){
        return collection.containsKey(id);
    }

    public boolean insert(Integer id, Route route){
        if (collection.putIfAbsent(id, route) == null){
            IdGenerator.compareMaxId(route.getId());
            return true;
        }
        return false;
    }

    public boolean update(Integer id, Route route){
        if (collection.containsKey(id)){
            collection.replace(id, route);
            return true;
        }
        return false;
    }

    public boolean removeByKey(Integer id){
        if (collection.containsKey(id)){
            collection.remove(id);
            return true;
        }
        return false;
    }

    public void clear(){
        collection.clear();
    }

    public Map<Integer, Route> show(){
        return Collections.unmodifiableMap(collection);
    }

    public int removeGreater(Route route){
        int sizeBefore = collection.size();
//        Iterator<Map.Entry<Integer, Route>> iterator = collection.entrySet().iterator();
//
//        while(iterator.hasNext()){
//            Map.Entry<Integer, Route> entry = iterator.next();                               вайб темка на понимание
//
//            if (entry.getValue().compareTo(route) > 0){
//                iterator.remove();
//            }
//        }
        collection.entrySet().removeIf(entry -> entry.getValue().compareTo(route) > 0);
        return sizeBefore - collection.size();
    }

    public boolean replaceIfLower(Integer id, Route route){
        if (collection.containsKey(id)){
            if (collection.get(id).compareTo(route) > 0){
                collection.replace(id, route);
                return true;
            }
            return false;
        }
        return false;
    }

    public int removeGreaterKey(Integer id){
        int sizeBefore = collection.size();
        collection.entrySet().removeIf(entry -> entry.getKey().compareTo(id) > 0);
        return sizeBefore - collection.size();
    }

    public double averageOfDistance(){
        double sum = 0f;
        for (Route r: collection.values()){
            sum += r.getDistance();
        }
        if (collection.isEmpty()){
            return 0.0;
        }
        return sum/collection.size();
    }

    public int countByDistance(double distance){
        int counter = 0;
        for (Route r: collection.values()){
            if (Math.abs(distance - r.getDistance()) < 0.00000001){
                counter += 1;
            }
        }
        return counter;
    }

    public ArrayList<Route> filterLessThanDistance(double distance){
        ArrayList<Route> distances = new ArrayList<>();
        for (Route route: collection.values()){
            if (route.getDistance() < distance){
                distances.add(route);
            }
        }
        return distances;
    }

    public void info(){
        System.out.println("Collection type: LinkedHashMap");
        System.out.println("Size: " + collection.size());
        System.out.println("Initialization date: " + initializationDate);
    }
}