package com.ivan.fish.aspect;

import com.ivan.fish.model.Fish;
import com.ivan.fish.model.FishStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Aspect
public class FishAspect {

    /**
     * 因为一个消费者可以调用多个提供者，因此每个提供者都有自己的断路器
     */
    public static Map<String, Fish> fishMap = new HashMap<>();

    static {
        fishMap.put("order-service", new Fish());
    }

    private Random random = new Random();

    /**
     * 类比拦截器
     * 判断当前断路器状态，从而决定是否发起调用
     * @param joinPoint
     * @return
     */
    @Around(value = "@annotation(com.ivan.fish.anno.FishAnno)")
    public Object fishAround(ProceedingJoinPoint joinPoint){
        Fish fish = fishMap.get("order-service");
        FishStatus status = fish.getStatus();
        Object proceed = null;
        switch (status) {
            case OPEN:
                proceed = "我是备胎";
                break;
            case CLOSE:
                try {
                    proceed = joinPoint.proceed();
                } catch (Throwable e) {
                    fish.addFailCount();
                    proceed = "我是备胎";
                }
                break;
            case HALF_OPEN:
                int i = random.nextInt(5);
                System.out.println(i);
                if (i == 1) {
                    try {
                        proceed = joinPoint.proceed();
                        fish.setStatus(FishStatus.CLOSE);
                        synchronized (fish.lock) {
                            fish.lock.notifyAll();
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                proceed = "我是备胎";
        }
        return proceed;
    }
}
