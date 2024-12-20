package repositories;

import models.Training;
import models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends BaseRepository<User>{
    boolean isEmailTaken(String email);
    public List<Training> getTrainingsForClient(int clientId);
    public List<Training> getTrainingsForTrainer(int trainerId);
}
