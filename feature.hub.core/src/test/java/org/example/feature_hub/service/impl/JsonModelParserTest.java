package org.example.feature_hub.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.feature_hub.model.ModelField;
import org.example.feature_hub.model.impl.JsonModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest
class JsonModelParserTest {
    private static final Logger log = getLogger(JsonModelParserTest.class);

    @Autowired
    public JsonModelParser modelParser;


    public static Set<TestEntry> getTestData() {

        return Set.of(
              new TestEntry("test1", new JsonModel.Builder()
                    .id(1)
                    .modelFields(new ArrayList<>(List.of(
                          new ModelField("crawlers", new ArrayList<>(List.of(
                                new JsonModel.Builder()
                                      .id(1)
                                      .modelFields(new ArrayList<>(List.of(
                                            new ModelField("id", "site"),
                                            new ModelField("name", "SiteCrawler"),
                                            new ModelField("domain", "example.com"),
                                            new ModelField("url", "https://example.com"),
                                            new ModelField("mappingQuery", "[$=id|%=name] > /node > /node2"),
                                            new ModelField("totalUnits", 2d),
                                            new ModelField("units", new ArrayList<>(List.of(
                                                  new JsonModel.Builder()
                                                        .id(1)
                                                        .modelFields(new ArrayList<>(List.of(
                                                              new ModelField("name", "SiteCrawler@1/2"),
                                                              new ModelField("index", 0d),
                                                              new ModelField("limitRequests", 5d)
                                                        )))
                                                        .build(),
                                                  new JsonModel.Builder()
                                                        .id(1)
                                                        .modelFields(new ArrayList<>(List.of(
                                                              new ModelField("name", "SiteCrawler@2/2"),
                                                              new ModelField("index", 1d),
                                                              new ModelField("limitRequests", 5d)
                                                        )))
                                                        .build()
                                            ))),
                                            new ModelField("includeCountries", new ArrayList<>(List.of("US", "GB", "AT", "CA", "JA")))
                                      )))
                                      .build()
                          )))
                    )))
                    .build()
              )
        );
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void testParseMode(TestEntry entry) {

        JsonModel jsonModel = modelParser.parseModel(readTestNode(entry.filename));

        assertNotNull(jsonModel);
        assertEquals(entry.expected, jsonModel);
    }

    private JsonNode readTestNode(String fileName) {
        try (InputStream is = JsonModelParserTest.class.getResourceAsStream("/parsers/%s.json".formatted(fileName))) {
            ObjectMapper om = new ObjectMapper();
            return om.readTree(is);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    record TestEntry(String filename, JsonModel expected) {};
}