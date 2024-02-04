package com.ehouse_spring.ehouse.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehouse_spring.ehouse.entity.House;
import com.ehouse_spring.ehouse.entity.repository.interfaces.HouseRepository;

@Repository
public class HouseRepositoryImpl implements HouseRepository{

    @Autowired
    private DataSource dataSource;

    /**
     * This method is used to fetch all the houses from the database.
     * 
     * @return a list of houses
     */
    @Override
    public List<House> findAll() {
        List<House> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from house");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(sqlToHouse(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method maps a SQL result set to a House object
     * 
     * @param rs the SQL result set
     * @return a House object
     */
    private House sqlToHouse(ResultSet rs) throws SQLException{
        return new House(
            rs.getLong("id"),
            rs.getString("name"));
    }

    /**
     * This method is used to fetch a house from the database based on its id.
     * 
     * @param id the id of the house
     * @return the house with the specified id, or null if no house with the
     */
    @Override
    public House find(Integer id) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM house WHERE id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return sqlToHouse(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to persist a house into the database.
     * 
     * @param house the house to be persisted
     * @return true if the house was persisted successfully, false otherwise
    */
    @Override
    public boolean persist(House house) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO house (name) VALUE (?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, house.getName());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            house.setId(rs.getLong(1));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to update a house in the database.
     * 
     * @param house the house to be updated
     * @return true if the house was updated successfully, false otherwise
     */
    @Override
    public boolean update(House house) {
        try(Connection connection =dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE house SET name=? WHERE id=?");

            statement.setString(1, house.getName());
            statement.setLong(2, house.getId());

            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete a house from the database based on its id.
     * 
     * @param id the id of the house
     * @return true if the house was deleted successfully, false otherwise
     */
    @Override
    public boolean delete(Integer id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM house WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate()==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
