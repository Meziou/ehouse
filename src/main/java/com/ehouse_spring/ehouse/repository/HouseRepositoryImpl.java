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

    private House sqlToHouse(ResultSet rs) throws SQLException{
        return new House(
            rs.getInt("id"),
            rs.getString("name")
        );
    }

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

    @Override
    public boolean persist(House house) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO room (name, created_at, id_house) VALUE (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, house.getName());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            house.setId(rs.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(House house) {
        try(Connection connection =dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE house SET name=?, created_at=?, id_house=? WHERE id=?");

            statement.setString(1, house.getName());

            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM house WHERE id=?");
            statement.setInt(1, id);
            return statement.executeUpdate() ==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
