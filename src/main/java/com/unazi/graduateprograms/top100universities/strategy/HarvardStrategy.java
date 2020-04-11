package com.unazi.graduateprograms.top100universities.strategy;

import com.unazi.graduateprograms.Course;
import com.unazi.graduateprograms.CourseStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HarvardStrategy implements CourseStrategy {
    @Override
    public List<Course> degreeProgram() {
        List<Course> degrees = new ArrayList<>();
        List<String> tuitionCost = new ArrayList<>();
        String baseUrl = "https://www.harvard.edu";

        try {
            Document doc = Jsoup.connect(baseUrl).get();
            Elements links = doc.select("a[href]");
            for (Element link: links){
                if (link.text().equalsIgnoreCase("Graduate & Professional Schools")){
                    Document gradschoolPage = Jsoup.connect(baseUrl + link.attr("href")).get();
                    Elements gradschoolLinks = gradschoolPage.select("a[href]");
                    for (Element gradLink : gradschoolLinks){
                        if (gradLink.text().equalsIgnoreCase("Graduate Programs")){
                            Document gradAdmissionPage = Jsoup.connect(gradLink.attr("href")).get();
                            Elements gradAdmissionLinks = gradAdmissionPage.select("a[href]");
                            // System.out.println(gradLink);
                            for (Element gradAdmissionLink : gradAdmissionLinks){
                                if ((gradAdmissionLink.attr("href").endsWith("graduate-program") &&
                                        !gradAdmissionLink.attr("href").endsWith("undergraduate-program") ) ||
                                        gradAdmissionLink.attr("href").endsWith("/degree-programs")) {
                                    // System.out.println(gradAdmissionLink);
                                    if (gradAdmissionLink.attr("href").endsWith("graduate-program")){
                                        Course course = new Course();
                                        String parentUrl = gradLink.attr("href").substring(0, gradLink.attr("href").lastIndexOf('/'));
                                        // System.out.println(parentUrl);
                                        Document seasDegreeProgram = Jsoup.connect(parentUrl + gradAdmissionLink.attr("href")).get();
                                        Elements pageLinks = seasDegreeProgram.select("a[href]");
                                        String programName = seasDegreeProgram.select("div.page__header-title").select("a[href]").last().text();
                                        String website = parentUrl + gradAdmissionLink.attr("href");
                                        String greRequired = null;
                                        List<String> programDtl = new ArrayList();
                                        // System.out.println(programName);
                                        for (Element pageLink: pageLinks){
                                            if (pageLink.attr("href").endsWith("how-apply")) {
                                                Document applyPage = Jsoup.connect(parentUrl + pageLink.attr("href")).get();
                                                Elements pNodes = applyPage.select("div.page__upper-content").select("p");
                                                Elements tdNodes = applyPage.select("div.page__upper-content").select("td");
                                                pNodes.forEach(e -> programDtl.add(e.text()));
                                                tdNodes.forEach(e -> programDtl.add(e.text()));
                                                if (applyPage.text().toLowerCase().contains("The GRE is required ".toLowerCase())){
                                                    greRequired = "Yes";
                                                }
                                            }

                                        }

                                        course.setProgramName(programName);
                                        course.setWebsite(website);
                                        course.setProgramDetails(programDtl.toArray(new String[programDtl.size()]));
                                        course.setGreRequired(greRequired);
                                        course.setSchoolName("Harvard University");
                                        course.setAddress("13 Appian Way | Cambridge, MA 02138");
                                        course.setCity("Cambridge");
                                        course.setState("MA");
                                        course.setZip("02138");
                                        degrees.add(course);
                                    }

                                }
                            }

                        }
                    }
                }
            }

            Document eduSchoolPage = Jsoup.connect("https://www.gse.harvard.edu/masters/programs").get();
            Elements nodes = eduSchoolPage.select("h3").select("a[href]");
            String eduParentUrl = "https://www.gse.harvard.edu";
            for(Element element: nodes){
                Course course = new Course();
                Document progPage = Jsoup.connect(eduParentUrl + element.attr("href")).get();
                String programName = element.text();
                course.setProgramName(programName);

                Elements links2= progPage.select("a[href]");
                List<String> proDetials = new ArrayList<>();
                for (Element progLink : links2){
                    if (progLink.text().equalsIgnoreCase("application requirements and instructions")){
                        course.setWebsite(progLink.attr("href"));
                        Document applyPage = Jsoup.connect(progLink.attr("href")).get();
                        Elements divNodes = applyPage.select("div.field-item");
                        for (Element div : divNodes){
                            if (div.attr("property").equalsIgnoreCase("content:encoded")){
                                div.select("*").forEach(e-> proDetials.add(e.text()));

                            }
                        }


                    }
                }

                course.setProgramDetails(proDetials.toArray(new String[proDetials.size()]));
                course.setSchoolName("Harvard University");
                course.setAddress("13 Appian Way | Cambridge, MA 02138");
                course.setCity("Cambridge");
                course.setState("MA");
                course.setZip("02138");
                course.setGreRequired("Yes");
                degrees.add(course);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return  degrees;

    }
}
