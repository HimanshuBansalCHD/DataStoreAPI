package org.example.util;

import org.bson.Document;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SnappyUtil {

    public static byte[] compressDocuments(List<Document> documents) throws IOException {
        byte[] uncompressed = documents.toString().getBytes();
        return Snappy.compress(uncompressed);
    }

    public static List<Document> decompressDocuments(byte[] compressed) throws IOException {
        byte[] uncompressed = Snappy.uncompress(compressed);
        String documentsString = new String(uncompressed);
        String[] documentStrings = documentsString.substring(1, documentsString.length() - 1).split(", ");
        List<Document> documents = new ArrayList<>();
        for (String documentString : documentStrings) {
            Document document = Document.parse(documentString);
            documents.add(document);
        }
        return documents;
    }
}