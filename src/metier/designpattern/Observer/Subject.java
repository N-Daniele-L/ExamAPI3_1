package metier.designpattern.Observer;


import java.util.ArrayList;
import java.util.List;

public abstract class Subject implements ObserverInterface{
    protected static List<ObserverInterface> myObservers = new ArrayList<>();

    public void addObserver(ObserverInterface o){
        myObservers.add((ObserverInterface) o);
    }
    public void removeObserver(ObserverInterface o){
        myObservers.remove(o);
    }
    public void notifyObservers(){
        String msg=getNotification();
        for(ObserverInterface o : myObservers) o.update(msg);
    }

    public abstract String getNotification();
}
