package com.yashika.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SavingAOP {
    private static final Logger logger = LoggerFactory.getLogger(SavingAOP.class);

    @Before("execution(* com.yashika.demo.services.*.save*(..))")
    public void executeBeforeTheSavingOperationIsPerformed(){
        logger.info("\\>>>" + "\"Saving to the database");
    }
}
