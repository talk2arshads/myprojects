package com.in28minutes.springboot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.in28minutes.springboot.model.Person;

public class NormalForToJava8ForLoop {

    public static void main(String[] args) {

        final NormalForToJava8ForLoop forloop = new NormalForToJava8ForLoop();
        forloop.listForEachWithLambadaExamples();
        forloop.mapForEachWithLambadaExamples();

        final List<Person> persons = Arrays.asList(new Person("mkyong", 30), new Person("jack", 20), new Person("lawrence", 40));

        final Person result = NormalForToJava8ForLoop.getStudentByName(persons, "jack");
        System.out.println(result);

    }

    private void listForEachWithLambadaExamples() {

        final List<String> items = new ArrayList<>();
        items.add("A");
        items.add("B");
        items.add("C");
        items.add("D");
        items.add("E");

        // lambda
        // Output : A,B,C,D,E
        items.forEach(item -> System.out.println(item));

        // Output : C
        items.forEach(item -> {
            if ("C".equals(item)) {
                System.out.println(item);
            }
        });

        // method reference
        // Output : A,B,C,D,E
        items.forEach(System.out::println);

        // Stream and filter
        // Output : B
        items.stream().filter(s -> s.contains("B")).forEach(System.out::println);
    }

    private void mapForEachWithLambadaExamples() {

        final Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);

        items.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));

        items.forEach((k, v) -> {
            System.out.println("Item : " + k + " Count : " + v);
            if ("E".equals(k)) {
                System.out.println("Hello E");
            }
        });
    }

    private void streamsFilterWithLambadaExamples() {

        final List<Person> persons = Arrays.asList(new Person("mkyong", 30), new Person("jack", 20), new Person("lawrence", 40));

        final Person result1 = persons.stream() // Convert to steam
                .filter(x -> "jack".equals(x.getName())) // we want "jack" only
                .findAny() // If 'findAny' then return found
                .orElse(null); // If not found, return null

        System.out.println(result1);

        final Person result2 = persons.stream().filter(x -> "ahmook".equals(x.getName())).findAny().orElse(null);

        System.out.println(result2);

    }

    private static Person getStudentByName(List<Person> persons, String name) {

        Person result = null;
        for (final Person temp : persons) {
            if (name.equals(temp.getName())) {
                result = temp;
            }
        }
        return result;
    }

}
