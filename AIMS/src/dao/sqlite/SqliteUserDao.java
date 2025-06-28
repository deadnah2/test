package dao.sqlite;

import dao.IUserDAO;
import entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements IUserDAO {
    private final Connection connection;

    SqliteUserDao(Connection connection){
        this.connection = connection;
    }

    public User getUserFromQueryResult(ResultSet result){
        try{
            int id = result.getInt("id");
            String name = result.getString("name");
            String email = result.getString("email");
            String phone = result.getString("phone");
            return new User(id, name, email, phone);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll()  {
        try{
            String query = "SELECT * FROM \"User\";";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            List<User> users = new ArrayList<User>();
            while (result.next()){
                User user = getUserFromQueryResult(result);
                users.add(user);
            }
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        try{
            String query = "Select * FROM \"User\" " +
                    "WHERE id = " + Integer.toString(id) + ";";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return getUserFromQueryResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createUser(User user) {
        try {
            String query = "INSERT INTO \"User\" (name, email, phone) "
                    + "VALUES "
                    + "(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();
            System.out.println("Update successfully");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserById(User user) {
        try{
            String query = "UPDATE \"User\""
                    + "SET email = ?, phone = ?, name = ? "
                    + "WHERE id = ?;";
            PreparedStatement stm = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getPhone());
            stm.setString(3, user.getName());
            stm.setInt(4, user.getId());
            stm.executeUpdate();
            stm.getGeneratedKeys();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserById(int id) {
        try{
            String query = "DELETE FROM \"User\" WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Deleted user");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
