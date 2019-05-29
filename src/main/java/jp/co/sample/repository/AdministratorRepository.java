package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 *Administratorのリポジトリ.
 *
 * @author knmrmst
 */
@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Administratorオブジェクトを生成するROW_MAPPER.
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
	 * 管理者情報を登録する.
	 *  <p>
	 * 	引数で受け取ったAdministratorがIDを持っていなければstudentデータベースのadministratorテーブルに挿入する
	 *  IDを持っていればadministratorテーブルのIDが一致するデータをアップデートする。
	 *  </p>
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		System.out.println("AdministratorRepositoryのinsert()が呼び出されました。");
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		if(administrator.getId()==null) {
			String insertSql = "INSERT INTO administrators(name,mail_address,password)VALUES(:name,:mailAddress,:password)";
			template.update(insertSql, param);
		}else {
			String updateSql="UPDATE administrators SET name=:name,mail_address=:mailAddress,password=:password WHERE id=:id";
			template.update(updateSql, param);
		}
		
	}

	/**
	 *　メールアドレスとパスワードで管理者情報を探す機能.
	 * 
	 * <p>administratorテーブルのデータで、mailAddressとpasswordが一致するものがあればそのデータをオブジェクトにして返す。
	 * 一致するものがなければNULLを返す。
	 * </p>
	 * 
	 * @param mailAddress　メールアドレス
	 * @param password　パスワード
	 * @return administrator 管理者情報
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		System.out.println("AdministratorRepositoryのfindByMailAddressAndPassword()が呼び出されました");
		String findByMailAddressAndPasswordSql = "SELECT id,name,mail_address,password FROM administrators "
				+ "WHERE mail_address=:mailAddress AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",password);
		Administrator administrator;
		try {
			administrator = template.queryForObject(findByMailAddressAndPasswordSql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		} catch (DataAccessException e) {
			System.out.println("パスワードとメールアドレスに不正があります");
			return null;
		}
	}
	
	public Administrator load(Integer id) {
		String loadSql="SELECT id,name,mail_address,password FROM administrators WHERE id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		try {
			Administrator administrator  = template.queryForObject(loadSql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		} catch (DataAccessException e) {
			System.out.println("入力されたIDを持つデータはありません");
			return null;
		}
		
	}


}
