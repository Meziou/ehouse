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

    /**
 * Returns a list of all rooms in the database.
 *
 * @return a list of all rooms in the database
 */
@Override
public List<Room> findAll() {
    List<Room> list = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
        // create a prepared statement to execute the SQL query
        PreparedStatement statement = connection.prepareStatement("SELECT * from room");
        // execute the query and retrieve the results
        ResultSet rs = statement.executeQuery();
        // loop through the results and add each room to the list
        while (rs.next()) {
            list.add(sqlToRoom(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    private Room sqlToRoom(ResultSet rs) throws SQLException {
    /**
     * This method maps a SQL result set to a Room object.
     *
     * @param rs the SQL result set
     * @return a Room object
     * @throws SQLException if an error occurs while processing the SQL result set
     */
    return new Room(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDate("created_at").toLocalDate(),
        rs.getInt("id_house")
    );
}

/**
 * Returns a list of all rooms in the database that belong to the specified house.
 *
 * @param id_house the ID of the house
 * @return a list of all rooms in the database that belong to the specified house
 */
@Override
public List<Room> findByHouse(int id_house) {
    List<Room> list = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
        // create a prepared statement to execute the SQL query
        PreparedStatement statement = connection.prepareStatement("SELECT * from room WHERE id_house=?");
        // set the first parameter of the query
        statement.setInt(1, id_house);
        // execute the query and retrieve the results
        ResultSet rs = statement.executeQuery();
        // loop through the results and add each room to the list
        while (rs.next()) {
            list.add(sqlToRoom(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    /**
 * Returns the room with the specified ID, or null if no room with the specified ID exists.
 *
 * @param id the ID of the room to be retrieved
 * @return the room with the specified ID, or null if no room with the specified ID exists
 */
@Override
public Room find(Integer id) {
    try (Connection connection = dataSource.getConnection()) {
        // create a prepared statement to execute the SQL query
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM room WHERE id=?");
        // set the first parameter of the query
        statement.setInt(1, id);
        // execute the query and retrieve the results
        ResultSet rs = statement.executeQuery();
        // check if there is a result
        if (rs.next()) {
            // map the SQL result to a Room object
            return sqlToRoom(rs);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    /**
 * This method persists a room object to the database.
 *
 * @param room the room object to be persisted
 * @return true if the room was successfully persisted, false otherwise
 */
@Override
public boolean persist(Room room) {
    try (Connection connection = dataSource.getConnection()) {
        // create a prepared statement to execute the SQL query
        PreparedStatement statement = connection.prepareStatement("INSERT INTO room (name, created_at, id_house) VALUE (?,?,?)", Statement.RETURN_GENERATED_KEYS);

        // set the parameters of the prepared statement
        statement.setString(1, room.getName());
        statement.setDate(2, room.getCreated_at() != null ? Date.valueOf(room.getCreated_at()) : null);
        statement.setInt(3, room.getId_house());

        // execute the prepared statement
        statement.executeUpdate();

        // retrieve the generated key
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();

        // set the ID of the room object
        room.setId(rs.getLong(1));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    /**
 * This method updates a room object in the database.
 *
 * @param room the room object to be updated
 * @return true if the room was successfully updated, false otherwise
 */
@Override
public boolean update(Room room) {
    try (Connection connection = dataSource.getConnection()) {
        // create a prepared statement to execute the SQL query
        PreparedStatement statement = connection.prepareStatement("UPDATE room SET name=?, created_at=?, id_house=? WHERE id=?");

        // set the parameters of the prepared statement
        statement.setString(1, room.getName());
        statement.setDate(2, room.getCreated_at() != null ? Date.valueOf(room.getCreated_at()) : null);
        statement.setInt(3, room.getId_house());
        statement.setLong(4, room.getId());

        // execute the prepared statement
        return statement.executeUpdate() == 1;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    /**
 * Deletes a room from the database based on the given room ID.
 *
 * @param id the ID of the room to be deleted
 * @return true if the room was successfully deleted, false otherwise
 */
@Override
public boolean delete(Integer id) {
    try (Connection connection = dataSource.getConnection()) {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM room WHERE id=?");
        statement.setInt(1, id);
        return statement.executeUpdate() ==1;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
