package com.github.kuzznya.titantest.model;

import java.util.List;

public interface CsvResult {

    List<Object> getData();

    default String getDataAsString() {
        StringBuilder builder = new StringBuilder();
        for (Object value : this.getData())
            builder.append(", ").append(value.toString());
        builder.delete(0, 2);
        return builder.toString();
    }
}
