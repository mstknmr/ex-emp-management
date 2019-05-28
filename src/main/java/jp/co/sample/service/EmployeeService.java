package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * Employeeのサービスクラス.
 * 
 * @author knmrmst
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員全員のリスト取得.
	 * 
	 * <p>
	 * employeeRepositoryのfindAll()を実行してその戻り値を返す。
	 * </p>
	 * 
	 * @return List<Employee> 従業員リスト
	 */
	public List<Employee> showList(){
		return employeeRepository.findAll();
	}
}
