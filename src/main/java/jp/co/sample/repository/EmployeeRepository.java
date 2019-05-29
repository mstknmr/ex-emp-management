package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * Employeeのリポジトリ.
 *
 * @author knmrmst
 */
@Repository
public class EmployeeRepository {
	private final static RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
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
	 * employeesテーブルの要素を全件検索する機能.
	 * <p>
	 * employeesテーブルの要素を全件検索してEmployee型のリストに格納して返す。
	 * </p>
	 * 
	 * @return List<employee> employeeList 従業員のリスト
	 */
	public List<Employee> findAll() {
		System.out.println("EmployeeRepositoryのfindAll()が呼び出されました");
		String findAllSql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count \n"
				+ "FROM employees ORDER BY hire_date";
		List<Employee> employeeList = template.query(findAllSql, EMPLOYEE_ROW_MAPPER);

		return employeeList;
	}

	/**
	 * 従業員情報の主キー検索. 
	 * <p>
	 * 引数で受け取ったIDで主キー検索を行い、テーブルに一致する従業員情報があれば従業員情報を返す。
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	public Employee load(Integer id) {
		System.out.println("EmployeeRepositoryのload()が呼び出されました");
		String loadSql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
				+ "FROM employees WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee;
		try {
			employee = template.queryForObject(loadSql, param, EMPLOYEE_ROW_MAPPER);
			return employee;
		} catch (DataAccessException e) {
			System.out.println("IDの一致する検索結果は０件です");
			return null;
		}
	}

	/**
	 * employeesテーブルの更新機能.
	 * <p>
	 * employeesテーブルのデータで、引数で受け取ったemployeeオブジェクトのidが一致するデータの
	 * dependents_codeをemployeeオブジェクトの持っているdependentsCodeに変更する
	 * </p>
	 * 
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		System.out.println("EmployeeRepositoryのupdate()が呼び出されました");
		System.out.println(employee);
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
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
