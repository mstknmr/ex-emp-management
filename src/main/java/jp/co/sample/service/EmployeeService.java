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
	 * @return List<Employee> 従業員リスト
	 */
	public List<Employee> showList(){
		return employeeRepository.findAll();
	}
	
	/**
	 * IDが一致する従業員情報の検索.
	 * 
	 * @param id　従業員ID
	 * @return　　従業員情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}
	
	/**
	 * 従業員情報を更新する.
	 * 
	 * @param employee 従業員情報
	 */
	public void update (Employee employee) {
		employeeRepository.update(employee);
	}
}
