package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.LabelStatus;
import org.elnar.crudapp.exception.JdbcRepositoryException;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Label;
import org.elnar.crudapp.repository.LabelRepository;
import org.elnar.crudapp.util.DBUtils;
import org.elnar.crudapp.util.LabelMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM labels WHERE id = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM labels";
	private static final String INSERT_SQL = "INSERT INTO labels (name, label_status) VALUES (?, ?)";
	private static final String UPDATE_SQL = "UPDATE labels SET name = ?, label_status = ?  WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "UPDATE labels SET label_status = ? WHERE id = ?";
	
	@Override
	public Label getById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_BY_ID_SQL)){
			 preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return LabelMapper.mapResultSetToLabel(resultSet);
			}else {
				throw new NotFoundException("Метка не найдена по идентификатору: " + id);
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
	
	@Override
	public List<Label> getAll() {
		List<Label> labels = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_ALL_SQL)){
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				labels.add(LabelMapper.mapResultSetToLabel(resultSet));
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return labels;
	}
	
	@Override
	public Label save(Label label) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(INSERT_SQL)){
			 preparedStatement.setString(1, label.getName());
			 preparedStatement.setString(2, label.getLabelStatus().name());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount == 0){
				throw new JdbcRepositoryException("Создание метки не удалось, ни одна запись не была изменена.");
			}
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()){
				label.setId(generatedKeys.getLong(1));
			}else {
				throw new JdbcRepositoryException("Создание метки не удалось, не удалось получить идентификатор.");
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return label;
	}
	
	@Override
	public Label update(Label updateLabel) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_SQL)){
			 preparedStatement.setString(1, updateLabel.getName());
			 preparedStatement.setString(2, updateLabel.getLabelStatus().name());
			 preparedStatement.setLong(3, updateLabel.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount == 0) {
				throw new JdbcRepositoryException("Обновление метки не удалось, ни одна запись не была изменена.");
			}
			
			updateLabel = getById(updateLabel.getId());
			
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return updateLabel;
	}
	
	@Override
	public void deleteById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_BY_ID_SQL)){
			 preparedStatement.setString(1, LabelStatus.DELETED.name());
			 preparedStatement.setLong(2, id);
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount == 0) {
				throw new NotFoundException("Метка с ID " + id + " не найден.");
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
}
