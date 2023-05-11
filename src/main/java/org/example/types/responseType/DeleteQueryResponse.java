package org.example.types.responseType;

import lombok.Builder;

@Builder
public class DeleteQueryResponse {
    boolean isDeleted;

    int rowsEffected;
}
