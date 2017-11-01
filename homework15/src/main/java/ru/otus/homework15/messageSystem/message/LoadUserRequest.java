package ru.otus.homework15.messageSystem.message;


import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.db.DBService;
import ru.otus.homework15.exception.DBException;
import ru.otus.homework15.messageSystem.Address;

public class LoadUserRequest extends RequestToDb {

    private long userId;

    public LoadUserRequest(Address from, Address to, Long userId) {
        super(from, to);
        this.userId = userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Response exec(DBService dbService) {
        User user = null;
        try {
            user = (User) (dbService).getUser(userId);
        } catch (DBException e) {
            e.printStackTrace();
            return null;
        }
        return new LoadUserResponse(getTo(), getFrom(), user);
    }
}
