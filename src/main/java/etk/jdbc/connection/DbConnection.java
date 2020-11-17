package etk.jdbc.connection;

import etk.jdbc.exceptions.PersistenceException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.sql.DataSource;

/**
 *
 * @author Pablo JS dos Santos
 */
public class DbConnection {
    private DataSource dataSource;
    private Executor executor;

    public DbConnection(DataSource dataSource, Executor executorService) {
        this.dataSource = dataSource;
        this.executor = executorService;
    }

    public <T> CompletionStage<T> writeAsync(Function<DbSession, T> action) {
        return CompletableFuture.supplyAsync(() -> this.write(action), this.executor);
    }

    public <T> T write(Function<DbSession, T> action) {
        T value = null;

        try (DbSession session = new DbSession(this.dataSource.getConnection())) {
            session.execute("BEGIN");

            try {
                value = action.apply(session);
            } catch (Exception e) {
                session.execute("ROLLBACK");
                throw new PersistenceException("EJCW37", e.getMessage(), e);
            }

            session.execute("COMMIT");
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("EJCWS44", e.getMessage(), e);
        }

        return value;
    }

    public <T> CompletionStage<T> readAsync(Function<DbSession, T> action) {
        return CompletableFuture.supplyAsync(() -> this.read(action), this.executor);
    }

    public <T> T read(Function<DbSession, T> action) {
        try (DbSession session = new DbSession(this.dataSource.getConnection())) {
            return action.apply(session);
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("EJCRS60", e.getMessage(), e);
        }
    }
}
