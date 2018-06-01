# spring quartz 获取下一次执行时间

根据业务需求(虽然有些变态), 无论定时任务是每天凌晨执行,还是每隔几个小时执行,都需要显示下一次
定时任务的执行时间, 代码如下 
```
package com.hf.quartz;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

public class getNextDate {
    public static void main(String[] args) {
        Date next = null;
        try{
            String  cronExpr = "0 30 23 * * ?";
            System.out.println("自动计费表达式为----" + cronExpr);
            CronSequenceGenerator generator = new CronSequenceGenerator(cronExpr);
            next = generator.next(new Date());
        }catch (Exception e){
            System.out.println("获取自动计费定时任务下一次时间异常----" +e.getMessage());
            e.printStackTrace();
        }

        System.out.println("输出下次执行的任务的时间---" + next);
    }
}

```

- summary<br>
夏天来了, 冬天还会远吗

