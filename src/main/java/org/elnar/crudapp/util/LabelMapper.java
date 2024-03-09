package org.elnar.crudapp.util;

import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.model.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabelMapper {
	public static Label mapResultSetToLabel(ResultSet resultSet) throws SQLException {
		return Label.builder()
				.id(resultSet.getLong("id"))
				.name(resultSet.getString("name"))
				.labelStatus(LabelStatus.valueOf(resultSet.getString("label_status")))
				.build();
	}
}
