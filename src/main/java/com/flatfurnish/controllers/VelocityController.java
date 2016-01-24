package com.flatfurnish.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;

public class VelocityController {
	
	private String vmLocalFilePath = "";
	private String vmFileName = "";
	private String xmlFileName = "";
	private Properties velocityProperties;
	private VelocityEngine ve;
	private VelocityContext context;
	private Template temp;
	private StringWriter wr;
	private FileWriter fw;
	
	public StringWriter generateVelocityTemplate(List<Map<String, String>> valuesListParam){
		
		velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path",
				vmLocalFilePath);
		ve = new VelocityEngine();
		
		try {
			ve.init(velocityProperties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		try {
			temp = ve.getTemplate(vmFileName);
			context = new VelocityContext();
			context.put("allProducts", valuesListParam);
			wr = new StringWriter();
			temp.merge( context, wr );
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		this.createXmlFile(wr);

		System.out.println( wr.toString() );
		
		return wr;
		
	}
	
	public void createXmlFile(StringWriter writerParam){
		
		try {
			fw = new FileWriter(xmlFileName);
			fw.write(writerParam.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	

	
	public String getXmlFileName() {
		return xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public String getVmLocalFilePath() {
		return vmLocalFilePath;
	}

	public void setVmLocalFilePath(String vmLocalFilePath) {
		this.vmLocalFilePath = vmLocalFilePath;
	}

	public String getVmFileName() {
		return vmFileName;
	}

	public void setVmFileName(String vmFileName) {
		this.vmFileName = vmFileName;
	}





}
