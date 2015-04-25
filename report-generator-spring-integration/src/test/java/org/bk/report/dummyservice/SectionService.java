package org.bk.report.dummyservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class SectionService extends HttpServlet {

    private static final long serialVersionUID = -5725843491242163821L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        SAXReader reader = new SAXReader();
        String section = "";
        try {
            Document document = reader.read(request.getInputStream());
            String sectionName = document.selectSingleNode(
                    "/section/meta/sectionName").getText();
            
            String entityId = document
                    .selectSingleNode("/section/meta/entityId").getText();
            System.out.println("sectionName : " + sectionName);
            String sectionMeta = "<meta><entityId>" + entityId
                    + "</entityId><sectionName>" + sectionName
                    + "</sectionName></meta>";
            if (sectionName.equals("header")) {
                section += "<section>" + sectionMeta
                        + "<header>This is a header</header></section>";
            } else if (sectionName.equals("body")) {
                section += "<section>" + sectionMeta
                        + "<body>This is the Body</body></section>";
            } else if (sectionName.equals("footer")) {
                section += "<section>" + sectionMeta
                        + "<footer>This is the footer</footer></section>";
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.print(section);
        out.close();
    }
}
