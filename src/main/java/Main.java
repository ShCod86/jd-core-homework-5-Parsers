import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = Parsers.parseCSV(columnMapping, fileName);
        String json = Writers.listToJson(list);
        Writers.writeString(json, "data.json");

        List<Employee> list2 = Parsers.parseXML("data.xml");
        String jsonXml = Writers.listToJson(list2);
        Writers.writeString(jsonXml, "data2.json");

        String jsonParse = Parsers.readString("data.json");
        List<Employee> list3 = Writers.jsonToList(jsonParse);
        list3.forEach(System.out::println);
    }
}
