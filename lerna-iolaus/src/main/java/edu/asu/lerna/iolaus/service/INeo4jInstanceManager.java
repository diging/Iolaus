package edu.asu.lerna.iolaus.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import edu.asu.lerna.iolaus.domain.INeo4jInstance;


public interface INeo4jInstanceManager {
	
	abstract String addNeo4jInstance(INeo4jInstance instance);
	abstract void deleteNeo4jInstance(String instanceId) throws UnsupportedEncodingException;
	abstract List<INeo4jInstance> getAllInstances();
	abstract INeo4jInstance getInstance(String id);
	abstract boolean updateNeo4jInstance(INeo4jInstance instance);
	abstract String getInstanceId(String port, String host);
}
