package org.example.types.responseType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateQueryResponse {
    String Id;
    boolean IsSuccessful;
    String oldValue;
    String newValue;
    Object payload;

}
