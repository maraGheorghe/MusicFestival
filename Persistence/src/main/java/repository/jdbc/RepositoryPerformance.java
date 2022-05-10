package repository.jdbc;

import model.Performance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.interfaces.RepositoryInterfacePerformance;
import repository.jdbc.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
public class RepositoryPerformance implements RepositoryInterfacePerformance {

    private JDBCUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public RepositoryPerformance(Properties properties) {
        logger.info("Initializing RepositoryPerformance with properties: {} ", properties);
        jdbcUtils = new JDBCUtils(properties);
    }

    @Override
    public Optional<Performance> save(Performance performance) {
        logger.traceEntry("Saving performance: {} ", performance);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into performances (performance_date, place, no_of_seats, artist) values (?, ?, ?, ?)")) {
            preparedStatement.setString(1, performance.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            preparedStatement.setString(2, performance.getPlace());
            preparedStatement.setInt(3, performance.getNoOfAvailableSeats() + performance.getNoOfSoldSeats());
            preparedStatement.setString(4, performance.getArtist());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement("select performance_id from performances order by performance_id desc limit 1")) {
            var result = preparedStatement.executeQuery();
            if(result.next())
                performance.setID(result.getLong("performance_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.traceExit();
        return Optional.of(performance);
    }

    private int getNumberOfSoldTickets(Long performanceID) {
        logger.traceEntry("Counting the number of sold tickets for the performance witd ID: {}.", performanceID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select sum(seats) as count from tickets where performance_id = ?")) {
            preparedStatement.setLong(1, performanceID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next())
                    return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        return 0;
    }

    @Override
    public Optional<Performance> find(Long ID) {
        logger.traceEntry("Finding performance with ID: {}.", ID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from performances where performance_id = ?")) {
            preparedStatement.setLong(1, ID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Performance performance = extractPerformance(resultSet, ID);
                    logger.traceExit(performance);
                    return Optional.of(performance);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<Performance> delete(Performance performance) {
        logger.traceEntry("Deleting performance: {} ", performance);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from performances where performance_id =  ?")) {
            preparedStatement.setLong(1, performance.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Deleted {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(performance);
    }

    @Override
    public Optional<Performance> update(Performance performance) {
        logger.traceEntry("Updating performance: {} ", performance);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update performances set performance_date = ?, place = ?, no_of_seats = ?, artist = ? where performance_id = ?")) {
            preparedStatement.setString(1, performance.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            preparedStatement.setString(2, performance.getPlace());
            preparedStatement.setInt(3, performance.getNoOfAvailableSeats() + performance.getNoOfSoldSeats());
            preparedStatement.setString(4, performance.getArtist());
            preparedStatement.setLong(5, performance.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Updated {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(performance);
    }

    @Override
    public List<Performance> findAll() {
        logger.traceEntry("Finding all performances.");
        Connection connection = jdbcUtils.getConnection();
        List<Performance> performances = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from performances")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long performance_id = resultSet.getLong("performance_id");
                    Performance performance = extractPerformance(resultSet, performance_id);
                    performances.add(performance);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return performances;
    }

    private Performance extractPerformance(ResultSet resultSet, Long performance_id) throws SQLException {
        LocalDateTime date = LocalDateTime.parse(resultSet.getString("performance_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String place = resultSet.getString("place");
        int sold = getNumberOfSoldTickets(performance_id);
        int available = resultSet.getInt("no_of_seats") - sold;
        String artist = resultSet.getString("artist");
        return new Performance(performance_id, date, place, available, sold, artist);
    }

    @Override
    public List<Performance> findAllPerformancesForADay(LocalDate date) {
        logger.traceEntry("Finding all performances for the day: {}.", date);
        Connection connection = jdbcUtils.getConnection();
        List<Performance> performances = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from performances where performance_date LIKE ?")) {
            preparedStatement.setString(1, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + '%');
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long performance_id = resultSet.getLong("performance_id");
                    Performance performance = extractPerformance(resultSet, performance_id);
                    performances.add(performance);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return performances;
    }
}