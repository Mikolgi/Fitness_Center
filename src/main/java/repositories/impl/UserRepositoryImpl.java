package repositories.impl;

import models.Training;
import repositories.UserRepository;
import models.User;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(User user) {
        String query = user.getRole().equalsIgnoreCase("trainer")
                ? "INSERT INTO users (email, password, name, phone, role, qualification) VALUES (?, ?, ?, ?, ?, ?)"
                : "INSERT INTO users (email, password, name, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, user.getEmail());
            prst.setString(2, user.getPassword());
            prst.setString(3, user.getName());
            prst.setString(4, user.getPhone());
            prst.setString(5, user.getRole());

            if (user.getRole().equalsIgnoreCase("trainer")) {
                prst.setString(6, user.getQualification());
            }

            prst.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void update(User user) {
        String query = user.getRole().equalsIgnoreCase("trainer")
                ? "UPDATE users SET email = ?, password = ?, name = ?, phone = ?, role = ?, qualification = ? WHERE id = ?"
                : "UPDATE users SET email = ?, password = ?, name = ?, phone = ?, role = ? WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, user.getEmail());
            prst.setString(2, user.getPassword());
            prst.setString(3, user.getName());
            prst.setString(4, user.getPhone());
            prst.setString(5, user.getRole());
            if (user.getRole().equalsIgnoreCase("trainer")) {
                prst.setString(6, user.getQualification());
                prst.setInt(7, user.getId());
            } else {
                prst.setInt(6, user.getId());
            }
            prst.executeUpdate();
        } catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                users.add(resultToUser(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, id);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                return resultToUser(rs);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, id);
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, email);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return false;
    }

    private User resultToUser(ResultSet rs) {
        try {
            return User.builder()
                    .id(rs.getInt("id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .phone(rs.getString("phone"))
                    .role(rs.getString("role"))
                    .qualification("trainer".equalsIgnoreCase(rs.getString("role")) ? rs.getString("qualification") : null)
                    .build();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Training> getTrainingsForClient(int clientId) {
        String query = "SELECT * FROM trainings WHERE client_id = ?";
        List<Training> trainings = new ArrayList<>();
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, clientId);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                Training training = resultToTraining(rs);
                trainings.add(training);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return trainings;
    }

    @Override
    public List<Training> getTrainingsForTrainer(int trainerId) {
        String query = "SELECT * FROM trainings WHERE trainer_id = ?";
        List<Training> trainings = new ArrayList<>();
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, trainerId);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                Training training = resultToTraining(rs);
                trainings.add(training);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return trainings;
    }

    private Training resultToTraining(ResultSet rs){
        try {
            return Training.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .schedule(rs.getString("schedule"))
                    .clientId(rs.getInt("client_id"))
                    .trainerId(rs.getInt("trainer_id"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
