package org.example.types;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FingerPrintPattern {
    private byte[] fingerPrintPattern;

}
