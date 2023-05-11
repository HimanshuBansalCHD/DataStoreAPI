package org.example.types;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FacialFeatures {
    private byte[] facialFeature;
}
