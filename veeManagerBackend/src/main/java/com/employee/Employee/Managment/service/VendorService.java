package com.employee.Employee.Managment.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.employee.Employee.Managment.entities.EmailData;
import com.employee.Employee.Managment.model.ShareEmail;
import com.employee.Employee.Managment.model.VendorDto;

public interface VendorService {

	void addVendor(VendorDto vendorDto, String userId);

	String deleteVendor(String vendorId);

	VendorDto getVendor(String vendorId);

	void updateVendor(VendorDto vendorDto, String userId, String vendorId);

	void sharePost(ShareEmail shareEmail) throws UnsupportedEncodingException;

	List<EmailData> getAllEmail(String userId);

}
