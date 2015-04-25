package org.bk.report.domain;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<Section> sections;
    
    public Report(){
        sections = new ArrayList<Section>();
    }
    
    public Report(List<Section> sections){
    	this.sections = sections;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }
    
    public String toString(){
        StringBuilder reportAsString = new StringBuilder();
        for (Section section:sections){
            reportAsString.append("\n").append(section.toString());
        }
        
        return reportAsString.toString();
    }
}
