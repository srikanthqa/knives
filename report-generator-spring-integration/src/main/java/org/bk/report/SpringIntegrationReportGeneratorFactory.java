package org.bk.report;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIntegrationReportGeneratorFactory implements
        ReportGeneratorFactory {

    @Override
    public ReportGenerator createReportGenerator() {
        String[] configs = {"classpath:/applicationContext-spring-integration.xml"};
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configs);
        return (ReportGenerator)applicationContext.getBean("reportGenerator");
    }

}
