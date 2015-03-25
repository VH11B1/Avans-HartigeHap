package edu.avans.hartigehap.service;

/**
 * Created by Mark on 8-3-2015.
 */
public interface NotificationService {
    public boolean sendNotification (String from, String to, String subject, String msg);
}
