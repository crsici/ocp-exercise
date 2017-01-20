package com.flyingsh4rk.org;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by pthanhtrung on 1/11/2017.
 */
public class PrintCounter {
    public static int counter = 0;

    public static void main(String args[]){
        System.out.println("Start");
        List<Future<?>> futureList  = new ArrayList();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        IntStream.iterate(0,i->i++).limit(5).forEach((i)->futureList.add(executorService.submit(()->counter++)));
        for(Future<?> result: futureList){
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
