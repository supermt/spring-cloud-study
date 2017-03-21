/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: LifeCycleLogController.java 
 * @Prject: microservice-monitor
 * @Package: edu.uestc.msstudio 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月13日 下午2:21:10 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uestc.msstudio.cloud.recording.ObjectType;
import edu.uestc.msstudio.cloud.recording.Record;
import edu.uestc.msstudio.cloud.recording.RecordRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

/** 
 * @ClassName: LifeCycleLogController 
 * @Description: 
 * this is the view controller for life cycle modeling
 * @author: MT
 * @date: 2017年3月13日 下午2:21:10  
 */
@RestController
@RequestMapping("/")
public class LifeCycleLogController {
	
	@Autowired
	private RecordRepository reDao;
	
	@GetMapping("count")
	public Object countLog(){
		return reDao.count();
	}
	
	@GetMapping("showAll")
	public Object showLog(){
		return reDao.findAll();
	}
	
	@GetMapping("targetEntity")
	public Object findTargetByIdAndType(@RequestParam String type,@RequestParam Long id){
		ObjectType obType = null;
		
		switch(type){
			case "user": obType = ObjectType.User;break;
			default: obType = ObjectType.Entitlement;
		}
		
		String idSource = "%\"id\":"+id+"%";
		String idTarget = "%\"id\":"+id+"%";
		return reDao.searchByTypeAndId(obType.ordinal(), idSource, idTarget);
	}
	
	@GetMapping("/uml/targetEntity")
	public String umlTargetByIdAndType(@RequestParam String type,@RequestParam Long id)
	{
		ObjectType obType = null;
		
		switch(type){
			case "user": obType = ObjectType.User;break;
			default: obType = ObjectType.Entitlement;
		}
		
		String idSource = "%\"id\":"+id+"%";
		String idTarget = "%\"id\":"+id+"%";
		List<Record> recordList = reDao.searchByTypeAndId(obType.ordinal(), idSource, idTarget);
		
		if (recordList.isEmpty()) {
			return "No Target Record";
		}
		
		StringBuilder source = new StringBuilder();
		source.append("@startuml\n");
		source.append("start\n");		
		source.append(recordsToUml(recordList));
		source.append("end\n");
		source.append("@enduml\n");
		return generateUML(source.toString());
	}
	
	
	@GetMapping("testuml")
	public String testStatemachine(){
		String source = "@startuml\n";
		source += "Bob -> Alice : hello\n";
		source += "@enduml\n";
		return generateUML(source);
	}
	
	@PostMapping("testact")
	public String testActivity(@RequestBody String source) {
		return generateUML(source);
	}
	
	/*
	 * This method can not run only with PlantUML
	 * Seems need another plantform
	 * */
//	@GetMapping("testState")
//	public String testStateMachine() 
//	{
//		String source = "@startuml\n";
//		source+="[*] --> Active\n";
//		source+="Active --> Inactive\n";
//		source+="@enduml\n";
//		return generateUML(source);
//	}
	
	private String generateUML(String source){
		String desc = "";
		try {
			SourceStringReader reader = new SourceStringReader(source);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// Write the first image to "os"
			desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();
	
			// The XML is stored into svg
			String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
			return svg;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(desc);
			return null;
		}
	}
	
	private String recordsToUml(List<Record> input){
		
		StringBuilder result = new StringBuilder();

		String currentSource = null;
		String currentTarget = null;
		
		for (Record item:input){
			if (item.getAction().toString().contains("query")){
				continue;
			}
			
			if (onetoone(item)){
				// one entity's action like create,transfer
				currentSource = item.getJsonSource();
				if (!currentSource.equals(currentTarget)){
					// currentSource is not empty and equals the last target source
					// insert source entity
					result.append(":");	
					result.append(item.getJsonSource());
					currentSource = item.getJsonSource();
					result.append(";\n");
				}
				
				// insert action
				result.append("-> " + item.getAction().toString()+";\n");	
				
				// insert target entity
				result.append(":");
				result.append(item.getJsonTarget());
				currentTarget = item.getJsonTarget();
				result.append(";\n");
				
			}else if (ontomany(item)){
				// one entity transfer to many other entities,like split
				currentSource = item.getJsonSource();
				if (!currentSource.equals(currentTarget)){
					// currentSource is not empty and equals the last target source
					// insert source entity
					result.append(":");	
					result.append(item.getJsonSource());
					currentSource = item.getJsonSource();
					result.append(";\n");
				}
				
				// insert action
				result.append("-> " + item.getAction().toString()+";\n");	
				
				// insert target entity
				result.append("split\n");
				
				// phrase to list
				JSONArray objs = JSONArray.fromObject(item.getJsonTarget());
				JSONObject obj = null;
				int i=0;
				for (i=0;i<objs.size()-1;i++){
					obj=objs.getJSONObject(i);
					result.append(":");
					result.append(obj.toString());
					result.append(";\n");
					result.append("split again\n");
				}
				obj=objs.getJSONObject(i);
				result.append(":");
				result.append(obj.toString());
				result.append(";\n");
				
				result.append("end split\n");
				currentTarget = item.getJsonTarget();
				
			}else {
				// many entities transfer to one entity,like merge
				
				result.append("split\n");
				
				
				JSONArray objs = JSONArray.fromObject(item.getJsonSource());
				JSONObject obj = null;
				int i=0;
				for (i=0;i<objs.size()-1;i++){
					obj=objs.getJSONObject(i);
					result.append(":");
					result.append(obj.toString());
					result.append(";\n");
					result.append("split again\n");
				}
				obj=objs.getJSONObject(i);
				result.append(":");
				result.append(obj.toString());
				result.append(";\n");
				
				result.append("end split\n");
				currentSource = item.getJsonSource();
				
				// insert action
				result.append("-> " + item.getAction().toString()+";\n");	
				
				// insert target entity
				result.append(":");
				result.append(item.getJsonTarget());
				currentTarget = item.getJsonTarget();
				result.append(";\n");
			}
		}
		
		return result.toString();
	}

	private boolean onetoone(Record item) {
		boolean result = false;
		switch(item.getAction()){
			case updateUser: result = true;break;
			case queryByUser:result = true;break;
			case queryUserById:result = true;break;
			default: break;
		}
		return result;
	}

	private boolean ontomany(Record item) {
		boolean result = false;
		switch(item.getAction()){
			case splitUser: result = true;break;
			default: break;
		}
		return result;
	}
	
	
}
