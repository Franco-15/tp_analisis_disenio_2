package archivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArchivoXMLLogs implements IArchivoLogs{

	private static final String FILE_NAME = "logs.xml";

    @Override
    public void escribirLogs(List<Log> logs) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = doc.createElement("logs");
            doc.appendChild(rootElement);
            for (Log log : logs) {
                Element logElement = doc.createElement("log");
                rootElement.appendChild(logElement);
                createElement(doc, logElement, "numero_documento", log.getNumeroDocumento());
                createElement(doc, logElement, "evento", log.getEvento());
                createElement(doc, logElement, "fecha_evento", log.getFechaEvento());
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new FileWriter(FILE_NAME));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> leerLogs() {
        List<Log> logs = new ArrayList<>();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_NAME));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("log");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String numeroDocumento = eElement.getElementsByTagName("numero_documento").item(0).getTextContent();
                    String evento = eElement.getElementsByTagName("evento").item(0).getTextContent();
                    String fechaEvento = eElement.getElementsByTagName("fecha_evento").item(0).getTextContent();
                    logs.add(new Log(numeroDocumento, evento, fechaEvento));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    @Override
    public void actualizarLog(Log log) {
        List<Log> logs = leerLogs();
        for (Log l : logs) {
            if (l.getNumeroDocumento().equals(log.getNumeroDocumento()) && l.getEvento().equals(log.getEvento())) {
                l.setFechaEvento(log.getFechaEvento());
                break;
            }
        }
        escribirLogs(logs);
    }

    @Override
    public void eliminarLog(String numeroDocumento, String evento) {
        List<Log> logs = leerLogs();
        logs = logs.stream()
                .filter(l -> !(l.getNumeroDocumento().equals(numeroDocumento) && l.getEvento().equals(evento)))
                .collect(Collectors.toList());
        escribirLogs(logs);
    }

    private void createElement(Document doc, Element parent, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }
}
