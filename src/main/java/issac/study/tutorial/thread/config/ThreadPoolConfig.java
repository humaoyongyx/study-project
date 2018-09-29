package issac.study.tutorial.thread.config;

import issac.study.tutorial.thread.annotation.Bean;
import issac.study.tutorial.thread.annotation.Config;

import java.util.concurrent.*;


@Config
public class ThreadPoolConfig {

    @Bean("pool1")
    public ExecutorService pool1() {

        return new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    @Bean("pool2")
    public ExecutorService pool2() {

        return Executors.newCachedThreadPool();
    }

}
