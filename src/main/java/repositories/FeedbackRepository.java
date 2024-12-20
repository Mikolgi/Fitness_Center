package repositories;

import models.Feedback;

import java.util.List;

public interface FeedbackRepository extends BaseRepository<Feedback> {
    public List<Feedback> getFeedbacksByTrainerId(int trainerId);

}
