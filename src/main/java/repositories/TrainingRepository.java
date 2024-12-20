package repositories;

import models.Exercise;
import models.Training;

import java.util.List;

public interface TrainingRepository extends BaseRepository<Training> {
    List<Exercise> getExercisesForTraining(int trainingId);

}
