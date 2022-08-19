package com.example.book.view;

import java.util.List;

interface BookViewRepository {
    List<? extends BookViewRecord> list();
    List<? extends BookViewRecord> filter(Boolean available);
}
