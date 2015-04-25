package org.bk.report.common;

import java.util.List;

import org.bk.report.ReportRequest;
import org.bk.report.SectionRequest;

public class SectionRequestSplitter {
    
    public List<SectionRequest> split(ReportRequest reportRequest){
        return reportRequest.getSectionRequests();
    }

}
