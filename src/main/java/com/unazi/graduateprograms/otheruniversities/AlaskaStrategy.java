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

public class AlaskaStrategy implements CourseStrategy {
    @Override
    public List<Course> degreeProgram() {
        List<Course> degrees = new ArrayList<>();

        String schoolStite = "http://alaska.edu";
        String img = null;
        List<String> tuition = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(schoolStite).get();
            // get image
            Elements elements = doc.select("img[src]");
            for (Element element : elements){
                if (element.attr("alt").equalsIgnoreCase("University of Alaska Logo")){
                    //System.out.println(element);
                    img = schoolStite + element.attr("src");
                    break;
                }
            }

            Elements elementLinks = doc.select("a[href]");
            for (Element element : elementLinks){
                if (element.text().toLowerCase().contains("cost of attendance")){
                    Document costPage = Jsoup.connect(schoolStite + element.attr("href")).get();
                    Element costDiv = costPage.select("div.dot-list").first();
                    tuition.add(costDiv.text());
                    break;
                }
            }

            // we have to go to the graduate programs page directy
            String programBaseUrl = "https://catalog.uaa.alaska.edu/";
            Document graduateprogramsPage = Jsoup.connect("https://catalog.uaa.alaska.edu/graduateprograms/#programstext").get();
            Elements graduateProgramLinks = graduateprogramsPage.select("div#programstextcontainer").select("a[href]");

            for (Element element : graduateProgramLinks){
                if (element.attr("href").startsWith("/graduateprograms")){
                    Course course = new Course();
                    List<String> programDetails = new ArrayList<>();

                    String programWebsite = programBaseUrl + element.attr("href");
                    Document programPage = Jsoup.connect(programWebsite).get();
                    String programName = programPage.select("h1.page-title").first().text();
                    System.out.println(programName);
                    Elements programDetailDiv = programPage.select("div#textcontainer");
                    programDetailDiv.select("*").forEach(e-> programDetails.add(e.text()));
                    if (programDetailDiv.text().toLowerCase().contains("Graduate Record Examination (GRE) results".toLowerCase())){
                        course.setGreRequired("Yes");
                    }
                    course.setWebsite(programWebsite);
                    course.setProgramName(programName);
                    course.setSchoolName("University of Alaska Anchorage");
                    course.setAddress("3211 Providence Drive\n" +
                            "Anchorage, AK 99508");
                    course.setTuitionCost(tuition.toArray(new String[tuition.size()]));
                    course.setProgramDetails(programDetails.toArray(new String[programDetails.size()]));
                    course.setImg(img);
                    course.setCity("Anchorage");
                    course.setState("AK");
                    course.setZip("99508");
                    degrees.add(course);
                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        }


        return degrees;
    }
}
