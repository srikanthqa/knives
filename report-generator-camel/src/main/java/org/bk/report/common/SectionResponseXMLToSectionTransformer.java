package org.bk.report.common;

import java.io.StringReader;

import org.bk.report.domain.Section;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class SectionResponseXMLToSectionTransformer {
	public Section transform(String sectionXML) {
		SAXReader saxReader = new SAXReader();
		Document document;
		String sectionName = "";
		String entityId = "";
		try {
			document = saxReader.read(new StringReader(sectionXML));

			sectionName = document
					.selectSingleNode("/section/meta/sectionName").getText();
			entityId = document.selectSingleNode("/section/meta/entityId")
					.getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new Section(entityId, sectionName, sectionXML);
	}
}
