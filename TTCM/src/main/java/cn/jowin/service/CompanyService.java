package cn.jowin.service;

import cn.jowin.entity.Company;

public interface CompanyService {
	Company registCompany(
			String companyName,
			String companyAddress, 
			String companyPhone,
			String companyEmail,
			String companyFax,
			String companyWebsite);
	
	void deleteCompanyRegistById(String companyId);
}
