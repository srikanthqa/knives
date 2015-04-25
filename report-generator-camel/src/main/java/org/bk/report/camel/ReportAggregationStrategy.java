package org.bk.report.camel;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.bk.report.domain.Report;
import org.bk.report.domain.Section;

public class ReportAggregationStrategy implements AggregationStrategy {
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null) {
			Section section = newExchange.getIn().getBody(Section.class);
			Report report = new Report();
			report.addSection(section);
			newExchange.getIn().setBody(report);
			return newExchange;
		}

		Report report = oldExchange.getIn().getBody(Report.class);
		Section section = newExchange.getIn().getBody(Section.class);
		report.addSection(section);
		oldExchange.getIn().setBody(report);
		return oldExchange;

	}
}

