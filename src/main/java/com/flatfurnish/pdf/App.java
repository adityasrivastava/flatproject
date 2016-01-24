package com.flatfurnish.pdf;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import com.flatfurnish.controllers.FOPPdfController;
import com.flatfurnish.controllers.FileReadController;
import com.flatfurnish.controllers.VelocityController;


public class App 
{
	private static String workspaceLocation = new File(".").getAbsolutePath();
	private static String vmFileName = "";
	private static String xmlFileName = "";
	private static String xslFileName = "";
	private static String pdfFileName = "";
	private static String csvFileName = "";
	
	private static StringWriter wr;

	public static void main(String[] args) {
		
		vmFileName = args[0];
		xslFileName = args[1];
		csvFileName = args[2];
		pdfFileName = args[3];
		
		// File Read
		
		FileReadController fr = new FileReadController();
		List<Map<String, String>> listOfDetails = fr.readFileAndGetDetails(csvFileName);
		
		// Generate Template
	
		VelocityController vc = new VelocityController();
		vc.setVmFileName(vmFileName);
		vc.setVmLocalFilePath(workspaceLocation);
		vc.setXmlFileName(xmlFileName);
		wr = vc.generateVelocityTemplate(listOfDetails);
		
		// PDF Generation 
		
		FOPPdfController fp = new FOPPdfController();
		fp.setXslFileName(xslFileName);
		fp.setFileLocation(workspaceLocation);
		fp.setPdfFileName(pdfFileName);
		fp.setTemplateFileNameXML(xmlFileName);
		
		try {
			fp.convertXSLToPDF(wr);
		} catch (FOPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
