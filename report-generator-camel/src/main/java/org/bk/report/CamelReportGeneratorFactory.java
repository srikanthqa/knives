package org.bk.report;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CamelReportGeneratorFactory implements
        ReportGeneratorFactory {

    @Override
    public ReportGenerator createReportGenerator() {
        String[] configs = {"classpath:camel-context.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configs);
        ReportGenerator reportGenerator = (ReportGenerator) ctx.getBean("consumerMessageGateway");
        return reportGenerator;
   }

}
