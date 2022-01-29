package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.City;


public interface ICityTransaction {
		
	public boolean addCity(City city);
	public List<City> getCities();
	public boolean updateCity(City city);
	public boolean deleteCity(City city); 
	public City getCityById(int id);
}
