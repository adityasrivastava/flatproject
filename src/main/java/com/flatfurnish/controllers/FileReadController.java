package com.flatfurnish.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReadController {

	private BufferedReader br = null;
	private String line = "";
	private String cvsSplitBy = ",";
	private List<Map<String, String>> detailList;
	private Map<String,String> rowMap;
	
	public List<Map<String, String>> readFileAndGetDetails(String fileName){
		detailList = new ArrayList();
		
		try {

			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(cvsSplitBy);
				rowMap = new HashMap<String,String>();
				rowMap.put("item",row[0]);
				rowMap.put("price", row[1]);
				rowMap.put("discount", row[2]);
				rowMap.put("total", row[3]);
				detailList.add(rowMap);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done Getting Data");
	
		return detailList;
	}


}
