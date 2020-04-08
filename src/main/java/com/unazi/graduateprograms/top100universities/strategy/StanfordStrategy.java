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

public class StanfordStrategy implements CourseStrategy {
    @Override
    public List<Course> degreeProgram() {
        String admissionUrl = "https://gradadmissions.stanford.edu";
        String tuitionInfoLink = "https://registrar.stanford.edu/students-tuition";
        List<Course> degrees = new ArrayList<>();



        Document doc = null;
        try {
            // start of tuition info
            List<String> tuitionList = new ArrayList<>();
            Document tuitionInfoPage = Jsoup.connect(tuitionInfoLink).get();
            tuitionList .add(tuitionInfoPage.select("div.content-head").select("h1.title").text());
            Elements elements = tuitionInfoPage.select("div.content-body").select("tr");
            for(Element e : elements){
                tuitionList .add(e.text());
            }

            // end of tuition info

            doc = Jsoup.connect(admissionUrl).get();
            Elements links = doc.select("a[href]");
            for(Element link: links){
                if (link.text().contains("Search Degrees")){
                    //System.out.println(link);
                    Document tempDoc = Jsoup.connect(admissionUrl + link.attr("href")).get();
                    Elements tempLinks = tempDoc.select("a[href]");
                    for(Element link2 : tempLinks){
                        if(link2.attr("href").startsWith("/programs/")){
                            // System.out.println(admissionUrl + link2.attr("href"));
                            Course course = new Course();
                            String email = null;
                            String website = null;
                            Document programPage = Jsoup.connect(admissionUrl + link2.attr("href")).get();
                            String programName = programPage.select("h1.title").text();
                            course.setProgramName(programName);
                            Elements programLinks = programPage.select("div.content").select("a[href]");
                            // System.out.println(programPage.select();
                            for (Element link3: programLinks){

                                //
                                if (link3.attr("href").equalsIgnoreCase(link3.text())){
                                    website = link3.attr("href");
                                    course.setWebsite(website);

                                }

                                if (link3.attr("href").startsWith("mailto")){
                                    email =  link3.attr("href");
                                    course.setEmail(email);
                                }
                                if( website != null && email != null) break;
                            }

                            List<String> programDtlList = new ArrayList<>();

                            Elements programDetail = programPage.select("div").select("div#content-lower");
                            if (programDetail.text().toLowerCase().contains("GRE general test scores required".toLowerCase())){
                                course.setGreRequired("Yes");
                            }
                            programDtlList.add(programDetail.select("h2").first().text()); // title for the detail
                            programDtlList.add(programDetail.select("div#block-views-degrees-for-a-program-block").select("div.content").text());
                            programDtlList.add(programDetail.select("h2").last().text()); // title for the detail
                            programDtlList.add(programDetail.select("div#block-views-hcp-degrees-for-a-program-block").select("div.content").text());
                            programDtlList.add(programPage.select("h1.title").text());
                            course.setProgramDetails(programDtlList.toArray(new String[programDtlList.size()]));
                            course.setTuitionCost(tuitionList.toArray(new String[tuitionList.size()]));
                            course.setTuitionCost(tuitionList.toArray(new String[tuitionList.size()]));
                            course.setSchoolName("Stanford University");
                            course.setCity("Stanford");
                            course.setAddress("450 Serra Mall, Stanford, CA 94305");
                            course.setState("CA");
                            course.setZip("94305");
                            degrees.add(course);

                        }
                    }

                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return degrees;
    }
}
