package org.bk.report;

import org.bk.report.domain.Report;

public interface ReportGenerator {
    Report generateReport(ReportRequest reportRequest);
}
