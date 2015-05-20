package org.bk.report;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.bk.report.domain.Report;
import org.bk.report.domain.Section;
import org.bk.report.dummyservice.SectionService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.eclipse.jetty.servlet.ServletTester;


public class TestIntegrationReportGenerator {
    
    private static String baseURL = null;
    @BeforeClass
    public static void initServletContainer() throws Exception {
        ServletTester tester = new ServletTester();
        tester.setContextPath("/");
        tester.addServlet(SectionService.class, "/section");
        baseURL = tester.createConnector(true);
        System.setProperty("sectionBaseURL", baseURL);
        tester.start();
    }    
    
    @Test
    public void testReportGeneratorIntegrationFlow(){
        ReportGeneratorFactory reportGeneratorFactory = new CamelReportGeneratorFactory();
        ReportGenerator reportGenerator = reportGeneratorFactory.createReportGenerator();
        List<SectionRequest> sectionRequests = new ArrayList<SectionRequest>();
        
        String entityId="MSFT";
        
        sectionRequests.add(new SectionRequest(entityId,"header"));
        sectionRequests.add(new SectionRequest(entityId,"body"));
        sectionRequests.add(new SectionRequest(entityId,"footer"));        

        ReportRequest reportRequest = new ReportRequest(sectionRequests);


        Report report = reportGenerator.generateReport(reportRequest);
        List<Section> sectionOfReport = report.getSections();
        System.out.println(report);
        assertEquals(3, sectionOfReport.size());
        
    }
    

}
