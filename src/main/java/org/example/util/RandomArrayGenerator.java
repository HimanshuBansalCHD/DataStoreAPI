package org.example.util;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.ByteBuffer;
import java.util.Random;

public class RandomArrayGenerator {
    @Autowired
    Random rd;
    public byte[] generateByteArray(int size) {
        byte[] arr = new byte[size];
        rd.nextBytes(arr);
        return arr;
    }

    public int generateRandomAccountNumber() {
        int accountNumber;
        accountNumber = 1000000 + rd.nextInt();
        return accountNumber;
    }

}
