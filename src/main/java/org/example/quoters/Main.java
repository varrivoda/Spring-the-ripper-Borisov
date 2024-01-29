package org.example.quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        //context.getBean(/*"TerminatorQuoter", */Quoter.class).sayQuote();

        while(true){
            Thread.sleep(1000);
            context.getBean("terminatorQuoter", TerminatorQuoter.class).sayQuote();
        }
    }
}