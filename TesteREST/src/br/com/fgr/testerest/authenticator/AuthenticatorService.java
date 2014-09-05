package br.com.fgr.testerest.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AuthenticatorService extends Service {

    private static final String TAG = AuthenticatorService.class.getName();

    private Authenticator mAuthenticator;

    public AuthenticatorService() {

        super();

    }

    @Override
    public void onCreate() {

        Log.i(TAG, "onCreate()");

        mAuthenticator = new Authenticator(this);

    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i(TAG, "onBind()");

        return mAuthenticator.getIBinder();

    }

}