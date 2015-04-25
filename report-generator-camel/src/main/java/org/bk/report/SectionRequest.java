package org.bk.report;

public class SectionRequest {
    private final String sectionId;
    private final String entityId;

    public SectionRequest(String entityId, String sectionId) {
        this.entityId = entityId;
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getEntityId() {
        return entityId;
    }

}
