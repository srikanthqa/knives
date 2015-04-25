package org.bk.report.common;

import java.util.List;

import org.bk.report.domain.Report;
import org.bk.report.domain.Section;

public class SectionResponseAggregator {

    public Report aggregate(List<Section> sections) {
        return new Report(sections);
    }

}
