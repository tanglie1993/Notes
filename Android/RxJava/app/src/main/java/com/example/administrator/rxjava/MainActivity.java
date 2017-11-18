package com.example.administrator.rxjava;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    Observer<String> observer = new Observer<String>() {

        @Override
        public void onCompleted() {
            Log.i(TAG, "Completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "Error: " + e);
        }

        @Override
        public void onNext(String s) {
            Log.i(TAG, s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleDemo();
        errorDemo();
        actionsDemo();
    }

    private void actionsDemo() {
        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.d(TAG, s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "onError");
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.d(TAG, "completed");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private void errorDemo() {
        // NullPointerException也会自动被onerror捕获
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber = null;
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }

    private void simpleDemo() {
         Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);
    }
}
