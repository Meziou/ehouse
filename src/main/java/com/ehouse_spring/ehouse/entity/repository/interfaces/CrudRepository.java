package com.ehouse_spring.ehouse.entity.repository.interfaces;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T find (Integer id);
    boolean persist(T entity);
    boolean update(T entity);
    boolean delete(Integer id);
}
