package org.bk.report;

import java.util.List;

public class ReportRequest {
    private final List<SectionRequest> sectionRequests;

    public ReportRequest(List<SectionRequest> sectionRequests) {
        this.sectionRequests = sectionRequests;
    }

    public List<SectionRequest> getSectionRequests() {
        return sectionRequests;
    }

}
