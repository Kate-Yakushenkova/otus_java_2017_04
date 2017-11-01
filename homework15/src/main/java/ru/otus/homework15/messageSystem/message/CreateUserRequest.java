package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.db.DBService;
import ru.otus.homework15.messageSystem.Address;

public class CreateUserRequest extends RequestToDb {

    private User user;

    public CreateUserRequest(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Response exec(DBService dbService) {
        CreateUserResponse response;
        try {
            Long createdUser = dbService.addUser(user);
            response = new CreateUserResponse(getTo(), getFrom(), createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
}
