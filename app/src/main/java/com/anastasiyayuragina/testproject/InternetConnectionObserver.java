package com.anastasiyayuragina.testproject;

import java.util.Observable;

/**
 * Created by anastasiyayuragina on 8/30/16.
 *
 */
public class InternetConnectionObserver extends Observable {

    public void dataChanged() {
        setChanged();
        notifyObservers();
    }
}
