package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Administratorオブジェクトを生成するROW_MAPPER
	 */
	private final static RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	/**
	 * @param administrator 引数で受け取ったAdministratorがIDを持っていなければstudentデータベースのadministratorテーブルに挿入する
	 *                      IDを持っていればadministratorテーブルのIDが一致するデータをアップデートする。
	 */
	public void insert(Administrator administrator) {
		System.out.println("AdministratorRepositoryのinsert()が呼び出されました。");
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		String insertSql = "INSERT INTO administrator(name,mail_address,password)VALUES(:name,:mailAddress,password)";
		template.update(insertSql, param);

	}

	/**
	 * @param mailAddress
	 * @param password
	 * @return Administratorオブジェクト
	 * administratorテーブルのデータで、mailAddressとpasswordが一致するものがあればそのデータをオブジェクトにして返す
	 * 一致するものがなければNULLを返す
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String findByMailAddressAndPasswordSql = "SELECT id,name,mail_address,password FROM administrator "
				+ "WHERE mail_address=:mailAddress AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",password);
		Administrator administrator = template.queryForObject(findByMailAddressAndPasswordSql, param, ADMINISTRATOR_ROW_MAPPER);
		if(administrator == null) {
			return null;
		}
		return administrator;
	}
}
