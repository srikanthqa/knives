package org.bk.report.common;

import org.bk.report.SectionRequest;

public class SectionRequestToXMLTransformer {

    public String transform(SectionRequest sectionRequest){
        String sectionRequestAsString = "<section><meta><entityId>" + sectionRequest.getEntityId()
        + "</entityId><sectionName>" + sectionRequest.getSectionId()
        + "</sectionName></meta></section>";
        
        return sectionRequestAsString;
        
    }
}
