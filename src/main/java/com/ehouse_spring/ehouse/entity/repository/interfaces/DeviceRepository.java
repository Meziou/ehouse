package com.ehouse_spring.ehouse.entity.repository.interfaces;

import java.util.List;

import com.ehouse_spring.ehouse.entity.Device;
import com.ehouse_spring.ehouse.entity.repository.interfaces.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device>{
    List<Device> findByRoom(String room);
}
