package com.flatfurnish.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class FOPPdfController {
	
	private String xslFileName = "";
	private String templateFileNameXML = "";
	private String fileLocation = "";
	private String pdfFileName = "";
	
	private File xsltFile;
	private StreamSource xmlSource;
	private FopFactory fopFactory;
	private FOUserAgent foUserAgent;
	private OutputStream out;
	private Fop fop;
	private TransformerFactory factory;
	private Transformer transformer;
	private Result res;
	private byte[] byteArray;

    public void convertXSLToPDF(StringWriter writerParam)  throws IOException, FOPException, TransformerException {
        
        xsltFile = new File(fileLocation+"/"+xslFileName);
        if(!xsltFile.exists()){
        	xsltFile.createNewFile();
        }

        byteArray = writerParam.toString().getBytes();
        
        xmlSource = new StreamSource(new ByteArrayInputStream(byteArray));
        fopFactory = FopFactory.newInstance(new File(fileLocation).toURI());
        foUserAgent = fopFactory.newFOUserAgent();
        out = new java.io.FileOutputStream(pdfFileName);
    
        try {   
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer(new StreamSource(xsltFile));
            res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }

	public String getXslFileName() {
		return xslFileName;
	}

	public void setXslFileName(String xslFileName) {
		this.xslFileName = xslFileName;
	}

	public String getTemplateFileNameXML() {
		return templateFileNameXML;
	}

	public void setTemplateFileNameXML(String templateFileNameXML) {
		this.templateFileNameXML = templateFileNameXML;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

}