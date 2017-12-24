package com.example.administrator.rxjava;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    class Student{
        String[] courses = new String[2];
    };
    Student[] students = new Student[2];


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

        students[0] = new Student();
        students[0].courses[0] = "courseA";
        students[0].courses[1] = "courseB";
        students[1] = new Student();
        students[1].courses[0] = "courseC";
        students[1].courses[1] = "courseD";

        simpleDemo();
        errorDemo();
        actionsDemo();
        schedulerDemo();
        mapDemo();
        flatMapDemo();
        liftDemo();
        composeDemo();
    }

    private void composeDemo() {
        Observable.from(students)
                .compose(new Observable.Transformer<Student, Student>() {
                    @Override
                    public Observable<Student> call(Observable<Student> studentObservable) {
                        return studentObservable.lift(new Observable.Operator<Student, Student>() {

                            @Override
                            public Subscriber<? super Student> call(final Subscriber<? super Student> subscriber) {
                                return new Subscriber<Student>() {
                                    @Override
                                    public void onCompleted() {
                                        subscriber.onCompleted();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        subscriber.onError(e);
                                    }

                                    @Override
                                    public void onNext(Student student) {
                                        student.courses[0] += " lifted";
                                        student.courses[1] += " lifted";
                                        subscriber.onNext(student);
                                    }
                                };
                            }
                        });
                    }
                })
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.courses);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        Log.i(TAG, "string: " + string);
                    }
                });
    }

    private void liftDemo() {

        Observable.from(students)
                .lift(new Observable.Operator<Student, Student>() {

                    @Override
                    public Subscriber<? super Student> call(final Subscriber<? super Student> subscriber) {
                        return new Subscriber<Student>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(Student student) {
                                student.courses[0] += " lifted";
                                student.courses[1] += " lifted";
                                subscriber.onNext(student);
                            }
                        };
                    }
                })
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.courses);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        Log.i(TAG, "string: " + string);
                    }
                });
    }

    private void flatMapDemo() {
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.courses);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        Log.i(TAG, "string: " + string);
                    }
                });
    }

    private void mapDemo() {
        Observable.just(1) // 输入类型 String
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer i) { // 参数类型 String
                        return "" + i; // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) { // 参数类型 Bitmap
                        Log.i(TAG, "string: " + string);
                    }
                });
    }

    private void schedulerDemo() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "doOnSubscribe");
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                      Log.i(TAG, Thread.currentThread().getName());
                      Log.i(TAG, "number:" + number);
                    }
                });
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
