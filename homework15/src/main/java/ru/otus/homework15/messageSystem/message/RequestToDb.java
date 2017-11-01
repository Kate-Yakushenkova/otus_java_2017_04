package ru.otus.homework15.messageSystem.message;


import ru.otus.homework15.db.DBService;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;

public abstract class RequestToDb extends Request {

    public RequestToDb(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Response exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            return exec((DBService) addressee);
        }
        throw new IllegalStateException("Адресат не может обработать данное сообщение");
    }

    public abstract Response exec(DBService dbService);
}
