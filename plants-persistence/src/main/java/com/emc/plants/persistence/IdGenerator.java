package com.emc.plants.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Bean implementation class for Enterprise Bean: IdGenerator
 */
@Entity(name="IdGenerator")
@Table(name="IDGENERATOR", schema="APP")
@NamedQueries({
	@NamedQuery(
		name="removeAllIdGenerator",
		query="delete from IdGenerator")
})
public class IdGenerator
{
	@Id
	private String idName;
	private int idValue;
	
	public IdGenerator() {
		idName="unknown";
		idValue = -1;
	}
	public IdGenerator(String idName)
	{
		setIdName(idName);
	}
	public IdGenerator(String idName, int idValue) 
	{
		setIdName(idName);
		setIdValue(idValue);
	}
	
	/**
	 * Get the next available id.
	 */
	public int nextId() 
	{
		int i = getIdValue();
		i++;
		setIdValue(i);    
		return i;
	}
	
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public int getIdValue() {
		return idValue;
	}
	public void setIdValue(int idValue) {
		this.idValue = idValue;
	}
}
