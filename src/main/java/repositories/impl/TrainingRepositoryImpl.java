package repositories.impl;

import models.Exercise;
import models.Training;
import repositories.TrainingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingRepositoryImpl implements TrainingRepository {
    private final Connection connection;

    public TrainingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Training fitnessClass) {
        String query = "INSERT INTO trainings (name, description, schedule, client_id, trainer_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fitnessClass.getName());
            stmt.setString(2, fitnessClass.getDescription());
            stmt.setString(3, fitnessClass.getSchedule());
            stmt.setInt(4, fitnessClass.getTrainerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Training training) {
        String query = "UPDATE trainings SET name = ?, description = ?, schedule = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, training.getName());
            prst.setString(2, training.getDescription());
            prst.setString(3, training.getSchedule());
            prst.setInt(4, training.getClientId());
            prst.setInt(5, training.getId());
            prst.executeUpdate();
        } catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Training> getAll() {
        List<Training> classes = new ArrayList<>();
        String query = "SELECT * FROM trainings";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                classes.add(new Training(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("schedule"),
                        rs.getInt("client_id"),
                        rs.getInt("trainer_id")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return classes;
    }

    @Override
    public Training getById(int id) {
        String query = "SELECT * FROM trainings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Training(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("schedule"),
                            rs.getInt("client_id"),
                            rs.getInt("trainer_id")
                    );
                }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM trainings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public List<Exercise> getExercisesForTraining(int trainingId) {
        String query = "SELECT e.id, e.name, e.description, e.difficulty_level " +
                "FROM exercises e " +
                "JOIN training_exercises te ON e.id = te.exercise_id " +
                "WHERE te.training_id = ?";
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainingId);
            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    exercises.add(Exercise.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .difficulty(rs.getString("difficulty"))
                            .build());
                }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return exercises;
    }

}
