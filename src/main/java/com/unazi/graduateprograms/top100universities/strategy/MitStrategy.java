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

public class MitStrategy implements CourseStrategy {
    @Override
    public List<Course> degreeProgram() {
        String admissionUrl = "http://gradadmissions.mit.edu";
        String img = "http://gradadmissions.mit.edu/sites/default/files/mit-logo-sprite.png";
        String schoolName = "Massachusetts Institute of Technology";
        String city = "Cambridge";
        String state = "MA";
        String tuitionInfo = null;
        String zip = "02139";
        List<Course> courses = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(admissionUrl).get();
            Elements links = doc.select("[src]");
            //links.forEach(System.out::println);

            Elements links2 = doc.select("a[href]");
            // Get MIT tuition info
            for (Element link : links2) {
                if (link.text().contains("Expenses")) {
                    String url = link.attr("href");
                    Document costPage = Jsoup.connect(admissionUrl + url).get();
                    Element e = costPage.selectFirst("div.article-content");
                    tuitionInfo = e.select("ul").text();
                }
            }

            // Get each graduate degree program for MIT
            String programName = null;

            Elements links3 = doc.select("a[href]");
            // links3.forEach(System.out::println);
            for (Element link : links3) {
                // we repeat this iteration for Doctoral Degrees
                if (link.text().contains("Master's Degrees") || link.text().contains("Doctoral Degrees") ){
                    Document document = Jsoup.connect(admissionUrl + link.attr("href")).get();
                    Elements proglinks = document.select("div.program_list_column").select("a[href]");
                    for (Element e : proglinks) {
                        Course course = new Course();
                        // set common fields
                        course.setSchoolName(schoolName);
                        List<String> list = new ArrayList<String>();
                        list.add(tuitionInfo);
                        course.setTuitionCost(list.toArray(new String[list.size()]));
                        course.setCity(city);
                        course.setState(state);
                        course.setZip(zip);
                        course.setAddress("77 Massachusetts Ave, Cambridge, MA 02139");
                        course.setImg(img);
                        Document programPage = Jsoup.connect(admissionUrl + e.attr("href")).get();

                        //   Determine if GRE is Required
                        if (programPage.text().toLowerCase().contains("General test required".toLowerCase())) {
                            course.setGreRequired("Yes");
                        }

                        //  compute program Name
                        Elements programsH1tag = programPage.select("h1");
                        for (Element h1 : programsH1tag) {
                            if (h1.attr("id").equalsIgnoreCase("page-title")) {
                                course.setProgramName(programName = h1.text());
                                break;
                            }
                        }
                        Elements programDivs = programPage.select("div");
                        // Get program details
                        List<String> programDetails = new ArrayList<>();
                        for (Element div : programDivs) {
                            if(div.attr("class").contains("group-section-two")
                                    || div.attr("class").contains("group-section-three") || div.attr("class").contains("group-section-four"))
                            programDetails.add(div.text());
                        }
                        course.setProgramDetails(programDetails.toArray(new String[programDetails.size()]));

                        // Get program email

                        Elements departmentLinks = programPage.select("a[href]");
                        for (Element a : departmentLinks) {
                            if (a.attr("href").startsWith("mail")) {
                                course.setEmail(a.attr("href"));
                            }
                        }

                        //  Get program Website
                        for (Element a : departmentLinks) {
                            if (a.text().toLowerCase().equalsIgnoreCase(programName.toLowerCase())) {
                                String programWebsite = a.attr("href");
                                course.setWebsite(programWebsite);
                            }

                            if (a.text().toLowerCase().contains("apply here")) {
                                String applyLinks = a.attr("href");
                                course.setApplyLink(applyLinks);;
                            }

                        }


                        courses.add(course);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }



}
