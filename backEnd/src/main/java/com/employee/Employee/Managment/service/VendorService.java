package com.employee.Employee.Managment.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.employee.Employee.Managment.entities.EmailData;
import com.employee.Employee.Managment.model.ShareEmail;
import com.employee.Employee.Managment.model.VendorDto;

public interface VendorService {

	String addVendor(VendorDto vendorDto, Long userId);

	String deleteVendor(Long vendorId);

	VendorDto getVendor(Long vendorId);

	String updateVendor(VendorDto vendorDto, Long userId, Long vendorId);

	void sharePost(ShareEmail shareEmail) throws UnsupportedEncodingException;

	List<EmailData> getAllEmail();

}
