package edu.asu.lerna.iolaus.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.sys.Prop;

import edu.asu.lerna.iolaus.domain.json.IJsonNode;
import edu.asu.lerna.iolaus.domain.json.IJsonRelation;
import edu.asu.lerna.iolaus.domain.json.impl.JsonNode;
import edu.asu.lerna.iolaus.domain.misc.ReturnElementsOfOTC;
import edu.asu.lerna.iolaus.domain.misc.Tree;
import edu.asu.lerna.iolaus.domain.queryobject.INode;
import edu.asu.lerna.iolaus.domain.queryobject.IQuery;
import edu.asu.lerna.iolaus.domain.queryobject.IRelNode;
import edu.asu.lerna.iolaus.domain.queryobject.PropertyOf;
import edu.asu.lerna.iolaus.domain.queryobject.impl.Node;
import edu.asu.lerna.iolaus.domain.queryobject.impl.RelNode;
import edu.asu.lerna.iolaus.service.ICacheManager;
import edu.asu.lerna.iolaus.service.IObjectToCypher;
import edu.asu.lerna.iolaus.service.IRepositoryManager;

@Service
public class RepositoryManager implements IRepositoryManager{

	@Autowired
	private ICacheManager cacheManager;
	
	@Autowired
	private IObjectToCypher objectToCypher; 

	private static final Logger logger = LoggerFactory
			.getLogger(RepositoryManager.class);
	
	@Override
	public void executeQuery(IQuery q)
	{
		Tree tree=breakdownQuery(q);
		//traverseTreeInPostOrder(tree);
		
		//TODO: Execute cypher by calling the cache manager
		//cacheManager.executeQuery("");
	}
	@SuppressWarnings("rawtypes")
	private Map<String, List<Object>> processResults(List<List> results) {
		Map<String, List<Object>> processedResults=new LinkedHashMap<String,List<Object>>();
		String target=PropertyOf.TARGET.toString();
		String rel=PropertyOf.RELATION.toString();
		List<Object> column;
		if(results != null)
		{
			for(List rowList: results)
			{
				//System.out.println("--------------------------------------------");
				int targetCount=1;
				int relationshipCount=1;
				boolean flag=true;
				for(Object obj: rowList)
				{
					
					if(obj instanceof IJsonNode)
					{
						IJsonNode jsonNode = (IJsonNode) obj;
						String current;
						if(flag){
							current=PropertyOf.SOURCE.toString();
							flag=false;
						}else{
							current=target+targetCount;
							targetCount++;
						}
						if(!processedResults.containsKey(current)){
							column=new LinkedList<Object>();
							processedResults.put(current, column);
						}
						else{
							column=processedResults.get(current);
						}
						column.add(jsonNode);
					}
					else if(obj instanceof IJsonRelation)
					{
						IJsonRelation jsonRelation = (IJsonRelation) obj;
						if(!processedResults.containsKey(rel+relationshipCount)){
							column=new LinkedList<Object>();
							processedResults.put(rel+relationshipCount, column);
						}
						else{
							column=processedResults.get(rel+relationshipCount);
						}
						column.add(jsonRelation);
						relationshipCount++;
					}
				}
				//System.out.println("--------------------------------------------");
			}
		}
	
		return processedResults;
	}

	private void traverseTreeInPostOrder(Tree tree) {
		Map<String,List<List<String>>> sourceToTargetLabelMap=tree.getSourceToTargetLabelMap();
		Map<String,String> targetJsonMap=tree.getTargetJsonMap();
		Map<String,String> oldLabelToNewLabelMap=tree.getOldLabelToNewLabelMap();
		Map<String,List<Integer>> currentChildMap=new HashMap<String, List<Integer>>();
		Map<String,Map<String,List<Object>>>aggregateResults=new HashMap<String, Map<String,List<Object>>>();
		initializeCurrentChildCounter(currentChildMap,sourceToTargetLabelMap);
		Stack<String> stack=new Stack<String>();
		String sourceLabel=PropertyOf.SOURCE.toString();
		System.out.println(sourceToTargetLabelMap);
		int outerCount=0;
		int innerCount=0;
		boolean flag=true;
		do{
			while(flag){
				if(!sourceToTargetLabelMap.containsKey(sourceLabel)){
					System.out.println("***"+sourceLabel);
					break;
				}
				innerCount=currentChildMap.get(sourceLabel).get(1);
				outerCount=currentChildMap.get(sourceLabel).get(0);
				int i=0;
				for(List<String >childList:sourceToTargetLabelMap.get(sourceLabel)){
					int j=0;
					for(String siblings:childList){
						if(i!=outerCount){
							if(sourceToTargetLabelMap.containsKey(siblings)){
								stack.push(siblings);
							}
						}else{
							if(i!=innerCount){
								if(sourceToTargetLabelMap.containsKey(siblings)){
									stack.push(siblings);
								}
							}
						}
						j++;
					}
					i++;
				}
				if(sourceToTargetLabelMap.get(sourceLabel).get(outerCount).size()-1>=innerCount+1){
					currentChildMap.get(sourceLabel).set(1, innerCount+1);
				}
				else if(sourceToTargetLabelMap.get(sourceLabel).size()-1>outerCount+1) {
					currentChildMap.get(sourceLabel).set(0, outerCount+1);
					currentChildMap.get(sourceLabel).set(1, 0);
				}else{
					currentChildMap.get(sourceLabel).set(0, -1);
					currentChildMap.get(sourceLabel).set(1, -1);
				}
				stack.push(sourceLabel);
				sourceLabel=sourceToTargetLabelMap.get(sourceLabel).get(outerCount).get(innerCount);
			}
			sourceLabel=stack.pop();
			if(currentChildMap.get(sourceLabel).get(0)!=-1){
				String parent=sourceLabel;
				sourceLabel=stack.pop();
				stack.push(parent);
				flag=true;
			}else{
				String json=null;
				List<List> results=cacheManager.executeQuery(json, null);
				Map<String,List<Object>> processedResults=processResults(results);
				aggregateResults(aggregateResults,processedResults,sourceToTargetLabelMap,oldLabelToNewLabelMap,sourceLabel);
				System.out.println(sourceLabel);
				flag=false;
			}
		}while(!stack.isEmpty());
	}
	
	
	
	private void aggregateResults(
			Map<String, Map<String, List<Object>>> aggregatedResults,
			Map<String, List<Object>> processedResults,
			Map<String, List<List<String>>> nestedLabelMap, Map<String, String> oldLabelToNewLabelMap, String root) {
		
			Map<Integer,Map<String,List<Object>>> resultsOfTargets=new LinkedHashMap<Integer,Map<String, List<Object>>>();
			int outerForCounter=0;
			for(List<String> targetsOfSameSource:nestedLabelMap.get(root)){
				for(String sameTargets:targetsOfSameSource){
					if(nestedLabelMap.containsKey(sameTargets)){
						unionOfResults(aggregatedResults,resultsOfTargets,outerForCounter,sameTargets);
					}	
				}
				outerForCounter++;
			}
			Map<String, List<Object>> sourceQueryResults=new LinkedHashMap<String, List<Object>>();
			intersectionOfResultsWithSourceQuery(sourceQueryResults,processedResults,resultsOfTargets,oldLabelToNewLabelMap,nestedLabelMap,root);	
	}
	
	private void intersectionOfResultsWithSourceQuery(
			Map<String, List<Object>> sourceQueryResults,Map<String, List<Object>> processedResults,Map<Integer, Map<String, List<Object>>> resultsOfTargets,
			Map<String, String> oldLabelToNewLabelMap, Map<String, List<List<String>>> nestedLabelMap, String root) {
		
		
		Map<Integer,Map<String, List<Object>>>intermediateResults=new LinkedHashMap<Integer,Map<String, List<Object>>>();
		intermediateResults.put(0, processedResults);
		
		for(int loopCounter=0;loopCounter<resultsOfTargets.size();loopCounter++){
			
			Map<String,List<Object>> currentIterationResults=new LinkedHashMap<String,List<Object>>();
			intermediateResults.put(loopCounter+1, currentIterationResults);
			Iterator<Entry<String, List<Object>>> intermediateResultsIterator = intermediateResults.get(loopCounter).entrySet().iterator();
			Iterator<Entry<String, List<Object>>> tempResultsIterator = resultsOfTargets.get(loopCounter).entrySet().iterator();
			Map<Integer,Iterator<Object>> iterator=new HashMap<Integer,Iterator<Object>>();
			int targetCount=1;
			int relationshipCount=1;
			String[] labels=new String[intermediateResults.get(loopCounter).size()];	
			int i=0;
			boolean flag=true;
			String targetNode=nestedLabelMap.get(root).get(loopCounter).get(0);
			String oldTargetMapping=oldLabelToNewLabelMap.get(targetNode);
			int targetId=0;
			//This loop creates labels for the results of source query
			while(intermediateResultsIterator.hasNext()){
				 Map.Entry<String,List<Object>> pairs = (Map.Entry<String,List<Object>>)intermediateResultsIterator.next();
				 List<Object> column=(List<Object>) pairs.getValue();
				 String label="";
				 if(column.get(0) instanceof JsonNode){
					 if(flag){
						 flag=false;
						 label=PropertyOf.SOURCE.toString();
					 }else{
						 label=PropertyOf.TARGET.toString()+targetCount;
						 if(label.equals(oldTargetMapping)){
							 targetId=i;
						 }
						 targetCount++;
					 }
				 }else{
					 label=PropertyOf.RELATION.toString()+relationshipCount;
					 relationshipCount++;
				 }
				 labels[i]=label;
				 currentIterationResults.put(label, new LinkedList<Object>());
				 iterator.put(i++, column.iterator());
			}
			
			int startOfTempResults=i;
	
			//This loop creates labels for the results of Target query
			while(tempResultsIterator.hasNext()){
				 Map.Entry<String,List<Object>> pairs = (Map.Entry<String,List<Object>>)tempResultsIterator.next();
				 List<Object> column=(List<Object>) pairs.getValue();
				 String label="";
				 if(column.get(0) instanceof JsonNode){
					 if(flag){
						 flag=false;
						 label=PropertyOf.SOURCE.toString();
					 }else{
						 label=PropertyOf.TARGET.toString()+targetCount;
						 currentIterationResults.put(label, new LinkedList<Object>());
						 targetCount++;
					 }
				 }else{
					 label=PropertyOf.RELATION.toString()+relationshipCount;
					 currentIterationResults.put(label, new LinkedList<Object>());
					 relationshipCount++;
				 }
				 labels[i]=label;
				 iterator.put(i++, column.iterator());
			}
			
			List<Object> row;
			while(iterator.get(targetId).hasNext()){
				row=new LinkedList<Object>();
				createRow(row,iterator,startOfTempResults);
				JsonNode node=(JsonNode)row.get(targetId);
				String id=node.getId();
				Map<Integer,List<Object>> matchedRows = new LinkedHashMap<Integer, List<Object>>();
				getMatchedRows(matchedRows,iterator,id,startOfTempResults,labels);
				reinitializeIterators(iterator,resultsOfTargets.get(loopCounter),startOfTempResults);
				cartesianProduct(row,matchedRows,startOfTempResults,currentIterationResults,labels);	
			}
		}
		
	}
	
	private void createRow(List<Object> row,
			Map<Integer, Iterator<Object>> iterator, int startOfTempResults) {
		for(int i=0;i<startOfTempResults;i++){
			row.add(iterator.get(i).next());
		}
	}
	private void getMatchedRows(Map<Integer, List<Object>> matchedRows,
			Map<Integer, Iterator<Object>> iterator, String id,
			int startOfTempResults, String[] labels) {
		int rowCount=0;
		while(iterator.get(startOfTempResults).hasNext()){
			JsonNode node=(JsonNode) iterator.get(startOfTempResults).next();
			List<Object> newRow=null;
			boolean flag=false;
			if(node.getId().equals(id)){
				newRow=new LinkedList<Object>();
				matchedRows.put(rowCount++, newRow);
				flag=true;
			}	
			for(int j=startOfTempResults+1;j<labels.length;j++){
				Object obj=iterator.get(j).next();
				if(flag){
					newRow.add(obj);
				}
			}
		}
	}
	
	private void cartesianProduct(List<Object> row,
			Map<Integer, List<Object>> matchedRows, int startOfTempResults,
			Map<String, List<Object>> currentResults, String[] labels) {
		if(matchedRows.size()!=0){
			for(int x=0;x<matchedRows.size();x++){
				int j=0;
				for(;j<startOfTempResults;j++){
					currentResults.get(labels[j]).add(row.get(j));
				}
				j=startOfTempResults+1;
				for(Object obj:matchedRows.get(x)){
					currentResults.get(labels[j++]).add(obj);	
				}
			}
		}	
	}
	
	private void reinitializeIterators(
			Map<Integer, Iterator<Object>> iterator,
			Map<String, List<Object>> map, int startOfTempResults) {
		for(Entry<String, List<Object>> entry:map.entrySet()){
			iterator.put(startOfTempResults++, entry.getValue().iterator());
		}
	}
	private void unionOfResults(
			Map<String, Map<String, List<Object>>> aggregateResults,
			Map<Integer, Map<String, List<Object>>> tempResults,
			int outerForCounter, String sibling) {
		
		Map<String, List<Object>> childResult;
		if(!tempResults.containsKey(outerForCounter)){
			tempResults.put(outerForCounter, aggregateResults.get(sibling));
		}else{
			childResult=tempResults.get(outerForCounter);
			Map<String,List<Object>> siblingResult=aggregateResults.get(sibling);
			Iterator<Entry<String, List<Object>>> itr = siblingResult.entrySet().iterator();
			Map<Integer,Iterator<Object>> iterator=new HashMap<Integer,Iterator<Object>>();
			String[] labels=new String[siblingResult.size()];
			int i=0;
			while(itr.hasNext()){
				 Map.Entry<String,List<Object>> pairs = (Map.Entry<String,List<Object>>)itr.next();
				 List<Object> column=(List<Object>) pairs.getValue();
				 labels[i]=pairs.getKey();
				 iterator.put(i++, column.iterator());
			}
			List<Object> sourceColumn;
			while(iterator.get(0).hasNext()){
				for(i=0;i<siblingResult.size();i++){
					sourceColumn=childResult.get(labels[i]);
					Object value = iterator.get(i).next();
					sourceColumn.add(value);
				 }
			}
		}	
	}
	
	private void initializeCurrentChildCounter(Map<String, List<Integer>> currentChildMap,
			Map<String, List<List<String>>> nestedProblemMap) {
		
		for(Entry<String, List<List<String>>> entry:nestedProblemMap.entrySet()){
			List<Integer> currentCountList=new ArrayList<Integer>();
			currentCountList.add(0);
			currentCountList.add(0);
			currentChildMap.put(entry.getKey(), currentCountList);
		}
	}
	/**
	 * @author Karan
	 * It takes IQuery as a input parameter and breaks down a single queries in multiple queries.
	 * targetJsonMap is a mapping of target label with its json query.
	 * oldLabelToNewLableMap is a mapping of the labels used in the query with unique labels created by parseNodeListObject.
	 * nestedProblemMap is mapping of target label with the targets of their nested jsons.
	 * It returns tree structure which is required for the aggregating results.
	 **/
	@Override
	public Tree breakdownQuery(IQuery q){
		INode n = q.getNode();
		Map<IRelNode,String> parsedNodeToLabelMap=new LinkedHashMap<IRelNode,String>();
		Map<IRelNode,String> allNodeToLabelMap=new LinkedHashMap<IRelNode,String>();
		Map<String,List<List<String>>> sourceToTargetLabelMap=new LinkedHashMap<String, List<List<String>>>();
		Map<String,String> targetJsonMap=new LinkedHashMap<String,String>();
		Map<String,String> oldLabelToNewLabelMap=new LinkedHashMap<String,String>();
		
		Tree tree=new Tree();
		int targetCounter=1;
		int counter=0;
		String source="";
		List<IRelNode> keyElements=new ArrayList<IRelNode>();
		if(n!=null){
			ReturnElementsOfOTC nodeListObject = objectToCypher.objectToCypher(n);
			source=PropertyOf.SOURCE.toString();
			targetCounter=parseNodeListObject(nodeListObject,allNodeToLabelMap,parsedNodeToLabelMap,sourceToTargetLabelMap,targetJsonMap,oldLabelToNewLabelMap,targetCounter,keyElements,source);
			IRelNode relNode=null;
			if(keyElements.size()!=0){
				while((relNode=keyElements.get(counter++))!=null){
					nodeListObject = objectToCypher.objectToCypher(relNode);
					source=allNodeToLabelMap.get(relNode);
					parsedNodeToLabelMap.put(relNode,source);
					targetCounter=parseNodeListObject(nodeListObject,allNodeToLabelMap,parsedNodeToLabelMap,sourceToTargetLabelMap,targetJsonMap,oldLabelToNewLabelMap,targetCounter,keyElements,source);
					if(counter==allNodeToLabelMap.size()){
						break;
					}
				}
			}
		}else{
			logger.info("Node is null");
		}
		tree.setSourceToTargetLabelMap(sourceToTargetLabelMap);
		tree.setTargetJsonMap(targetJsonMap);
		tree.setOldLabelToNewLabelMap(oldLabelToNewLabelMap);
		return tree;
	}
	
	
	/**
	 * @author Karan
	 * This method parse the results of objectToCypher method. It returns the list of two elements. First is json and second is objectToLabelMap.
	 * It just considers the entry with key of type IRelNode and ignores everything else
	 **/
	public int parseNodeListObject(ReturnElementsOfOTC nodeListObject, Map<IRelNode, String> allElements, Map<IRelNode, String> parsedElements, Map<String, List<List<String>>> nestedProblemMap, Map<String, String> targetJsonMap, Map<String, String> oldLabelToNewLabelMap, int targetCounter, List<IRelNode> keyElements, String source){
		String jsonQuery = nodeListObject.getJson();
		Map<Object,String> objectToLabelMap = nodeListObject.getObjectToTargetLabelMap();
		targetJsonMap.put(source, jsonQuery);
		logger.info("***********************************\nJson Query : "+jsonQuery+"\n***********************************\n");
		List<String> subProblemList=new ArrayList<String>();
		List<List<String>> problemList=new ArrayList<List<String>>();
		String lastTarget="";
		for (Map.Entry<Object, String> entry : objectToLabelMap.entrySet()){
		    Object obj=entry.getKey();
		    if(obj instanceof RelNode){
				IRelNode relNode=(IRelNode)obj;
		    	if(!parsedElements.containsKey(relNode)){
		    		if(!objectToLabelMap.get(relNode).equals(lastTarget)){
		    			subProblemList=new ArrayList<String>();
		    			problemList.add(subProblemList);
		    		}
		    		String target=PropertyOf.TARGET.toString()+targetCounter;
		    		subProblemList.add(target);
		    		oldLabelToNewLabelMap.put(target, objectToLabelMap.get(relNode));
		    		INode node=relNode.getNode();
		    		List<Object> nodeDetails = node.getPropertyOrRelationshipOrAnd();
		    		Iterator<Object> nodeDetailsIterator = nodeDetails.iterator();
		    		while(nodeDetailsIterator.hasNext()){
		    			Object o=nodeDetailsIterator.next();
		    			if(o instanceof JAXBElement){
		    				JAXBElement<?> element = (JAXBElement<Object>) o;
		    				if(element.getName().toString().contains("}and")||element.getName().toString().contains("}or")){
		    					allElements.put(relNode,PropertyOf.TARGET.toString()+targetCounter);
		    					keyElements.add(relNode);
		    					break;
		    				}
		    			}	
		    		}
		    		targetCounter+=1;
		    		lastTarget=objectToLabelMap.get(relNode);
		    	}
			}
		}
		nestedProblemMap.put(source, problemList);
		return targetCounter;
	}
	
	@Override
	public void queryToCypher(IQuery q)
	{
		throw new NotImplementedException("Not yet implemented");
	}
	
}
