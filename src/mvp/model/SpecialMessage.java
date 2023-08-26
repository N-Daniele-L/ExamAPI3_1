package mvp.model;

import metier.Message;

import java.util.List;

public interface SpecialMessage {
    List<Message> afficherMessageNonLu(String mail);
}
