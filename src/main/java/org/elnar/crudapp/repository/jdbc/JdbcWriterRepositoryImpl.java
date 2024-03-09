package org.elnar.crudapp.repository.jdbc;

import org.elnar.crudapp.enums.WriterStatus;
import org.elnar.crudapp.exception.JdbcRepositoryException;
import org.elnar.crudapp.exception.NotFoundException;
import org.elnar.crudapp.model.Writer;
import org.elnar.crudapp.repository.WriterRepository;
import org.elnar.crudapp.util.DBUtils;
import org.elnar.crudapp.util.WriterMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcWriterRepositoryImpl implements WriterRepository {
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM writers WHERE id = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM writers";
	private static final String INSERT_SQL = "INSERT INTO writers (firstname, lastname, writer_status) VALUES (?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE writers SET firstname = ?, lastname = ?, writer_status = ? WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "UPDATE writers SET writer_status = ? WHERE id = ?";
	
	@Override
	public Writer getById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_BY_ID_SQL)){
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return WriterMapper.mapResultSetToWriter(resultSet);
			}else {
				throw  new NotFoundException("Писатель не найден по идентификатору: " + id);
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
	
	@Override
	public List<Writer> getAll() {
		List<Writer> writers = new ArrayList<>();
		
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(SELECT_ALL_SQL)){
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				writers.add(WriterMapper.mapResultSetToWriter(resultSet));
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return writers;
	}
	
	@Override
	public Writer save(Writer writer) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(INSERT_SQL)){
			preparedStatement.setString(1, writer.getFirstname());
			preparedStatement.setString(2, writer.getLastname());
			preparedStatement.setString(3, writer.getWriterStatus().name());
			
			int rowCount = preparedStatement.executeUpdate();
			
			if(rowCount == 0){
				throw new JdbcRepositoryException("Создание писателя не удалось, ни одна запись не была изменена.");
			}
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if(generatedKeys.next()){
				writer.setId(generatedKeys.getLong(1));
			}else {
				throw new JdbcRepositoryException("Создание писателя не удалось, не удалось получить идентификатор.");
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return writer;
	}
	
	@Override
	public Writer update(Writer updateWriter) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(UPDATE_SQL)){
			preparedStatement.setString(1, updateWriter.getFirstname());
			preparedStatement.setString(2, updateWriter.getLastname());
			preparedStatement.setString(3, updateWriter.getWriterStatus().name());
			preparedStatement.setLong(4, updateWriter.getId());
			
			int rowCount = preparedStatement.executeUpdate();
			if (rowCount == 0) {
				throw new JdbcRepositoryException("Обновление писателя не удалось, ни одна запись не была изменена.");
			}
			
			updateWriter = getById(updateWriter.getId());
			
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
		return updateWriter;
	}
	
	@Override
	public void deleteById(Long id) {
		try(PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_BY_ID_SQL)){
			preparedStatement.setString(1, WriterStatus.DELETED.name());
			preparedStatement.setLong(2, id);
			
			int rowCount = preparedStatement.executeUpdate();
			
			if (rowCount == 0) {
				throw new NotFoundException("Писатель с ID " + id + " не найден.");
			}
		} catch (SQLException e) {
			throw new JdbcRepositoryException("Ошибка выполнения SQL-запроса", e);
		}
	}
}
