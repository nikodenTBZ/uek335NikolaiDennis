package ch.noseryoung.invist.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.noseryoung.invist.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Insert
    void insertUser(User user);

    @Query("Select * from users where email = :email ")
    User getUser(String email);

}
