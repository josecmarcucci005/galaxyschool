package com.galaxyschool.db;

import java.util.List;

public interface Dao<T> {

    T get(String name);

    List<T> getAll();

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}
