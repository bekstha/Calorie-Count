package fi.metropolia.practisecalorie.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import fi.metropolia.practisecalorie.user.User;

public class UserFood {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userID"
    )
    public List<Food>foods;
}
