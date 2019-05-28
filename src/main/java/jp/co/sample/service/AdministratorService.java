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

}
