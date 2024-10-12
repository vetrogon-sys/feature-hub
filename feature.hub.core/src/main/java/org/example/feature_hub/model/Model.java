package org.example.feature_hub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public abstract class Model<T> {

    protected final int id;
    protected final List<ModelField> fields;

    protected transient final T rootNode;
    protected Model(int id, List<ModelField> fields, T rootNode) {
        this.id = id;
        this.fields = fields;
        this.rootNode = rootNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model<?> model)) {
            return false;
        }

        if (id != model.id) {
            return false;
        }
        return Objects.equals(fields, model.fields);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }
}
