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
public class DeviceRepositoryImpl implements DeviceRepository {
    @Autowired
    private DataSource dataSource;

    /**
     * This method is used to fetch all the devices from the database.
     * 
     * @return a list of devices
     */
    @Override
    public List<Device> findAll() {
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

    /**
     * This method maps a SQL result set to a Device object
     * 
     * @param rs the SQL result set
     * @return a Device object
     * @throws SQLException if there is an error accessing the result set
     */
    private Device sqlToDevice(ResultSet rs) throws SQLException {
        return new Device(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("status"),
                rs.getDate("created_at").toLocalDate(),
                rs.getInt("id_room"));
    }

    /**
     * This method is used to fetch all the devices from the database based on the
     * room id.
     * 
     * @param id_room the id of the room
     * @return a list of devices
     */
    @Override
    public List<Device> findByRoom(int id_room) {
        List<Device> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT d.*, r.name as room_name FROM device d LEFT JOIN room r ON d.id_room = r.id WHERE d.id_room=?");
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

    /**
     * This method is used to fetch a device from the database based on its id.
     * 
     * @param id the id of the device
     * @return the device with the specified id, or null if no device with the
     * specified id exists
     */
    @Override
    public Device find(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            // create a prepared statement to retrieve the device
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM device WHERE id = ?");
            statement.setInt(1, id);

            // execute the query and retrieve the result set
            ResultSet rs = statement.executeQuery();

            // check if there is a device with the specified id
            if (rs.next()) {
                // map the SQL result set to a Device object
                return sqlToDevice(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to persist a device into the database.
     * 
     * @param device the device to be persisted
     * @return true if the device was persisted successfully, false otherwise
     */
    @Override
    public boolean persist(Device device) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO device (name, type, status, created_at, id_room)VALUE (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreated_at() != null ? Date.valueOf(device.getCreated_at()) : null);
            statement.setInt(5, device.getId_room());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            device.setId(rs.getLong(1));

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to update a device in the database.
     * 
     * @param device the device to be updated
     * @return true if the device was updated successfully, false otherwise
     */
    @Override
    public boolean update(Device device) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE device SET name=?, type=?, status=?, created_at=?, id_room=? WHERE id=?");

            statement.setString(1, device.getName());
            statement.setString(2, device.getType());
            statement.setString(3, device.getStatus());
            statement.setDate(4, device.getCreated_at() != null ? Date.valueOf(device.getCreated_at()) : null);
            statement.setInt(5, device.getId_room());
            statement.setLong(6, device.getId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete a device from the database based on its id.
     * 
     * @param id the id of the device
     * @return true if the device was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(Integer id) {
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM device WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
