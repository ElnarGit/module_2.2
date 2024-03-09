package org.elnar.crudapp.util;

import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.exception.JdbcRepositoryException;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.model.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterMapper {
	public static Writer mapResultSetToWriter(ResultSet resultSet) throws SQLException {
		Long writerId = resultSet.getLong("id");
		List<Post> posts = getPostsForWriter(writerId);
		
		return Writer.builder()
				.id(resultSet.getLong("id"))
				.firstname(resultSet.getString("firstname"))
				.lastname(resultSet.getString("lastname"))
				.writerStatus(WriterStatus.valueOf(resultSet.getString("writer_status")))
				.posts(posts)
				.build();
	}
	
	private static List<Post> getPostsForWriter(Long writerId) {
		String sql = "SELECT * FROM posts WHERE writer_id = ?";
		
		List<Post> posts = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(sql)){
			preparedStatement.setLong(1, writerId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Post post = PostMapper.mapResultSetToPost(resultSet);
				posts.add(post);
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return posts;
	}
}
