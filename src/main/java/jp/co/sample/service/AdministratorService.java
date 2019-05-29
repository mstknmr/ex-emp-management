package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * administratorのserviceクラス.
 * 
 * @author knmrmst
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;
	
	/**
	 * 管理者情報を挿入する.
	 * 
	 * <p>
	 * administratorRepositoryのinsert()を呼ぶ
	 * </p>
	 * @param administrator　管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}
	
	/**
	 * ログイン処理をする
	 * <p>
	 * administratorRepositoryのfindByMailAddressAndPassword()を呼ぶ
	 * </p>
	 * 
	 * @param mailAddress
	 * @param password
	 * @return
	 */
	
	public Administrator login(String mailAddress,String password) {
		return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
	}
	
	/**
	 * 従業員情報の検索.
	 * 
	 * @param id 従業員ID
	 * @return　従業員情報
	 */
	public Administrator load(Integer id) {
		return administratorRepository.load(id);
	}

}
