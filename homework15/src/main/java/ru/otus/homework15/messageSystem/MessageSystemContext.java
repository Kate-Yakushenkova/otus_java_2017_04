package ru.otus.homework15.messageSystem;


public class MessageSystemContext {

    private final MessageSystem messageSystem;

    private Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem, String address) {
        this.messageSystem = messageSystem;
        dbAddress = new Address(address);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
