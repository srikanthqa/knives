package org.bk.report;

import java.util.List;

import org.bk.report.domain.Report;
import org.bk.report.domain.Section;

public interface ReportGenerator {
    Report generateReport(ReportRequest reportRequest);
}
