package repositories.impl;

import models.Exercise;
import repositories.ExerciseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {
    private final Connection connection;

    public ExerciseRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Exercise exercise) {
        String query = "INSERT INTO exercises (name, description, difficulty_level) VALUES (?, ?, ?)";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, exercise.getName());
            prst.setString(2, exercise.getDescription());
            prst.setString(3, exercise.getDifficulty());
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Exercise> getAll() {
        String query = "SELECT * FROM exercises";
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Exercise exercise = Exercise.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .difficulty(rs.getString("difficulty"))
                        .build();
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return exercises;
    }


    @Override
    public Exercise getById(int exerciseId) {
        String query = "SELECT * FROM exercises WHERE id = ?";
        Exercise exercise = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exercise = Exercise.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .difficulty(rs.getString("difficulty"))
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return exercise;
    }

    @Override
    public void delete(int exerciseId) {
        String query = "DELETE FROM exercises WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public void update(Exercise exercise) {
        String query = "UPDATE exercises SET name = ?, description = ?, difficulty_level = ? WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setString(1, exercise.getName());
            prst.setString(2, exercise.getDescription());
            prst.setString(3, exercise.getDifficulty());
            prst.setInt(4, exercise.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
