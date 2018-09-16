package com.KamilIsmail.MovieApp.scheduled.TVGuideController;

import com.KamilIsmail.MovieApp.dictionery.TVChanels;
import com.KamilIsmail.MovieApp.scheduled.TVGuideBean.MovieBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//http://epg.koditvepg2.com/PL/guide.xml.gz
public class ParseTVGuide {

    Logger log = LoggerFactory.getLogger(ParseTVGuide.class);

    public void run() {
        try {
            File file = new File("/Users/kamilismail/Downloads/guide.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("programme");
            List<MovieBean> movieBeanList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                MovieBean temp = getMovie(nodeList.item(i));
                if (temp != null)
                    movieBeanList.add(temp);
            }
            List<MovieBean> movieBeanListTemp = movieBeanList;
            movieBeanList = getBestMovies(movieBeanList);
            movieBeanListTemp = getWorstMovies(movieBeanListTemp);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MovieBean> getBestMovies(List<MovieBean> list) {
        return list;
    }

    private List<MovieBean> getWorstMovies(List<MovieBean> list) {
        return list;
    }

    //time 20180916000500
    private MovieBean getMovie(Node node){
        String date, chanel, title, description;

        MovieBean movieBean = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            //sprawdzenie czy jest to film + dodanie ratingu
            Element element = (Element) node;
            TVChanels tvChanels = new TVChanels();
            chanel = getAttribute("channel", element);
            if (tvChanels.ifContains(chanel)) {
                date = getAttribute("start", element);
                title = getTagValue("title", element);
                description = getTagValue("desc", element);
                movieBean = new MovieBean(date, chanel, title, description);
            } else
                return null;
        }
        return movieBean;
    }

    private String getAttribute(String tag, Element element) {
        String attribute = element.getAttribute(tag);
        return attribute;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        String value = node.getNodeValue();
        return value;
    }
}
