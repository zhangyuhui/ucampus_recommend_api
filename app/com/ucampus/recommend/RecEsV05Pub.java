package com.ucampus.recommend;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.SearchHit;

import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

// import java.io.*;

import org.json.JSONObject;

public class RecEsV05Pub {

    public static List<Map<String, HashMap<String, Object>>> CourseRelevance(String studentMajor, String studentMajor2, String studentMajor3, String studentDepartment) throws org.json.JSONException {

        // input studentMajor: 一级学科
        // input studentMajor2: 二级学科
        // input studentMajor3: 以前的学科 (可能只对硕博有效)
        // input studentDepartment: 所在系名字
        // 对于不存在的请输入空字符串



        String searchType = "course";

        // coefficients, need to be trained with user click data; curretly it is arbitrary
        Float universityCoef = (float) 0.0;
        Float interestCoef = (float) 0.0; //it was 1.0 but here we don't have student interest preference
        Float majorCoef = (float) 0.6;
        Float majorCoef2 = (float) 0.3;
        Float majorCoef3 = (float) 0.6;
        Float majorCoef4 = (float) 0.1;

        Float departmentCoef = (float) 0.0;

        Float languageCoef = (float) 0.0; //I made up 'chinese' preference for all students
        Float randomCoef = (float) 0.05; // used to be 0.2
        Float numMajorCoef = (float) 1.0;


        Integer querySize = 2000;
        Integer randomQuerySize = 1000;


        StringBuffer buf = new StringBuffer();


        /*
        // pull student information
        String studentMajor = student1.studentMajor; // match to 'category', 'type', 'courseName'
        Map<String, Float> studentInterest = student1.studentInterest;
        String studentLanguage = student1.studentLanguage; // match to 'language'
        String studentUniversity = student1.studentUniversity; // match to 'university'
        String studentDepartment = student1.studentDepartment; //  match to 'category', 'type', 'courseName'
        String studentMajor2 = student1.studentMajor2; //  match to 'category', 'type', 'courseName'
        String studentMajor3 = student1.studentMajor3; //  match to 'category', 'type', 'courseName'
        String studentName = student1.name; //not matched to anything...
        */

        String studentMajor4 = ""; // 学科大类

        List<String> socList = new ArrayList<String>(Arrays.asList("法学", "政治学","社会学","民族学","马克思主义理论","公安学"));
        List<String> eduList = new ArrayList<String>(Arrays.asList("教育学", "心理学","体育学"));
        List<String> litList = new ArrayList<String>(Arrays.asList("文学", "新闻传播学"));
        List<String> techList = new ArrayList<String>(Arrays.asList("力学", "工程", "与技术","建筑学","城乡规划学","风景园林学","公安技术"));
        List<String> agriList = new ArrayList<String>(Arrays.asList("作物学", "园艺学", "农业资源与环境","植物保护","畜牧学","兽医学","林学", "水产","草学"));
        List<String> sciList = new ArrayList<String>(Arrays.asList("数学", "物理学", "化学","天文学","地理学","大气科学","海洋科学", "地球物理学",
                "地质学","生物学","系统科学","科学技术史","生态学","统计学"));
        List<String> medList = new ArrayList<String>(Arrays.asList("医","药","护理"));
        List<String> armyList = new ArrayList<String>(Arrays.asList("军","战"));
        List<String> manageList = new ArrayList<String>(Arrays.asList("管理"));
        List<String> artList = new ArrayList<String>(Arrays.asList("艺术学理论", "音乐与舞蹈学", "戏剧与影视学","美术学","设计学"));



        if(studentMajor.contains("哲学"))
        {
            studentMajor4 = "哲学";
        }else if(studentMajor.contains("经济学")){
            studentMajor4 = "经济";
        }else if(ContainString(studentMajor, socList)){
            studentMajor4 = "法律";
        }
        else if(ContainString(studentMajor, eduList)){
            studentMajor4 = "教育";
        }
        else if(ContainString(studentMajor, litList)){
            studentMajor4 = "文学";
        }
        else if(ContainString(studentMajor, techList)){
            studentMajor4 = "工学";
        }
        else if(ContainString(studentMajor, agriList)){
            studentMajor4 = "农业";
        }
        else if(ContainString(studentMajor, sciList)){
            studentMajor4 = "理学";
        }
        else if(ContainString(studentMajor, medList)){
            studentMajor4 = "医药";
        }
        else if(ContainString(studentMajor, armyList)){
            studentMajor4 = "军事";
        }
        else if(ContainString(studentMajor, manageList)){
            studentMajor4 = "管理";
        }
        else if(ContainString(studentMajor, artList)){
            studentMajor4 = "艺术";
        }






        List<String> excludeList = new ArrayList<String>();
        excludeList.add("工程");
        excludeList.add("与");
        excludeList.add("系");
        excludeList.add("所");
        excludeList.add("产品");
        excludeList.add("加工");
        excludeList.add("质量");
        excludeList.add("安全");
        excludeList.add("利用");
        excludeList.add("专业");
        excludeList.add("学位");
        excludeList.add("及");
        excludeList.add("贮藏");
        excludeList.add("技术");
        excludeList.add("基础");
        excludeList.add("系统");
        excludeList.add("高等");
        excludeList.add("比较");
        excludeList.add("成人");
        excludeList.add("应用");
        excludeList.add("专门");
        excludeList.add("地区");
        excludeList.add("设计");
        excludeList.add("理论");
        excludeList.add("防护");
        excludeList.add("保护");
        excludeList.add("设施");
        excludeList.add("预防");
        excludeList.add("经理");
        excludeList.add("资源");
        excludeList.add("文献");
        excludeList.add("结合");
        excludeList.add("思想");
        excludeList.add("动员");
        excludeList.add("训练");
        excludeList.add("编制");




        List<String> includeList = new ArrayList<String>();
        includeList.add("化学");
        includeList.add("数学");
        includeList.add("理学");
        includeList.add("工学");
        includeList.add("法学");
        includeList.add("文学");
        includeList.add("哲学");
        includeList.add("林学");
        includeList.add("草学");



        List<String> includeList2 = new ArrayList<String>();
        includeList2.add("内科学");
        includeList2.add("儿科学");
        includeList2.add("妇产科学");
        includeList2.add("眼科学");
        includeList2.add("骨伤科学");
        includeList2.add("妇科学");
        includeList2.add("五官科学");


        studentDepartment =  CleanString(studentDepartment, excludeList, includeList, includeList2);
        studentMajor =  CleanString(studentMajor, excludeList, includeList, includeList2);
        studentMajor2 =  CleanString(studentMajor2, excludeList, includeList, includeList2);
        studentMajor3 =  CleanString(studentMajor3, excludeList, includeList, includeList2);
        studentMajor4 =  CleanString(studentMajor4, excludeList, includeList, includeList2);

        // feature engineering: the matched scores + nomalization

        org.elasticsearch.common.settings.Settings settings = org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder().put("cluster.name", "uspace").build();
        TransportClient client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("124.16.75.121", 9300));

        //String queryString = "{\"_type\": \"course\",\"query\": {\"match\": {\"category\": \"政治\"}}}";
        //JSONObject queryStringObject = new JSONObject(queryString);
        //SearchResponse responseMajor = client.prepareSearch().setTypes().setSource(queryStringObject.toString()).execute().actionGet();



        SearchResponse responseMajor = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(studentMajor, "courseName", "category", "type"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();


        HashMap<String, Object> tempMap = new HashMap<String, Object>();


        Map<String, HashMap<String, Object>> responseMap = new HashMap<String, HashMap<String, Object>>();
        SearchHit[] results = responseMajor.getHits().getHits();
        Float sumScore = (float) 0.0;
        Float sumSquareScore = (float) 0.0;
        Integer numScore = 0;
        Float minScore = (float) 100.0;
        for (SearchHit hit : results) {
            //HashMap<String, Float> tempMap = new HashMap<String, Float>();
            //tempMap.put("majorScore", hit.getScore());
            //responseMap.put(hit.getId(), tempMap);
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        Float sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                tempMap = new HashMap<String, Object>();
                tempMap.put("majorScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                tempMap.put("source", hit.getSource());

                responseMap.put(hit.getId(), tempMap);

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                tempMap = new HashMap<String, Object>();
                tempMap.put("majorScore", (float)1.0);
                tempMap.put("source", hit.getSource());

                responseMap.put(hit.getId(), tempMap);

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }


        }


// major 2

        SearchResponse responseMajor2 = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(studentMajor2, "courseName", "category", "type"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();

        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseMajor2.getHits().getHits();



        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore2", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore2", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("majorScore2", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore2", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore2", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("majorScore2", (float) 1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }


// major 3

        SearchResponse responseMajor3 = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(studentMajor3, "courseName", "category", "type"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();

        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseMajor3.getHits().getHits();



        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore3", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore3", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();

                    tempMap.put("majorScore3", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore3", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore3", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("majorScore3", (float) 1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }


// major 4

        SearchResponse responseMajor4 = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(studentMajor4, "courseName", "category", "type", "courseDesc"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();

        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseMajor4.getHits().getHits();



        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore4", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore3", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("majorScore4", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("majorScore4", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("majorScore4", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("majorScore4", (float) 1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }


        // department

        SearchResponse responseDepartment = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(studentDepartment, "courseName", "category", "type"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();

        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseDepartment.getHits().getHits();



        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("departmentScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("departmentScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("source", hit.getSource());

                    tempMap.put("departmentScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("departmentScore", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("departmentScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("departmentScore", (float) 1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }




/* unused features
// language
        SearchResponse responseLanguage = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.matchQuery("language", studentLanguage), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();
        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseLanguage.getHits().getHits();
        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("languageScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("languageScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("source", hit.getSource());
                    tempMap.put("languageScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap); // No such key
                }
                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("languageScore", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("languageScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("languageScore", (float) 1.0);
                    tempMap.put("source", hit.getSource());
                    responseMap.put(hit.getId(), tempMap); // No such key
                }
                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }
        SearchResponse responseUniversity = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.matchQuery("university", studentUniversity), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();
        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseUniversity.getHits().getHits();
        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("universityScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("universityScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("universityScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("source", hit.getSource());
                    responseMap.put(hit.getId(), tempMap); // No such key
                }
                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("universityScore", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("universityScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("universityScore", (float) 1.0);
                    tempMap.put("source", hit.getSource());
                    responseMap.put(hit.getId(), tempMap); // No such key
                }
                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }
        Map<String, HashMap<String, Object>> responseInterest = new HashMap<String, HashMap<String, Object>>();
        for (String key : studentInterest.keySet()) {
        SearchResponse responseInterestTemp = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        //.setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.boolQuery().should(org.elasticsearch.index.query.QueryBuilders.multiMatchQuery(key, "courseName^3", "category^3", "type^3", "courseDesc")), FilterBuilders.typeFilter(searchType)))             // Query
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.multiMatchQuery(key, "courseName", "category", "type", "courseDesc"), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(querySize).setExplain(true)
                .execute()
                .actionGet();
        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseInterestTemp.getHits().getHits();
        //System.out.println(  results.length);
        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseInterest.get(hit.getId());
                if (temp != null) {
                    tempMap = responseInterest.get(hit.getId());
                    tempMap.put(key, (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("interestScore", (float) responseInterest.get(hit.getId()).get("interestScore") + ((hit.getScore() - minScore) / sdScore + (float)1.0) * studentInterest.get(key));
                    responseInterest.put(hit.getId(), tempMap);
                    //responseInterest.get(hit.getId()).put(key, (hit.getScore()-minScore)/sdScore);
                    //responseInterest.get(hit.getId()).put("interestScore", responseInterest.get(hit.getId()).get("interestScore") + ((hit.getScore()-minScore)/sdScore)*studentInterest.get(key));
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put(key, (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("interestScore", ((hit.getScore() - minScore) / sdScore + (float)1.0) * studentInterest.get(key));
                    tempMap.put("source", hit.getSource());
                    responseInterest.put(hit.getId(), tempMap); // No such key
                }
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseInterest.get(hit.getId());
                if (temp != null) {
                    tempMap = responseInterest.get(hit.getId());
                    tempMap.put(key, (hit.getScore() - minScore) / sdScore+(float) 1.0);
                    tempMap.put("interestScore", (float) 1.0);
                    responseInterest.put(hit.getId(), tempMap);
                    //responseInterest.get(hit.getId()).put(key, (hit.getScore()-minScore)/sdScore);
                    //responseInterest.get(hit.getId()).put("interestScore", responseInterest.get(hit.getId()).get("interestScore") + ((hit.getScore()-minScore)/sdScore)*studentInterest.get(key));
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put(key, (hit.getScore() - minScore) / sdScore+(float) 1.0);
                    tempMap.put("interestScore", (float) 1.0);
                    tempMap.put("source", hit.getSource());
                    responseInterest.put(hit.getId(), tempMap); // No such key
                }
            }
            //System.out.println( key );
            //System.out.println( map_score.get(key) );
        }
    }
    for (String key : responseInterest.keySet()) {
        // for (SearchHit hit : results) {
        HashMap temp = responseMap.get(key);
        if (temp != null) {
            tempMap = responseMap.get(key);
            tempMap.put("interestScore", (responseInterest.get(key).get("interestScore")));
            responseMap.put(key, tempMap);
            //responseMap.get(hit.getId()).put("interestScore", (responseInterest.get(hit.getId()).get("interestScore")));
        } else {
            tempMap = new HashMap<String, Object>();
            tempMap.put("interestScore", (responseInterest.get(key).get("interestScore")));
            tempMap.put("source", (responseInterest.get(key).get("source")));
            responseMap.put(key, tempMap); // No such key
        }
        //System.out.println( key_id );
        //System.out.println( tempMap);
    }
*/

        String randomQueryString = "{\"function_score\" : {\"query\" : { \"match_all\": {} },\"random_score\" : {}}}";

        JSONObject randomQueryStringObject = new JSONObject(randomQueryString);
        SearchResponse responseRandom = client.prepareSearch()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(org.elasticsearch.index.query.QueryBuilders.filteredQuery(QueryBuilders.wrapperQuery(randomQueryStringObject.toString()), FilterBuilders.typeFilter(searchType)))             // Query
                .setFrom(0).setSize(randomQuerySize).setExplain(true)
                .execute()
                .actionGet();


        sumScore = (float) 0.0;
        sumSquareScore = (float) 0.0;
        numScore = 0;
        minScore = (float) 100.0;
        results = responseRandom.getHits().getHits();
        for (SearchHit hit : results) {
            sumScore = sumScore + hit.getScore();
            sumSquareScore = sumSquareScore + hit.getScore() * hit.getScore();
            numScore = numScore + 1;
            if (hit.getScore() < minScore) {
                minScore = hit.getScore();
            }
        }
        sdScore = (float) java.lang.Math.sqrt(sumSquareScore / numScore - (sumScore / numScore) * (sumScore / numScore));
        if (sdScore > 0.0) {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("randomScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("universityScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("randomScore", (hit.getScore() - minScore) / sdScore + (float)1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        } else {
            for (SearchHit hit : results) {
                HashMap temp = responseMap.get(hit.getId());
                if (temp != null) {
                    tempMap = responseMap.get(hit.getId());
                    tempMap.put("randomScore", (float) 1.0);
                    responseMap.put(hit.getId(), tempMap);
                    //responseMap.get(hit.getId()).put("universityScore", (hit.getScore()-minScore)/sdScore);
                } else {
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("randomScore", (float) 1.0);
                    tempMap.put("source", hit.getSource());

                    responseMap.put(hit.getId(), tempMap); // No such key
                }

                //System.out.println( hit.getId() );
                //System.out.println( tempMap);
            }
        }


        // calculate relevance with pre-set coefficients
        for (String key : responseMap.keySet()) {
            responseMap.get(key).put("relevance", (float) 0.0);
            Integer numMajorHit = 0;
            if (responseMap.get(key).get("universityScore") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + universityCoef * (float) responseMap.get(key).get("universityScore"));
            }
            if (responseMap.get(key).get("languageScore") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + languageCoef * (float) responseMap.get(key).get("languageScore"));
            }
            if (responseMap.get(key).get("majorScore") != null) {
                numMajorHit = numMajorHit+1;
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + majorCoef * (float) responseMap.get(key).get("majorScore"));
            }
            if (responseMap.get(key).get("majorScore2") != null) {
                numMajorHit = numMajorHit+1;
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + majorCoef2 * (float) responseMap.get(key).get("majorScore2"));
            }
            if (responseMap.get(key).get("majorScore3") != null) {
                numMajorHit = numMajorHit+1;
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + majorCoef3 * (float) responseMap.get(key).get("majorScore3"));
            }
            if (responseMap.get(key).get("majorScore4") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + majorCoef4 * (float) responseMap.get(key).get("majorScore4"));
            }
            if (responseMap.get(key).get("departmentScore") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + departmentCoef * (float) responseMap.get(key).get("departmentScore"));
            }
            if (responseMap.get(key).get("interestScore") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + interestCoef * (float) responseMap.get(key).get("interestScore"));
            }
            if (responseMap.get(key).get("randomScore") != null) {
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + randomCoef * (float) responseMap.get(key).get("randomScore"));
            }


            if(numMajorHit>1){
                responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + numMajorCoef );


            }
            responseMap.get(key).put("relevance", (float) responseMap.get(key).get("relevance") + randomCoef * (float)Math.random() );





            //System.out.println(key);
            //System.out.println( responseMap.get(key).get("relevance") );
            //System.out.println(responseMap.get(key));

            buf.append(key);
            buf.append("   ");
            buf.append(responseMap.get(key).get("relevance"));
            buf.append("   ");

        }





        List<Map<String, HashMap<String, Object>>> sortedList = SortMap.SortMapByRelevanceObject(responseMap);
        List<Map<String, HashMap<String, Object>>> sortedListTrunc = new ArrayList<Map<String, HashMap<String, Object>>>();

        //System.out.println(studentName);
        //System.out.println(studentDepartment);
        //System.out.println(studentMajor);
        //System.out.println(studentMajor2);
        // System.out.println(studentMajor3);
        //System.out.println(studentMajor4);


        for (int i=0; i<20; i++ ){
            String keyTemp = sortedList.get(i).keySet().toArray()[0].toString();
            System.out.println(keyTemp);
            System.out.println(sortedList.get(i).get(keyTemp));
            sortedListTrunc.add(sortedList.get(i));
        }
        //Map<String, List<Map<String, HashMap<String, Float>>>> studentMap =  new HashMap<String, List<Map<String, HashMap<String, Float>>>>();
        //studentMap.put(studentName,sortedListTrunc);
        //return studentMap;
        return sortedListTrunc;




        //return buf.toString();


    }



    public static String CleanString(String inputString, List<String> excludeList, List<String> includeList, List<String> includeList2)
    {
        String inputStringTemp = inputString;
        //System.out.println(inputStringTemp);
        for (String excludeString : excludeList) {
            //System.out.println(excludeString);
            //System.out.println(inputStringTemp.contains(excludeString));
            inputStringTemp = inputStringTemp.replace(excludeString, "");
        }
        //System.out.println(inputStringTemp);

        if (!(ContainString(inputString, includeList2))) {
            inputStringTemp = inputStringTemp.replace("科学", "");
        }
        //System.out.println(inputStringTemp);
        if (!(ContainString(inputString, includeList))) {
            inputStringTemp = inputStringTemp.replace("学", "");
        }

        // System.out.println(inputStringTemp);
        return inputStringTemp;
    }



    public static boolean ContainString(String inputString, List<String> stringList)
    {
        for (String tempString : stringList) {
            if(inputString.contains(tempString))
            {
                return true;
            }
        }


        return false;
    }
}
