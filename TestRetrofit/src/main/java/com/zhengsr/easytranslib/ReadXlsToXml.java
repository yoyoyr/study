package com.zhengsr.easytranslib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * @author zhengshaorui 2018/6/24
 */
public class ReadXlsToXml {

    public static String en = "strings.xml"; //要生成的 array 的名字
    public static String ch = "strings-ch.xml"; //要生成的 array 的名字
    private static String ROOT_PATH; // 当前路径

    public static void main(String[] args) {
        File file = new File("");
        ROOT_PATH = file.getAbsolutePath();
        List<Data> ens = domList(ROOT_PATH + File.separator + en);
        Map<String, String> chs = domMap(ROOT_PATH + File.separator + ch);
        for (Data d : ens) {
            String value = chs.get(d.name);
            if (value != null) {
                System.out.println("key = " + d.name);
                System.out.println(d.value);
                System.out.println(value);
                System.out.println("==================================");
            } else {
                System.out.println("key = " + d.name);
                System.out.println(d.value);
                System.out.println("==================================");
            }
        }

    }

    public static List<Data> domList(String fileName) {
        List<Data> persons = new ArrayList<>();
        //1首先利用DocumentBuilderFactory 创建一个DocumentBuilderFactory实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream is = null;
        try {
            //2利用DocumentBuilderFactory 创建一个DocumentBuilder实例
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(fileName);
            System.out.println("path = " + fileName);
            is = new FileInputStream(file);
            //3加载整个文档(Document)
            Document document = builder.parse(is);
            //4获取文档的根节点(Element)
            Element element = document.getDocumentElement();
            //5获取根节点下所有标签为person的子节点
            NodeList items = element.getElementsByTagName("string");
            for (int i = 0; i < items.getLength(); i++) {
                Data person = new Data();
                //得到一个person节点
                Element personNode = (Element) items.item(i);
                //得到person节点中的id的属性值
                person.name = personNode.getAttribute("name");
                person.value = personNode.getFirstChild().getNodeValue();
                persons.add(person);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return persons;

    }

    public static Map<String, String> domMap(String fileName) {
        Map<String, String> persons = new HashMap<>();
        //1首先利用DocumentBuilderFactory 创建一个DocumentBuilderFactory实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream is = null;
        try {
            //2利用DocumentBuilderFactory 创建一个DocumentBuilder实例
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(fileName);
            System.out.println("path = " + fileName);
            is = new FileInputStream(file);
            //3加载整个文档(Document)
            Document document = builder.parse(is);
            //4获取文档的根节点(Element)
            Element element = document.getDocumentElement();
            //5获取根节点下所有标签为person的子节点
            NodeList items = element.getElementsByTagName("string");
            for (int i = 0; i < items.getLength(); i++) {
                Data person = new Data();
                //得到一个person节点
                Element personNode = (Element) items.item(i);
                //得到person节点中的id的属性值
                person.name = personNode.getAttribute("name");
                person.value = personNode.getFirstChild().getNodeValue();
                persons.put(person.name, person.value);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return persons;

    }


}
