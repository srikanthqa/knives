package org.bk.report.domain;

public class Section {
    private final String entityId;
    private final String name;
    private final String content;

    public Section(String entityId, String name, String content) {
        this.entityId = entityId;
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getEntityId() {
        return entityId;
    }
    
    public String toString(){
        return content;
    }

}
