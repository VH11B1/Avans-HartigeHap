package edu.avans.hartigehap.domain.planning;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mark on 9-3-2015.
 */
public class NotificationSubject {
    private static NotificationSubject subject = null;

    private static Collection<IObserver> observerCollection;

    private NotificationSubject () {
        observerCollection = new ArrayList<>();

        //Functie om mailobserver initieel toe te voegen
        registerObserver(new MailObserver());
    }

    public static NotificationSubject getInstance () {
        if (subject == null) {
            subject = new NotificationSubject();
        }
        return subject;
    }

    public static void registerObserver (IObserver observer) {
        observerCollection.add(observer);
    }

    public void unregisterObserver (IObserver observer) {
        observerCollection.remove(observer);
    }

    public void notifyObservers (Employee employee, Employee supervisor, String subject, String message) {
        for (IObserver observer : observerCollection) {
            observer.update(employee, supervisor, subject, message);
        }
    }
}
