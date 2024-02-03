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
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("status"),
            rs.getDate("created_at").toLocalDate(),
            rs.getInt("id_room"));
    }

    @Override
    public List<Device> findByRoom(int id_room) {
        List<Device> list = new ArrayList<>();
        try (Connection connection= dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM room WHERE device=?");
            statement.setInt(1, id_room);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM device WHERE id=?");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO device (name, type, status, created_at, id_room)VALUE (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreated_at()!= null ? Date.valueOf(device.getCreated_at()):null);
            statement.setInt(5, device.getId_room());

            statement.executeUpdate(); 
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            device.setId(rs.getLong(1));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Device device) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE device SET name=?, type=?, status=?, created_at=?, id_room=? WHERE id=?");
            
            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreated_at() != null ?Date.valueOf(device.getCreated_at()):null);
            statement.setInt(5, device.getId_room());
            statement.setLong(6, device.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = dataSource.getConnection();){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM device WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate()==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
