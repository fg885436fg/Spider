package spider.demo;

import java.util.concurrent.*;

public class study {

    public static void main (String[] args) {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            completableFuture.complete(Thread.currentThread().getName());
        }).start();
        doSomethingElse();//做你想做的其他操作

        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    static void doSomethingElse () {
        System.out.println("now doSomethingElse");

    }


}
