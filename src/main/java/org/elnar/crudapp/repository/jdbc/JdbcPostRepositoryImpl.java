package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.PostStatus;
import org.elnar.crudapp.exception.JdbcRepositoryException;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.model.Post;
import org.elnar.crudapp.repository.PostRepository;
import org.elnar.crudapp.util.DBUtils;
import org.elnar.crudapp.util.PostMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {
	
	private static final String SELECT_BY_ID_SQL = "SELECT p.id, p.content, p.created, p.updated, p.post_status, " +
			"w.id as writer_id, w.firstname, w.lastname, l.id as label_id, l.name " +
			"from posts p " +
			"left join post_label pl on p.id = pl.post_id " +
			"left join labels l on pl.label_id = l.id " +
			"left join writers w on w.id = p.writer_id " +
			"where p.id = ?";
	
	private static final String SELECT_ALL_SQL = "SELECT p.id, p.content, p.created, p.updated, p.post_status, " +
			"w.id as writer_id, w.firstname, w.lastname, l.id as label_id, l.name " +
			"from posts p " +
			"left join post_label pl on p.id = pl.post_id " +
			"left join labels l on pl.label_id = l.id " +
			"left join writers w on w.id = p.writer_id";
	
	private static final String INSERT_SQL = "INSERT INTO posts (content, created, updated, post_status, writer_id) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE posts SET content = ?, updated = ?, post_status = ? WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "UPDATE posts SET post_status = ? WHERE id = ?";
	
	private static final String INSERT_POST_LABEL_SQL = "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
	private static final String DELETE_POST_LABEL_SQL = "DELETE FROM post_label WHERE post_id = ?";
	
	@Override
	public Post getById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_BY_ID_SQL)){
			 preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return PostMapper.mapResultSetToPost(resultSet);
			}else {
				throw new NotFoundException("Пост не найден по идентификатору: " + id);
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
	
	@Override
	public List<Post> getAll() {
		List<Post> posts = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_ALL_SQL)){
			
		     ResultSet resultSet = preparedStatement.executeQuery();
		     while (resultSet.next()){
			    posts.add(PostMapper.mapResultSetToPost(resultSet));
			 }
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return posts;
	}
	
	@Override
	public Post save(Post post) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(INSERT_SQL)){
			preparedStatement.setString(1, post.getContent());
			preparedStatement.setTimestamp(2, new Timestamp(post.getCreated().getTime()));
			preparedStatement.setTimestamp(3, new Timestamp(post.getUpdated().getTime()));
			preparedStatement.setString(4, post.getPostStatus().name());
			preparedStatement.setLong(5, post.getWriter().getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount == 0){
				throw new JdbcRepositoryException("Создание поста не удалось, ни одна запись не была изменена.");
			}
			
			/*эта строка кода обновляет идентификатор объекта Post на значение,
			 сгенерированное базой данных при его вставке.*/
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()){
				post.setId(generatedKeys.getLong(1));
			}else {
				throw new JdbcRepositoryException("Создание поста не удалось, не удалось получить идентификатор.");
			}
			
			addLabelToPost(post.getId(), post.getLabels());
			
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return post;
	}
	
	@Override
	public Post update(Post updatePost) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_SQL)){
			preparedStatement.setString(1, updatePost.getContent());
			preparedStatement.setTimestamp(2, new Timestamp(updatePost.getUpdated().getTime()));
			preparedStatement.setString(3, updatePost.getPostStatus().name());
			preparedStatement.setLong(4, updatePost.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount == 0) {
				throw new JdbcRepositoryException("Обновление поста не удалось, ни одна запись не была изменена.");
			}
			
			deleteLabelsForPost(updatePost.getId());
			addLabelToPost(updatePost.getId(), updatePost.getLabels());
			
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return updatePost;
	}
	
	@Override
	public void deleteById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_BY_ID_SQL)){
			 preparedStatement.setString(1, PostStatus.DELETED.name());
			 preparedStatement.setLong(2, id);
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount == 0) {
				throw new NotFoundException("Пост с ID " + id + " не найден.");
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	
	private void addLabelToPost(Long postId, List<Label> labels) {
		if (labels == null) {
			return; // Ничего не делаем, если список меток пустой
		}
		
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(INSERT_POST_LABEL_SQL)) {
			for (Label label : labels) {
				if (label.getId() != null) {
					preparedStatement.setLong(1, postId);
					preparedStatement.setLong(2, label.getId());
					
					preparedStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
	
	private void deleteLabelsForPost(Long postId) {
		try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_POST_LABEL_SQL)) {
			preparedStatement.setLong(1, postId);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
}
	
	
	

