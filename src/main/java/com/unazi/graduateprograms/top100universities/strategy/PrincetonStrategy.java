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

public class PrincetonStrategy implements CourseStrategy {

    @Override
    public List<Course> degreeProgram() {
        List<Course> degress = new ArrayList<>();
        try {
            String img = "https://www.princeton.edu/themes/custom/tony/logo.svg";
            List<String> tuitionList = new ArrayList<>();
            String baseSite = "https://gradschool.princeton.edu";
            Document doc = Jsoup.connect("https://gradschool.princeton.edu/").get();
            Elements links = doc.select("a[href]");
            Elements imgLinks = doc.select("img[src]");
//            for (Element element : imgLinks) {
//                if (element.attr("alt").equalsIgnoreCase("Princeton University")) {
//                    img = element.attr("src");
//                    //  System.out.println(img);
//                    break;
//                }
//            }

            for (Element element : links) {
                String format = "%-40s%s%n";
                if (element.attr("href").endsWith("costs-funding")) {
                    Document costPage = Jsoup.connect(baseSite + element.attr("href")).get();
                    Elements costPageLinks = costPage.select("a[href]");
                    for (Element link : costPageLinks) {
                        if (link.attr("href").endsWith("tuition-and-costs")) {
                            Document tuitionPage = Jsoup.connect(baseSite + link.attr("href")).get();
                            Elements elements = tuitionPage.select("div.field-item");
                            Elements tables = elements.select("table");
                            for (Element table : tables) {
                                table.select("thead").select("th").forEach(e -> {
                                    tuitionList.add(String.format("%-20s", e.text()));
                                    //System.out.printf("%-20s",e.text());
                                });
                                System.out.println();

                                Elements tbody = table.select("tbody");
                                tbody.select("tr").forEach(e -> {
                                    e.select("td").forEach(t -> {
                                        tuitionList.add(String.format("%-20s", t.text()));
                                        System.out.printf(" %-20s ", t.text());
                                    });

                                    System.out.println();

                                });
                                break;
                            }
                            // elements.forEach(System.out::println);
                            break;

                        }
                    }
                }
            }

                for(Element element: links){
                if (element.attr("href").endsWith("academics")) {
                    Document academicsPage = Jsoup.connect(baseSite + element.attr("href")).get();
                    Elements academicPageLinks = academicsPage.select("a[href]");
                    for (Element link : academicPageLinks) {
                        if (link.attr("href").endsWith("fields-study")) {
                            Document fieldStudyPage = Jsoup.connect(baseSite + link.attr("href")).get();
                            Elements fieldStudyLinks = fieldStudyPage.select("a[href]");
                            for (Element linkInfieldStudy : fieldStudyLinks) {
                                if (linkInfieldStudy.attr("href").endsWith("4371")) {
                                    //System.out.println(linkInfieldStudy.attr("href"));
                                    Document nodes = Jsoup.connect(linkInfieldStudy.attr("href")).get();
                                    Elements nodesLinks = nodes.select("a[href]");
                                    for (Element nodeLink : nodesLinks) {
                                        Course course = new Course();
                                        if (nodeLink.attr("href").startsWith("/node")) {
                                            Document programPage = Jsoup.connect(baseSite + nodeLink.attr("href")).get();
                                            String programeName = programPage.select("h1#page-title").text();
                                            course.setProgramName(programeName);
                                            //  System.out.println(programPage);


                                            String website = null;
                                            String email = null;
                                            Elements programLinks = programPage.select("a[href]");
                                            Elements programDivs = programPage.select("div#applying");
                                            List<String> programDetails = new ArrayList<>();
                                            programDivs.forEach(e -> {
                                                programDetails.add(e.text());
                                            });
                                            course.setProgramDetails(programDetails.toArray(new String[programDetails.size()]));

                                            if (programDivs.text().toLowerCase().contains("General Test required".toLowerCase())) {
                                                course.setGreRequired("Yes");
                                            }
                                            // System.out.println(programDivs.text());

                                            for (Element programLink : programLinks) {
                                                if (programLink.text().toLowerCase().contains(programeName.toLowerCase())) {
                                                    website = programLink.attr("href");
                                                    course.setWebsite(website);
                                                }
                                                if (programLink.attr("href").startsWith("mail")) {
                                                    email = programLink.attr("href");
                                                    course.setEmail(email);
                                                }
                                            }
                                            course.setAddress("Princeton University Graduate School\n" +
                                                    "Clio Hall, Princeton, NJ, 08544");
                                            course.setCity("Princeton");
                                            course.setState("NJ");
                                            course.setZip("08544");
                                            course.setTuitionCost(tuitionList.toArray(new String[tuitionList.size()]));
                                            course.setImg(img);
                                            course.setSchoolName("Princeton University ");
                                            degress.add(course);

                                        }

                                    }
                                    break;
                                }
                            }

                        }
                    }

                    break;
                }
            }

            //  links.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return degress;

    }
}
