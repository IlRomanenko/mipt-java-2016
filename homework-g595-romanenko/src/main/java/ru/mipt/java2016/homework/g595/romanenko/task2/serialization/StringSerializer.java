package ru.mipt.java2016.homework.g595.romanenko.task2.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ru.mipt.java2016.homework.g595.romanenko.task2.serialization
 *
 * @author Ilya I. Romanenko
 * @since 26.10.16
 **/
public class StringSerializer implements SerializationStrategy<String> {

    private static final StringSerializer STRING_SERIALIZER = new StringSerializer();

    private static final IntegerSerializer INTEGER_SERIALIZER = IntegerSerializer.getInstance();

    private static final int BUFFER_SIZE = 10 * 1024 + 100;
    private static final byte[] BUFFER = new byte[BUFFER_SIZE];


    public static StringSerializer getInstance() {
        return STRING_SERIALIZER;
    }

    @Override
    public void serializeToStream(String s, OutputStream outputStream) throws IOException {
        INTEGER_SERIALIZER.serializeToStream(s.length(), outputStream);
        outputStream.write(s.getBytes());
    }

    @Override
    public int getBytesSize(String s) {
        return s.length() +
                INTEGER_SERIALIZER.getBytesSize(s.length()); //for serialize length of string
    }

    @Override
    public String deserializeFromStream(InputStream inputStream) throws IOException {
        int length = INTEGER_SERIALIZER.deserializeFromStream(inputStream);
        inputStream.read(BUFFER, 0, length);
        return new String(BUFFER, 0, length);
    }
}