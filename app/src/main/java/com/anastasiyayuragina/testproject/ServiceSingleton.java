package com.anastasiyayuragina.testproject;

/**
 * Created by anastasiyayuragina on 10/13/16.
 *
 */

public class ServiceSingleton {
    private static ServiceSingleton instance;
    private APIServices service;

    public static ServiceSingleton getInstance() {
        ServiceSingleton localInstance = instance;

        if (localInstance == null) {
            synchronized (ServiceSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceSingleton();
                }
            }
        }
        return localInstance;
    }

    private ServiceSingleton() {
        service = RetrofitSingleton.getInstance().getRetrofit().create(APIServices.class);
    }

    public APIServices getAPIServices() {
        return service;
    }
}
