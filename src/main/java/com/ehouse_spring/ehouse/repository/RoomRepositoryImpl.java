package com.ehouse_spring.ehouse.repository;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehouse_spring.ehouse.entity.Room;
import com.ehouse_spring.ehouse.entity.repository.interfaces.RoomRepository;

@Repository
public class RoomRepositoryImpl implements RoomRepository{

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Room> findAll() {
        List<Room> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from room");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(sqlToRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Room sqlToRoom(ResultSet rs) throws SQLException{
        return new Room(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("created_at").toLocalDate(),
            rs.getInt("id_house")
        );
    }

    @Override
    public List<Room> findByHouse(String house) {
        throw new UnsupportedOperationException("Unimplemented method 'findByHouse'");
    }
    
    @Override
    public Room find(Integer id) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM room WHERE id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return sqlToRoom(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean persist(Room room) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO room (name, created_at, id_house) VALUE (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, room.getName());
            statement.setDate(2, room.getCreated_at()!= null ? Date.valueOf(room.getCreated_at()):null);
            statement.setInt(3, room.getId_house());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            room.setId(rs.getLong(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Room room) {
        try(Connection connection =dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE room SET name=?, created_at=?, id_house=? WHERE id=?");

            statement.setString(1, room.getName());
            statement.setDate(2, room.getCreated_at()!= null ? Date.valueOf(room.getCreated_at()):null);
            statement.setInt(3, room.getId_house());
            statement.setLong(4, room.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM room WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate() ==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
