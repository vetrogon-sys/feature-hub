package org.example.feature_hub.service;

import org.example.feature_hub.model.Model;

public interface ModelParser<M extends Model<N>, N> {

    M parseModel(N node);

}
