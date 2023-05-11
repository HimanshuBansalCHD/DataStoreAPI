package org.example.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class BankDetails {
    private int accountNumber;
    @Setter
    private int accountBalance;

}
