package com.employee.Employee.Managment.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.Managment.entities.EmailData;
import com.employee.Employee.Managment.model.ShareEmail;
import com.employee.Employee.Managment.model.VendorDto;
import com.employee.Employee.Managment.service.VendorService;

@RestController
@CrossOrigin
@RequestMapping("/vendor")
public class VendorController {
	@Autowired
	private VendorService vendorService;

	@PostMapping("add/{userId}")
	ResponseEntity<String> addVendor(@RequestBody VendorDto vendorDto, @PathVariable Long userId) {
		return ResponseEntity.ok(vendorService.addVendor(vendorDto, userId));
	}

	@PutMapping("/update")
	ResponseEntity<String> updateVendor(@RequestBody VendorDto vendorDto, @RequestParam Long userId,@RequestParam Long vendorId) {
		return ResponseEntity.ok(vendorService.updateVendor(vendorDto, userId,vendorId));
	}


	@GetMapping("/{vendorId}")
	ResponseEntity<VendorDto> getVendor(@PathVariable Long vendorId) {
		return ResponseEntity.ok(vendorService.getVendor(vendorId));
	}

	@DeleteMapping("/{vendorId}")
	ResponseEntity<String> deleteVendor(@PathVariable Long vendorId) {
		return ResponseEntity.ok(vendorService.deleteVendor(vendorId));
	}
	@CrossOrigin
	@PostMapping(value = "/share", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> sharePost(@RequestBody ShareEmail shareEmail) throws UnsupportedEncodingException {
		 this.vendorService.sharePost(shareEmail);
		return new ResponseEntity<>("Post Successfully Share with your Friends",HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/email")
	public ResponseEntity<List<EmailData>> getAllEmailData() throws UnsupportedEncodingException {
		 return ResponseEntity.ok(vendorService.getAllEmail());
	}
}