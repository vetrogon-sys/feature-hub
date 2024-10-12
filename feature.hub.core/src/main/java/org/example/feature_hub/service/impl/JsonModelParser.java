package org.example.feature_hub.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.example.feature_hub.model.Model;
import org.example.feature_hub.model.ModelField;
import org.example.feature_hub.model.impl.JsonModel;
import org.example.feature_hub.service.ModelParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JsonModelParser implements ModelParser<JsonModel, JsonNode> {

    @Override
    public JsonModel parseModel(JsonNode node) {

        List<ModelField> fields = new ArrayList<>();
        node.fields()
              .forEachRemaining(jsonField -> {
                  fields.addAll(getModelFieldsFromNode(jsonField));
              });


        return new JsonModel.Builder()
              .id(1)
              .modelFields(fields)
              .rootNode(node)
              .build();
    }

    private List<ModelField> getModelFieldsFromNode(Map.Entry<String, JsonNode> jsonField) {
        List<ModelField> fields = new ArrayList<>();
        String name = jsonField.getKey();

        JsonNode val = jsonField.getValue();

        JsonNodeType fieldType = val.getNodeType();
        if (fieldType == JsonNodeType.ARRAY) {
            List<Object> innerFields = new ArrayList<>();
            for (JsonNode node : val) {
                innerFields.add(getModelParameterFromNode(node));
            }
            fields.add(new ModelField(name, innerFields));
        } else {
            fields.add(new ModelField(name, getModelParameterFromNode(val)));
        }

        return fields;
    }

    private Object getModelParameterFromNode(JsonNode node) {
        JsonNodeType nodeType = node.getNodeType();
        if (nodeType == JsonNodeType.OBJECT) {
            return parseModel(node);
        } else if (nodeType == JsonNodeType.NUMBER) {
            return node.doubleValue();
        } else if (nodeType == JsonNodeType.STRING) {
            return node.textValue();
        } else if (nodeType == JsonNodeType.ARRAY) {
            List<Object> inner = new ArrayList<>();
            for (JsonNode innerNode : node) {
                inner.add(getModelParameterFromNode(innerNode));
            }
            return inner;
        }
        return null;
    }
}
