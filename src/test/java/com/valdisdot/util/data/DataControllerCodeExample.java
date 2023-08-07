package com.valdisdot.util.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdisdot.util.data.controller.ConvertingDataController;
import com.valdisdot.util.data.controller.DataController;
import com.valdisdot.util.data.controller.RawDataController;
import com.valdisdot.util.data.element.Naming;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataControllerCodeExample {
    public static void main(String[] args) {
        //experiment1();
        experiment2();
    }

    static void experiment1(){
        //data cell group
        Naming<String> dataCellGroup = new Naming<>(
                Map.of(
                        "cell1", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
                        "cell2", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
                        "cell3", new DataCell<>(UUID.randomUUID()::toString, (s) -> {})
                )
        );
        //converter from Map to JsonNode
        ObjectMapper mapper = new ObjectMapper();
        Function<Map<String, String>, JsonNode> convertFunction = (map) -> {
            try {
                return mapper.readTree(mapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        //consumer
        Consumer<JsonNode> consumer = (node) -> System.out.println(node.get("cell2"));
        DataController dataController = new ConvertingDataController<>(dataCellGroup, convertFunction, consumer);
        dataController.process();
        //Output:
        //"8ee42de2-ba37-41db-a723-4b8475a990a9"
    }

    static void experiment2(){
        Naming<String> dataCellGroup = new Naming<>(
                Map.of(
                        "cell1", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
                        "cell2", new DataCell<>(UUID.randomUUID()::toString, (s) -> {}),
                        "cell3", new DataCell<>(UUID.randomUUID()::toString, (s) -> {})
                )
        );
        DataController dataController = new RawDataController<>(dataCellGroup, System.out::println);
        //simple Map printing
        dataController.process();
    }
}