package com.unazi.graduateprograms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GeorgiaTechStrategy implements CourseStrategy {

    private String schoolWebsite;
    private String programWebsite;
    private List<Course> degreePrograms;


    public GeorgiaTechStrategy(String schoolWebsite, String programsWebsite) {
        this.programWebsite = programsWebsite;
        this.schoolWebsite = schoolWebsite;
        this.degreePrograms = new ArrayList<Course>();

    }


    @Override
    public List<Course> degreeProgram() {

        String img = null;
        String tuition = null;

        try {
            Document doc = Jsoup.connect(this.schoolWebsite).get();
            Elements links = doc.select("a[href]");

            // Get the image for specific university
            Elements media = doc.select("img");
            for (Element link: media) {
                if (link.attr("alt").startsWith("Admissions")) {

                    img = this.schoolWebsite+link.attr("src");
                }
            }

            // Get Tuition information

            for (Element link : links) {
                if (link.attr("href").startsWith("/admissions")) {
                    Document temp = Jsoup.connect(this.schoolWebsite + link.attr("href")).get();
                    Elements tempLinks = temp.select("a[href]");
                    for (Element link1 : tempLinks) {
                        if (link1.text().equalsIgnoreCase("Tuition and Costs")) {
                            Document temp2 = Jsoup.connect(link1.attr("href")).get();
                            Elements tempLink2 = temp2.select("a[href]");
                            for (Element link2 : tempLink2) {
                                if (link2.text().equalsIgnoreCase("Current Year Cost")) {
                                    Document temp3 = Jsoup.connect(link2.attr("href")).get();
                                    Elements tables = temp3.select("table");
                                    for (Element e : tables) {

                                        //getTuitionCost(e,"Freshman Cost of Attendance");
                                        //tuition = getTuitionCost(e,"Graduate Students");
                                        //	getTuitionCost(e,"All Other Undergraduates");

                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }

            }

            // get program details
            for (Element link : links) {
                try {
                    Document nodes = Jsoup.connect("http://www.gradadmiss.gatech.edu/programs-a-z").get();
                    Elements admissionLinks = nodes.select("a[href]");
                    for (Element link1 : admissionLinks) {

                        if(link1.attr("href").startsWith("node")) {
                            Document programPage = Jsoup.connect("http://gradadmiss.gatech.edu/" + link1.attr("href")).get();
                            //	 System.out.println(programPage);
                            Elements pageLinks = programPage.select("a[href]");
                            Course temp = new Course();
                            temp.setImg(img);
                            temp.setTuitionCost(tuition);

                            for (Element p : pageLinks) {
                                if (p.attr("href").startsWith("mailto")){
                                    temp.setEmail(p.attr("href"));
                                }
                                if (p.text().toLowerCase().contains("website")) {
                                    String website = p.attr("href");
                                    temp.setWebsite(website);
                                }
                            }
                            Elements h2tag = programPage.select("h2");
                            for (Element e : h2tag) {
                                if (e.attr("class").equalsIgnoreCase("title")) {
                                    String programTitle = e.text();
                                    temp.setProgramName(programTitle);

                                }
                            }
                            // break;
                            if (programPage.text().toLowerCase().contains(("General Test: Required").toLowerCase())) {
                                temp.setGreRequired("Yes");
                            }
                            temp.setToeflRequired("Yes");
                            temp.setState("GA");
                            temp.setCity("Atlanta");
                            temp.setZip("30332");
                            temp.setSchoolName("Georgia Institute Of Technology");

                            List<String> programDetails = new ArrayList();
                            programDetails.add(programPage.select("div.field-item").first().text());

                            temp.setProgramDetails(programDetails.toArray(new String[programDetails.size()]));
                            this.degreePrograms.add(temp);

                        }

                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.degreePrograms;

    }

    private String getTuitionCost(Element node, String category) {
        if (node.attr("summary").equalsIgnoreCase(category)) {
            Elements allTableRows = node.select("tr");

            for (Element tr : allTableRows) {
                // System.out.println(tr);
                if (tr.text().contains("Total Per Year (2 Semesters)")) {
                    Elements allTableData = tr.select("td");
                    String residentTuition = allTableData.get(0).text();
                    System.out.println("Georgia Residents Tuition For " + category + " is: " + residentTuition);
                    String nonResidentTuition = allTableData.size() > 1 ? allTableData.get(1).text()
                            : allTableData.get(0).text();
                    System.out.println("Non-Georgia Residents Tuition For " + category + " is: " + nonResidentTuition);

                }
                Elements allParagraphs = node.select("p");
                if (category.equalsIgnoreCase("Graduate Students")) {
                    Elements allTableData = tr.select("td");
                    if (tr.select("th").text().equalsIgnoreCase("Total Per year (2 Semesters)")) {
                        String residentTuition = allTableData.get(0).text();
                        return residentTuition;
                    }

                }
            }


        }
        return "";
    }



}