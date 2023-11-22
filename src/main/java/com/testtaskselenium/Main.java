package com.testtaskselenium;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        List<Integer> arr1 = Arrays.asList(25, 28, 4, 78, 31, 4, 30, 12, 10, 13, 63, 54, 4, 19, 72, 97, 42, 4, 1, 36, 62, 90, 64, 94, 46, 41, 29, 32, 72, 76, 64, 45, 41, 55, 21, 39, 81, 31, 29, 77, 48, 7, 47, 77, 29, 47, 20, 45, 97, 22, 64, 22, 19, 40, 100, 22, 16, 25, 59, 99, 64, 90, 2, 44, 6, 36, 90, 16, 66, 68, 21, 25, 9, 60, 27, 69, 71, 25, 3, 49, 17, 12, 11, 2, 28, 40, 71, 20, 55, 22, 89, 34, 87, 29, 76, 47, 29, 96, 93, 9);
        Set<Integer> uniqueNum = arr1.stream()
                .collect(Collectors.toSet());
        System.out.println(uniqueNum);

        List<Integer> anotherWay =  arr1.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(anotherWay);
        System.out.println("\n\n*************************** Two lists with random values ***************************");
        // Generate the first list with 100 values
        int size1 = 100;
        int size2 = 200;
        int min = 0;
        int max = 123;

        List<Integer> list1 = new Random()
                .ints(size1, min, max)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("list1: "+ list1);

        // Generate the second list with 200 values
        List<Integer> list2 = new Random()
                .ints(size2, min, max)
                .boxed()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("List2: " + list2);

        // Unique values of the first list
        List<Integer> uniqueList1 = list1.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique values of the first list: " + uniqueList1);

        // Unique values of the first list that are not in the second list
        List<Integer> uniqueNotInList2 = list1.stream()
                .filter(value -> !list2.contains(value))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique values in the first list not in the second list: " + uniqueNotInList2);

        // Unique values of the second list that match the first list
        List<Integer> uniqueInList2MatchingList1 = list2.stream()
                .filter(value -> list1.contains(value))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique values in the second list matching the first list: " + uniqueInList2MatchingList1);

        // all unique values in both lists
        List<Integer> intersectingValues = list1.stream()
                .filter(list2::contains)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Values that present in both lists: " + intersectingValues);

        // all unique values that not repeat in both lists
        List<Integer> nonRepeatingValues = list1.stream()
                .filter(value -> list1.indexOf(value) == list1.lastIndexOf(value)
                        && list2.indexOf(value) == list2.lastIndexOf(value))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Values that not repeat in both lists: " + nonRepeatingValues);
        System.out.println("\n ********************** Some actions with json file *******************");
        workWithJson();
    }

    public static void workWithJson(){
        // Path to your JSON file
        String filePath = "C:\\MyFolder\\testTaskSelenium\\src\\main\\resources\\temp.json";

        try {
            // Read the JSON file
            JSONObject json = readJsonFile(filePath);

            // Task 1: Get all keys with value true
            assertStructure(json); // Assert the general structure
            System.out.print("Keys with true value: ");
            json.keySet().stream()
                    .filter(key -> json.get(key) instanceof Boolean && (boolean) json.get(key))
                    .forEach(System.out::print);

            // Task 2: Get keys whose values are lists
            assertStructure(json); // Assert the general structure
            json.keySet().stream()
                    .filter(key -> json.get(key) instanceof JSONArray)
                    .forEach(System.out::println);

            // Task 3: Add "key7" with value false
            assertStructure(json); // Assert the general structure
            json.put("key7", false);

            // Task 4: Update value of "key1" to "new_value1"
            assertStructure(json); // Assert the general structure
            json.put("key1", "new_value1");


            // Write the modified JSON back to the file
            //writeJsonToFile(json, filePath);
            System.out.println(json.toString(2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void assertStructure(JSONObject json) {
        // Add assertions for the expected structure
        assert json.has("key1") && json.get("key1") instanceof String;
        assert json.has("key2") && json.get("key2") instanceof Integer;
        assert json.has("key3") && json.get("key3") instanceof Boolean;
        assert json.has("key4") && json.get("key4") instanceof JSONObject;
        assert json.has("key5") && json.get("key5") instanceof JSONArray;
        assert json.has("key6") && json.get("key6") instanceof JSONObject;
    }

    private static JSONObject readJsonFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        return new JSONObject(jsonTokener);
    }
}

