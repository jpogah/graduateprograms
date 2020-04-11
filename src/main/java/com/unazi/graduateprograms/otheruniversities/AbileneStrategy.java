package com.unazi.graduateprograms.otheruniversities;

import com.unazi.graduateprograms.Course;
import com.unazi.graduateprograms.CourseStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AbileneStrategy implements CourseStrategy {
    @Override
    public List<Course> degreeProgram() {
        List<Course> degrees = new ArrayList<>();
        String baseUrl = "https://www.acu.edu";
        List<String> tuition = new ArrayList<>();
        String img = null;
        try {
            Document doc = Jsoup.connect(baseUrl).get();
            Elements elements = doc.select("a[href]");
            Elements srcLinks = doc.select("img[src]");
            for(Element srcLink : srcLinks){
                if(srcLink.attr("alt").equalsIgnoreCase("Abilene Christian University logo")){
                    img= baseUrl + srcLink.attr("href");
                }
            }

            for (Element link: elements){
                if (link.attr("aria-label").equalsIgnoreCase("Admissions & Aid > Graduate > Admissions")){

                    Document graduatePage = Jsoup.connect(baseUrl + link.attr("href")).get();
                    // System.out.println(baseUrl + link.attr("href"));
                    Elements graduateLinks = graduatePage.select("a[href]");
                    for (Element graduateLink : graduateLinks){
                        if (graduateLink.attr("aria-label").equalsIgnoreCase("learn more graduate")) {
                            Document nodes = Jsoup.connect(baseUrl + graduateLink.attr("href")).get();
                            Elements programLinks = nodes.select("table").select("a[href]");
                            for (Element programLink: programLinks){

                                Course course = new Course();
                                List<String> programDetails = new ArrayList<>();
                                if (programLink.attr("href").isEmpty() || programLink.attr("href").startsWith("http") ||
                                        programLink.attr("href").startsWith("mail")) continue;
                                String website = baseUrl + programLink.attr("href");
                                Document programPage = Jsoup.connect(website).get();
                                String programName = programPage.select("div.text_comp").select("h1").first().text();
                                course.setProgramName(programName);
                                Element div = null;
                                if (programName.equalsIgnoreCase("Certificate in Social Service Administration")
                                        ){
                                    div = programPage.select("div.text_comp").get(1);
                                } else if (programName.equalsIgnoreCase("Master of Liberal Arts Program") ||
                                        programName.equalsIgnoreCase("Master of Arts in Christian Ministry") ||
                                        programName.equalsIgnoreCase("Master of Arts in Communication")
                                        || programName.equalsIgnoreCase("Master of Accountancy")
                                        ||programName.equalsIgnoreCase("Master of Arts in Modern and American Christianity")
                                        || programName.equalsIgnoreCase("Master of Arts in New Testament")
                                        || programName.equalsIgnoreCase("Master of Arts in Old Testament")
                                        || programName.equalsIgnoreCase("Master of Arts in Theology")
                                        || programName.equalsIgnoreCase("Master of Arts in Global Service") ||
                                        programName.equalsIgnoreCase("Master of Divinity Equivalency")
                                        || programName.equalsIgnoreCase("Master of Athletic Training"))
                                {
                                    div =programPage.select("div.text_comp").get(2);
                                } else if (programName.equalsIgnoreCase("Master of Science in Speech-Language Pathology")){
                                    div =programPage.select("div.text_comp").get(4);
                                } else if (programName.equalsIgnoreCase("Master of Science in Social Work - Advanced Standing")){
                                    div =programPage.select("div.text_comp").get(4);
                                }
                                else {
                                    div =programPage.select("div.text_comp").get(3);
                                }

                                Element programDtlDiv = div;
                                programDtlDiv.select("*").forEach(e-> programDetails.add(e.text()));

                                //

                                //System.out.println(programDtlDiv.text());
                                Elements linksInPragramPage = programPage.select("a[href]");
                                for (Element linkInprogramPage : linksInPragramPage) {
                                    if (linkInprogramPage.attr("href").startsWith("mail") && linkInprogramPage.attr("href").length() < 255) {
                                        String email = linkInprogramPage.attr("href");
                                        course.setEmail(email);
                                        //  System.out.println(email);
                                        break;
                                    }

                                }
                                Elements pNodes = programPage.select("p.ex_coursework");
                                for (Element p : pNodes) {
                                    if (p.text().toLowerCase().contains("credit hours") || p.text().toLowerCase().contains("program")
                                            || p.text().toLowerCase().contains("payments ")) {
                                        tuition.add(p.text());
                                    }

                                    if (p.text().toLowerCase().contains("applicants")) {
                                        programDetails.add(p.text());
                                    }
                                }

                                course.setTuitionCost(tuition.toArray(new String[tuition.size()]));
                                course.setProgramDetails(programDetails.toArray(new String[programDetails.size()]));
                                course.setAddress("1600 Campus Court\n" +
                                        "Abilene, Texas 79699");
                                course.setSchoolName("Abilene Christian University");
                                course.setCity("Abilene");
                                course.setState("TX");
                                course.setZip("79699");
                                course.setImg(img);
                                course.setWebsite(website);
                                degrees.add(course);

                            }

                        }

                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return degrees;

    }
}
