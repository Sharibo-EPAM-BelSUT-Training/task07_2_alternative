package gmail.alexejkrawez;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class ChangeXML implements Runnable {
    
    private String path;
    private String pathDirOut;

    ChangeXML(String path, String pathDirOut) {
        this.path = path;
        this.pathDirOut = pathDirOut;
    }

    public void run() {

        try {
            final File xmlFile = new File(path);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(xmlFile);
            document.normalize();

            Node pointOfSale = document.getElementsByTagName("PointOfSale").item(0);
            NamedNodeMap attributes = pointOfSale.getAttributes();

            for (int n = 0; n < attributes.getLength(); n++) {
                Node node = attributes.item(n);

                if (node.getNodeName().equals("ParentPointOfSale")) {
                    node.setTextContent("ADC_" + node.getTextContent());
                } else if (node.getNodeName().equals("PointOfSaleCode")) {
                    node.setTextContent("ADC_" + node.getTextContent());
                }
            }

            Node pointOfSaleDescription = document.getElementsByTagName("PointOfSaleDescription").item(0);
            attributes = pointOfSaleDescription.getAttributes();

            for (int n = 0; n < attributes.getLength(); n++) {
                Node node = attributes.item(n);

                if (node.getNodeName().equals("Description")) {
                    node.setTextContent("ADC_" + node.getTextContent());
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            pathDirOut = pathDirOut + File.separator +
                    xmlFile.getName().replace("PointOfSaleManageSvRQ_", "PointOfSaleManageSvRQ_ADC_");
            File file = new File(pathDirOut);
            System.out.println(file.getName()); //печатает в консоль названия изменённых xml-файлов
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (org.xml.sax.SAXException | IOException | ParserConfigurationException
                | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException te) {
            te.printStackTrace();
        }

    }


}