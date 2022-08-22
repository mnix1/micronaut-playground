package com.example.book.view;

import java.util.List;
import java.util.UUID;

public interface BookView {
    UUID id();
    String name();
    Integer order();
    boolean available();
    Integer pages();
    List<String> comments();
}
