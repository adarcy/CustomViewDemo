package com.syxl.customviewdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.ObservableEmitter

import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.BiFunction


class RxJava2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        basicUse()
//        schedulerThread()
//        test()
        test2()

    }

    /**
     * 基本使用
     */
    private fun basicUse() {
        Observable.create(ObservableOnSubscribe<String?> { emitter ->
            emitter.onNext("1")
            emitter.onNext("2")
            emitter.onNext("3")
            emitter.onComplete()
        }).filter {
            it == "2"
        }.subscribe(object : Observer<String?> {
            override fun onSubscribe(d: Disposable) {
                Log.e("TAG", "onSubscribe():  ")
            }

            override fun onNext(s: String) {
                Log.e("TAG", "onNext():  $s")
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                Log.e("TAG", "onComplete():  ")
            }
        })
    }

    /**
     * 线程切换
     */
    fun schedulerThread() {
        Observable.create(ObservableOnSubscribe<String?> { emitter ->
            Log.e("TAG", "subscribe(): 所在线程为 " + Thread.currentThread().name)
            emitter.onNext("1")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {
                    Log.e("TAG", "onSubscribe(): 所在线程为 " + Thread.currentThread().name)
                }

                override fun onNext(s: String) {
                    Log.e("TAG", "onNext(): 所在线程为 " + Thread.currentThread().name)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.e("TAG", "onComplete(): 所在线程为 " + Thread.currentThread().name)
                }
            })
    }

    fun test(){
        object : Thread() {
            override fun run() {
                Log.e("TAG", "run: 当前默认执行环境为 " + currentThread().name)
                Observable.create(ObservableOnSubscribe<String?> {
                        emitter -> emitter.onNext("1")
                }) // 仅保留 observeOn(Scheduler)
                    .observeOn(Schedulers.io())
                    .subscribe(object : Observer<String?> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(s: String) {
                            Log.e("TAG", "onNext(): 所在线程为 " + currentThread().name)
                        }

                        override fun onError(e: Throwable) {}
                        override fun onComplete() {}
                    })
            }
        }.start()

    }

    fun test2(){
        Observable.zip(first(), second(), zipper())
            .subscribe(System.out::println);
    }

    fun first():ObservableSource<String> {
        return Observable.create {
            Thread.sleep(1000);
            it.onNext("11");
            it.onNext("12");
            it.onNext("13");
        }
    }

    fun  second():ObservableSource<String> {
        return Observable.create{
            it.onNext("21");
            Thread.sleep(2000);
            it.onNext("22");
            Thread.sleep(3000);
            it.onNext("23");
        }
    }

    fun  zipper() : BiFunction<String, String, String> {
        val add = BiFunction { x: String, y: String -> "$x,$y" }

        return add
    }

}


//flatMap(object : Function<String, ObservableSource<String> > {
//            override fun ObservableSource<String> apply(s:String) throws Exception {
//                return Observable.just(s);
//            }
//}).