package edu.asu.lerna.iolaus.web.usermanagement.backing;

import java.util.List;

import edu.asu.lerna.iolaus.annotation.NotEmpty;
import edu.asu.lerna.iolaus.annotation.NotEmptyList;
import edu.asu.lerna.iolaus.domain.implementation.Role;
/**
 * Modify user backing bean used for validating data based on user input in the form for modify User 
 * @author : Lohith Dwaraka 
 *
 */
public class ModifyUserBackingBean {

	@NotEmpty(message = "Please provide a username.")
	private String username;
	
	@NotEmpty(message = "Please provide name of user.")
	private String name;
	
	@NotEmpty(message = "Please provide an email address.")
	private String email;
	
	@NotEmptyList(message = "At least one role needs to be selected.")
	private List<Role> roles;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
