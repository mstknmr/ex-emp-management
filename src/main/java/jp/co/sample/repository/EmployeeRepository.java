package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * @author knmrmst
 *Employeeのリポジトリ
 */
@Repository
public class EmployeeRepository {
	private final static RowMapper<Employee> EMPLOYEE_ROW_MAPPER=(rs,i)->{
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * @return List<employee> employeeList
	 * employeesテーブルの要素を全件検索する機能
	 */
	public List<Employee> findAll(){
		System.out.println("EmployeeRepositoryのfindAll()が呼び出されました");
		String findAllSql="SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
				+ "FROM employees　ORDER BY hire_date DESC";
		List<Employee> employeeList = template.query(findAllSql,EMPLOYEE_ROW_MAPPER);
		
		return employeeList;
	}
	
	public Employee load(Integer id) {
		System.out.println("EmployeeRepositoryのload()が呼び出されました");
		String loadSql="ELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
				+ "FROM employees WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(loadSql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/**
	 * @param employee
	 * employeesテーブルのデータで、引数で受け取ったemployeeオブジェクトのidが一致するデータの
	 * dependents_codeをemployeeオブジェクトの持っているdependentsCodeに変更する
	 * 
	 */
	public void update(Employee employee) {
		System.out.println("EmployeeRepositoryのupdate()が呼び出されました");
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount FROM employees WHERE id=:id" ;
//		String updateSql= "UPDATE employees SET "
//				+ "name=:name,"
//				+ "image=:image,"
//				+ "gender=:gender,"
//				+ "hire_date=:hireDate,"
//				+ "mail_address=:mailAddress,"
//				+ "zip_code=:zipCode,"
//				+ "address=:address,"
//				+ "telephone=:telephone,"
//				+ "salary=:salary,"
//				+ "characteristics=:characteristics,"
//				+ "dependents_count=:dependentsCount "
//				+ "FROM employees WhERE id=:id";
		template.update(updateSql, param);
	}
}
