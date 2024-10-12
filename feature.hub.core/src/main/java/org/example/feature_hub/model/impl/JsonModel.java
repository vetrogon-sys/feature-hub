package org.example.feature_hub.model.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.feature_hub.model.Model;
import org.example.feature_hub.model.ModelField;

import java.util.List;

public class JsonModel extends Model<JsonNode> {
    public JsonModel(int id, List<ModelField> fields, JsonNode rootNode) {
        super(id, fields, rootNode);
    }

    public static class Builder {
        private int id;
        private List<ModelField> modelFields;
        private JsonNode rootNode;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder modelFields(List<ModelField> modelFields) {
            this.modelFields = modelFields;
            return this;
        }

        public Builder rootNode(JsonNode root) {
            this.rootNode = root;
            return this;
        }

        public JsonModel build() {
            return new JsonModel(id, modelFields, rootNode);
        }

    }
}
