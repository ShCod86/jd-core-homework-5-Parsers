import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parsers {

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader).withMappingStrategy(strategy).build();
            staff = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        long id = 0;
        String firstName = "";
        String lastName = "";
        String country = "";
        int age = 0;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));

        Node root = document.getFirstChild();
        NodeList rootChildes = root.getChildNodes();

        for (int i = 0; i < rootChildes.getLength(); i++) {
            if (rootChildes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element employeeElement = (Element) rootChildes.item(i);
                NodeList employeeChild = employeeElement.getChildNodes();
                for (int j = 0; j < employeeChild.getLength(); j++) {
                    if (employeeChild.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element employeeChildElement = (Element) employeeChild.item(j);
                        switch (employeeChildElement.getNodeName()) {
                            case "id": {
                                id = Long.parseLong(employeeChildElement.getTextContent());
                                break;
                            }
                            case "firstName": {
                                firstName = employeeChildElement.getTextContent();
                                break;
                            }
                            case "lastName": {
                                lastName = employeeChildElement.getTextContent();
                                break;
                            }
                            case "country": {
                                country = employeeChildElement.getTextContent();
                                break;
                            }
                            case "age": {
                                age = Integer.parseInt(employeeChildElement.getTextContent());
                                break;
                            }
                        }
                    }
                }
                Employee employee = new Employee(id, firstName, lastName, country, age);
                list.add(employee);
            }
        }
        return list;
    }

    public static String readString(String fileName) {
        String jsonString = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
           jsonString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
