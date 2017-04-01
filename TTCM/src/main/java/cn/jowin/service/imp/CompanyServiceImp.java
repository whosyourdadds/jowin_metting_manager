package cn.jowin.service.imp;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jowin.dao.CompanyDao;
import cn.jowin.entity.Company;
import cn.jowin.service.CompanyService;
import cn.jowin.service.exception.ServiceException;

@Service("companyService")
public class CompanyServiceImp implements CompanyService{
	
	@Resource
	private CompanyDao companyDao;
	
	@Transactional
	public Company registCompany(
			String companyName,
			String companyAddress, 
			String companyPhone,
			String companyEmail,
			String companyFax,
			String companyWebsite){
		if(companyName.trim().isEmpty()||companyName==null){
			throw new ServiceException("公司名称不能为空");
		}
		if(companyAddress.trim().isEmpty()||companyAddress==null){
			throw new ServiceException("公司地址不能为空");
		}
		if(companyPhone.trim().isEmpty()||companyPhone==null){
			throw new ServiceException("公司电话不能为空");
		}
		if(companyEmail.trim().isEmpty()||companyEmail==null){
			throw new ServiceException("公司邮箱不能为空");
		}
		if(companyFax.trim().isEmpty()||companyFax==null){
			throw new ServiceException("公司传真不能为空");
		}
		Company companyCheck =companyDao.findCompanyByName(companyName);
		if(companyCheck!=null){
			throw new ServiceException("公司已经存在");
		}
		String uuid = UUID.randomUUID().toString();
		Company company= new Company(
				uuid, null, null, 
				companyName, companyAddress, 
				companyPhone, companyFax, 
				companyEmail, companyWebsite, null,null,null,null);
		companyDao.saveCompany(company);
		return company;
	}
	
	
	@Transactional
	public void deleteCompanyRegistById(String companyId){
		if(companyId.trim().isEmpty()||companyId==null){
			throw new ServiceException("公司ID不能为空");
		}
		Company company= companyDao.findCompanyById(companyId);
		if(company == null){
			throw new ServiceException("公司不存在");
		}
		companyDao.deleteCompanyRegistById(companyId);
		company= companyDao.findCompanyById(companyId);
		if(company != null){
			throw new ServiceException("删除公司失败");
		}
	}
}
