package com.ehouse_spring.ehouse.entity.repository.interfaces;

import java.util.List;

import com.ehouse_spring.ehouse.entity.Room;

public interface RoomRepository extends CrudRepository<Room>{
    List<Room> findByHouse(String house);
}