/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.schillerschule.kuwasys20.Controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Bean für die gesamte Navigationsstruktur des Systems
 */
@ManagedBean(name = "kuwasys")
@RequestScoped
public class kuwasysControllerBean {

	// properties
	private String name;
	private String lastname;
	private String temp;

	/**
	 * default empty constructor
	 */
	public kuwasysControllerBean() {
	}

	/**
	 * Method that is backed to a submit button of a form.
	 */
	public String send() {
		// do real logic, return a string which will be used for the navigation
		// system of JSF
		temp = name;
		name = lastname;
		lastname = temp;
		return "page2.jsf";
	}

	public String userRole() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getExternalContext().isUserInRole("admin"))
			return "Admin";
		if (context.getExternalContext().isUserInRole("lehrer"))
			return "Lehrer";
		if (context.getExternalContext().isUserInRole("schueler"))
			return "Schüler";
		else
			return null;
	}

	public String logout() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.invalidateSession();
		ec.redirect("kuwasys.jsf"); // Or whatever servlet mapping you use
		// redirect() invokes FacesContext.responseComplete() for you

		return null;
	}

	public String home() {
		return "kuwasys";
	}

	// -------------------getter & setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
