package gui;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


public class XmlPictureLoader implements PictureLoader {

    public PictureData loadPicture(String metaFilePath) {

        AtomicReference<String> pictureFileName = new AtomicReference<>("");
        AtomicReference<String> pictureDescription = new AtomicReference<>("");
        AtomicReference<String> pictureResolutionValue = new AtomicReference<>("1");
        double pictureResolutionValueDouble= 0.0;
        AtomicReference<String> pictureResolutionUnit = new AtomicReference<>("px");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(metaFilePath);
            doc.getDocumentElement().normalize();
            NodeList node = doc.getElementsByTagName("resolution");

            pictureDescription.set(doc.getElementsByTagName("description").item(0).getTextContent());
            pictureFileName.set(doc.getElementsByTagName("image-file").item(0).getTextContent());
            pictureResolutionValue.set(doc.getElementsByTagName("resolution").item(0).getTextContent());
            pictureResolutionUnit.set(node.item(0).getAttributes().getNamedItem("unit").getNodeValue());

        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        pictureResolutionValueDouble = Double.valueOf(String.valueOf(pictureResolutionValue));

        return new PictureData(pictureFileName, pictureDescription, pictureResolutionValue, pictureResolutionValueDouble, pictureResolutionUnit);

    }

}
