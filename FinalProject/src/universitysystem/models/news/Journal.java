package universitysystem.models.news;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Journal {

    /**
     * Default constructor
     */
    public Journal() {
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private List<Subscriber> subscribers ;


    /**
     * 
     */
    public void subscribe(Subscriber subscriber) {
        if (this.subscribers == null) {
            this.subscribers = new ArrayList<>();
        }
        if (subscriber != null) {
            this.subscribers.add(subscriber);
        }
    }

    /**
     * 
     */
    public void unsubscribe(Subscriber subscriber) {
        if (this.subscribers != null && subscriber != null) {
            this.subscribers.remove(subscriber);
        }
    }

    /**
     * 
     */
    public void notifySubscribers() {
        if (this.subscribers == null) {
            return;
        }
        for (Subscriber subscriber : this.subscribers) {
            if (subscriber != null) {
                subscriber.update("New article available in " + this.name);
            }
        }
    }

    /**
     * 
     */
    public void publishNews(News news) {
        notifySubscribers();
    }

}