package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentalBooksInfo;

@Configuration
public class RentalBooksRowMapper implements RowMapper<RentalBooksInfo>{
	
	@Override
	public RentalBooksInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
		RentalBooksInfo rentalBooksInfo = new RentalBooksInfo();

        rentalBooksInfo.setBookId(rs.getInt("rent_id"));
        rentalBooksInfo.setTitle(rs.getString("title"));
        rentalBooksInfo.setRentalDate(rs.getDate("rent_date"));
        rentalBooksInfo.setReturnDate(rs.getDate("return_date"));
        
        return rentalBooksInfo;
    }

}
