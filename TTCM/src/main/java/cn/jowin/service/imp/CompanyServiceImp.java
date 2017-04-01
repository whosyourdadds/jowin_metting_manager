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
			throw new ServiceException("��˾���Ʋ���Ϊ��");
		}
		if(companyAddress.trim().isEmpty()||companyAddress==null){
			throw new ServiceException("��˾��ַ����Ϊ��");
		}
		if(companyPhone.trim().isEmpty()||companyPhone==null){
			throw new ServiceException("��˾�绰����Ϊ��");
		}
		if(companyEmail.trim().isEmpty()||companyEmail==null){
			throw new ServiceException("��˾���䲻��Ϊ��");
		}
		if(companyFax.trim().isEmpty()||companyFax==null){
			throw new ServiceException("��˾���治��Ϊ��");
		}
		Company companyCheck =companyDao.findCompanyByName(companyName);
		if(companyCheck!=null){
			throw new ServiceException("��˾�Ѿ�����");
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
			throw new ServiceException("��˾ID����Ϊ��");
		}
		Company company= companyDao.findCompanyById(companyId);
		if(company == null){
			throw new ServiceException("��˾������");
		}
		companyDao.deleteCompanyRegistById(companyId);
		company= companyDao.findCompanyById(companyId);
		if(company != null){
			throw new ServiceException("ɾ����˾ʧ��");
		}
	}
}
