package com.kr.caption.designmode.jiketime.jike25Chapter.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * ConsoleReporter 类相当于一个上帝类，定时根据给定的时间区间，从数据库中取出数据，
 * 借助 Aggregator 类完成统计工作，并将统计结果输出到命令行。
 *
 *
 *
 * ConsoleReporter 和 EmailReporter 中存在代码重复问题。
 * 在这两个类中，从数据库中取数据、做统计的逻辑都是相同的，可以抽取出来复用，否则就违反了 DRY 原则。而且整个类负责的事情比较多，职责不是太单一。
 * 特别是显示部分的代码，可能会比较复杂(比如 Email 的展示方式)，最好是将显示部分的代码逻辑拆分成独立的类。
 * 除此之外，因为代码 中涉及线程操作，并且调用了 Aggregator 的静态函数，所以代码的可测试性不好。
 */
public class ConsoleReporter {

    private MetricsStorage metricsStorage;
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }


    // 第 4 个代码逻辑:定时触发第 1、2、3 代码逻辑的执行;
    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 第 1 个代码逻辑:根据给定的时间区间，从数据库中拉取数据;
                long durationInMillis = durationInSeconds * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;
                Map<String, List<RequestInfo>> requestInfos =
                        metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                Map<String, RequestStat> stats = new HashMap<>();
                for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                    String apiName = entry.getKey();
                    List<RequestInfo> requestInfosPerApi = entry.getValue();
                    // 第 2 个代码逻辑:根据原始数据，计算得到统计数据;
                    RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInSeconds);
                    stats.put(apiName, requestStat);
                }
                // 第 3 个代码逻辑:将统计数据显示到终端(命令行或邮件);
                System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis);
                System.out.println(stats);
            }
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }

}

