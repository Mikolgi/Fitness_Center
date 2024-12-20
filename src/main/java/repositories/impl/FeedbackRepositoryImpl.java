package repositories.impl;

import models.Feedback;
import repositories.FeedbackRepository;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class FeedbackRepositoryImpl implements FeedbackRepository {
    private final Connection connection;

    public FeedbackRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Feedback feedback) {
        String query = "INSERT INTO feedbacks (user_id, trainer_id, rating, comment, created_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, feedback.getUserId());
            prst.setInt(2, feedback.getTrainerId());
            prst.setInt(3, feedback.getRating());
            prst.setString(4, feedback.getComment());
            prst.setTimestamp(5, Timestamp.valueOf(feedback.getCreatedAt()));
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Feedback feedback) {
        String query = "UPDATE feedbacks SET rating = ?, comment = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, feedback.getComment());
            stmt.setTimestamp(3, Timestamp.valueOf(feedback.getCreatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Feedback> getAll() {
        return null;
    }

    @Override
    public Feedback getById(int feedbackId) {
        String query = "SELECT * FROM feedbacks WHERE id = ?";
        Feedback feedback = null;
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, feedbackId);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                feedback = Feedback.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .trainerId(rs.getInt("trainer_id"))
                        .rating(rs.getInt("rating"))
                        .comment(rs.getString("comment"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return feedback;
    }

    @Override
    public void delete(int feedbackId) {
        String query = "DELETE FROM feedbacks WHERE id = ?";
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, feedbackId);
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Feedback> getFeedbacksByTrainerId(int trainerId) {
        String query = "SELECT * FROM feedbacks WHERE trainer_id = ?";
        List<Feedback> feedbacks = new ArrayList<>();
        try (PreparedStatement prst = connection.prepareStatement(query)) {
            prst.setInt(1, trainerId);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                feedbacks.add(Feedback.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .trainerId(rs.getInt("trainer_id"))
                        .rating(rs.getInt("rating"))
                        .comment(rs.getString("comment"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build());
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return feedbacks;
    }
}
