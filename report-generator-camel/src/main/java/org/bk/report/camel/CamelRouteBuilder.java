package org.bk.report.camel;

import org.apache.camel.builder.RouteBuilder;
import org.bk.report.ReportRequest;

public class CamelRouteBuilder extends RouteBuilder {
	private String serviceURL;

	@Override
	public void configure() throws Exception {
        from("direct:start")
        .convertBodyTo(ReportRequest.class)
        .split(bean("sectionRequestSplitterBean", "split"), new ReportAggregationStrategy())
		.transform().method("sectionRequestToXMLBean", "transform")
    	.to(serviceURL)
        .transform().method("sectionResponseXMLToSectionBean", "transform");
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
}
