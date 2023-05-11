package org.example.types;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerData {
    private String name;
    private int age;
}
