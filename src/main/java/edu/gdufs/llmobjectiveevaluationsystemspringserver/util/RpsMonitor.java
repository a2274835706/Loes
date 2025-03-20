package edu.gdufs.llmobjectiveevaluationsystemspringserver.util;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class RpsMonitor {

    private final ConcurrentLinkedDeque<Long> requestTimestamps = new ConcurrentLinkedDeque<>();

    private static final int REQUESTS_PER_SECOND_THRESHOLD = 100;

    private final AtomicInteger counter = new AtomicInteger(0);

    public void add(long timestamp) {
        requestTimestamps.addLast(timestamp);
        counter.incrementAndGet();
        while (!requestTimestamps.isEmpty() && timestamp - requestTimestamps.peek() > 1000) {
            requestTimestamps.poll();
            counter.decrementAndGet();
        }
    }

    public int getRps() {
        return counter.get();
    }

}
