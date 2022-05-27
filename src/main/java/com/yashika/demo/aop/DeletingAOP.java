package com.yashika.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeletingAOP {
    private static final Logger logger = LoggerFactory.getLogger(DeletingAOP.class);

    @Before("execution(* com.yashika.demo.services.*.delete*(..))")
    public void executeBeforeTheDeleteOperationIsPerformed(){

        logger.info("\\>>>" + "\"Deleting from database");
    }
}
