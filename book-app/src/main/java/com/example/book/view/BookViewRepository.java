package com.example.book.view;

import java.util.List;

public interface BookViewRepository {
    List<? extends BookView> list();
    List<? extends BookView> filter(boolean available);
}
