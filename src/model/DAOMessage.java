package model;

import metier.Message;

import java.util.List;

public interface DAOMessage {
    Message addMessage(Message mes);

    boolean removeMessage(Message mes);

    Message updateMessage(Message mes);

    Message readMessage(int idMess);

    List<Message> getMessage();

}
