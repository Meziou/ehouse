package com.ehouse_spring.ehouse.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehouse_spring.ehouse.entity.Device;
import com.ehouse_spring.ehouse.entity.repository.interfaces.DeviceRepository;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository{
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Device> findAll(){
        List<Device> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("Select * from device");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(sqlToDevice(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private Device sqlToDevice(ResultSet rs) throws SQLException{
        return new Device(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("status"),
            rs.getDate("createDateTime").toLocalDate(),
            rs.getString("room"));
    }

    @Override
    public List<Device> findByRoom(String room) {
        List<Device> list = new ArrayList<>();
        try (Connection connection= dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from room where device=?");
            statement.setString(1, room);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(sqlToDevice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public Device find(Integer id) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from device where id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return sqlToDevice(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean persist(Device device) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO device (name, type, status, createDateTime, room)VALUE (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreateDateTime()!= null ? Date.valueOf(device.getCreateDateTime()):null);
            statement.setString(5, device.getRoom());

            statement.executeUpdate(); 
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            device.setId(rs.getInt(1));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Device device) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE device SET name=?, type=?, status=?, createDateTime=?, room=? WHERE id=?");
            
            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreateDateTime() != null ?Date.valueOf(device.getCreateDateTime()):null);
            statement.setString(5, device.getRoom());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM device WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate()==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
