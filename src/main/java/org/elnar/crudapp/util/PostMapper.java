package org.elnar.crudapp.util;

import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.exception.JdbcRepositoryException;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PostMapper {
	public static Post mapResultSetToPost(ResultSet resultSet) throws SQLException {
		Long postId = resultSet.getLong("id");
		List<Label> labels = getLabelsForPost(postId);
		
		return Post.builder()
				.id(postId)
				.content(resultSet.getString("content"))
				.created(resultSet.getTimestamp("created"))
				.updated(resultSet.getTimestamp("updated"))
				.postStatus(PostStatus.valueOf(resultSet.getString("post_status")))
				.writerId(Writer.builder().id(resultSet.getLong("writer_id")).build())
				.labels(labels)
				.build();
	}
	
	
	private static List<Label> getLabelsForPost(Long postId) {
		String sql = "SELECT l.* FROM labels l " +
				"INNER JOIN post_label pl ON l.id = pl.label_id " +
				"WHERE pl.post_id = ?";
		
		List<Label> labels = new ArrayList<>();

		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(sql)){
			preparedStatement.setLong(1, postId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Label label = LabelMapper.mapResultSetToLabel(resultSet);
				labels.add(label);
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return labels;
	}
}
