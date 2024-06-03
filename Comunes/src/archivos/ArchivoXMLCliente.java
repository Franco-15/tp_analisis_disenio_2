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

import comunes.Cliente;


public class ArchivoXMLCliente implements IArchivoCliente{
	 private static final String FILE_NAME = "clientes.xml";

	    @Override
	    public void escribirClientes(List<Cliente> clientes) {
	        try {
	            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	            Element rootElement = doc.createElement("clientes");
	            doc.appendChild(rootElement);
	            for (Cliente cliente : clientes) {
	                Element clienteElement = doc.createElement("cliente");
	                rootElement.appendChild(clienteElement);
	                createElement(doc, clienteElement, "numero_documento", cliente.getDni());
	                createElement(doc, clienteElement, "grupo_afinidad", cliente.getGrupoAfinidad());
	                createElement(doc, clienteElement, "fecha_nacimiento", cliente.getFechaNacimiento());
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
	    public List<Cliente> leerClientes() {
	        List<Cliente> clientes = new ArrayList<>();
	        try {
	            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_NAME));
	            doc.getDocumentElement().normalize();
	            NodeList nList = doc.getElementsByTagName("cliente");
	            for (int temp = 0; temp < nList.getLength(); temp++) {
	                Node nNode = nList.item(temp);
	                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) nNode;
	                    String numeroDocumento = eElement.getElementsByTagName("numero_documento").item(0).getTextContent();
	                    String grupoAfinidad = eElement.getElementsByTagName("grupo_afinidad").item(0).getTextContent();
	                    String fechaNacimiento = eElement.getElementsByTagName("fecha_nacimiento").item(0).getTextContent();
	                    clientes.add(new Cliente(numeroDocumento, grupoAfinidad, fechaNacimiento));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return clientes;
	    }

	    @Override
	    public void actualizarCliente(Cliente cliente) {
	        List<Cliente> clientes = leerClientes();
	        for (Cliente c : clientes) {
	            if (c.getDni().equals(cliente.getDni())) {
	                c.setGrupoAfinidad(cliente.getGrupoAfinidad());
	                c.setFechaNacimiento(cliente.getFechaNacimiento());
	                break;
	            }
	        }
	        escribirClientes(clientes);
	    }

	    @Override
	    public void eliminarCliente(String numeroDocumento) {
	        List<Cliente> clientes = leerClientes();
	        clientes = clientes.stream()
	                .filter(c -> !c.getDni().equals(numeroDocumento))
	                .collect(Collectors.toList());
	        escribirClientes(clientes);
	    }

	    private void createElement(Document doc, Element parent, String name, String value) {
	        Element element = doc.createElement(name);
	        element.appendChild(doc.createTextNode(value));
	        parent.appendChild(element);
	    }
}
