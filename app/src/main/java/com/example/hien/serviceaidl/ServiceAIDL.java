package com.example.hien.serviceaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by hien on 8/2/17.
 */

public class ServiceAIDL extends Service {

    private IMyCal.Stub myCal = new IMyCal.Stub() {

        @Override
        public int sum(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public int sub(int a, int b) throws RemoteException {
            return a - b;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return myCal;
    }


}
