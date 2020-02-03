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
public class ExConnection {
    private DataSource dataSource;
    private Executor executor;

    public ExConnection(DataSource dataSource, Executor executorService) {
        this.dataSource = dataSource;
        this.executor = executorService;
    }

    public <T> CompletionStage<T> writeAsync(Function<Session, T> action) {
        return CompletableFuture.supplyAsync(() -> this.writeSession(action), this.executor);
    }

    public <T> T writeSession(Function<Session, T> action) {
        T value = null;

        try (Session session = new Session(this.dataSource.getConnection())) {
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

    public <T> CompletionStage<T> readAsync(Function<Session, T> action) {
        return CompletableFuture.supplyAsync(() -> this.readSession(action), this.executor);
    }

    public <T> T readSession(Function<Session, T> action) {
        try (Session session = new Session(this.dataSource.getConnection())) {
            return action.apply(session);
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("EJCRS60", e.getMessage(), e);
        }
    }
}
