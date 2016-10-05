package com.anastasiyayuragina.testproject;

import java.util.Observable;

/**
 * Created by anastasiyayuragina on 8/30/16.
 *
 */
public class InternetConnectionObservable extends Observable {
    private static InternetConnectionObservable observable = new InternetConnectionObservable();

    public static InternetConnectionObservable getInstance() {
        return observable;
    }

    private InternetConnectionObservable() {
    }

    void dataChanged() {
        synchronized (this) {
            setChanged();
            notifyObservers();
        }
    }
}
