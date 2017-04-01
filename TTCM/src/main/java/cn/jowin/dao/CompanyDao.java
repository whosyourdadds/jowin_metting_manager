package cn.jowin.dao;

import cn.jowin.entity.Company;

public interface CompanyDao {
	void saveCompany(Company company);
	Company findCompanyById(String companyId);
	Company findCompanyByName(String companyName);
	void deleteCompanyRegistById(String companyId);
}
