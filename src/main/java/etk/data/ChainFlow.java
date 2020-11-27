package etk.data;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Pablo JS dos Santos
 */
public class ChainFlow {
    private Function function;
    private Consumer consumer;
    private Supplier supplier;
    private Runnable runnable;
    private ChainFlow previous;
    private ChainFlow next;
    private Object value;
    private boolean async;

    public ChainFlow(Function function, Consumer consumer, Supplier supplier, Runnable runnable, ChainFlow previous, ChainFlow next, Object value, boolean async) {
        this.function = function;
        this.consumer = consumer;
        this.supplier = supplier;
        this.runnable = runnable;
        this.previous = previous;
        this.next = next;
        this.value = value;
        this.async = async;
    }

    public <T> T resolve() {
        if (this.previous != null) {
            return this.previous.resolve();
        }

        return (T) this.invoke(this.value);
    }

    private Object invoke(Object value) {
        if (value instanceof CompletionStage) {
            CompletionStage stage = (CompletionStage) value;
            return this.invokeAsync(stage);
        }

        Object newValue = this.transform(value);

        return Optional.ofNullable(this.next)
            .map(nextFlow -> nextFlow.invoke(newValue))
            .orElse(newValue);
    }

    private Object transform(Object value) {
        if (this.function != null) {
            return this.function.apply(value);
        }

        if (this.supplier != null) {
            return this.supplier.get();
        }

        if (this.consumer != null) {
            this.consumer.accept(value);
        } else if (this.runnable != null) {
            this.runnable.run();
        }

        return value;
    }

    private CompletionStage invokeAsync(CompletionStage stage) {
        CompletionStage newStage = this.transformAsync(stage);

        return Optional.ofNullable(this.next)
            .map(nextFlow -> (CompletionStage) nextFlow.invoke(newStage))
            .orElse(newStage);
    }

    private CompletionStage transformAsync(CompletionStage stage) {
        if (this.function != null) {
            return this.async
                ? stage.thenCompose(this.function)
                : stage.thenApply(this.function);
        }

        if (this.supplier != null) {
            return this.async
                ? stage.thenCompose(ignored -> this.supplier.get())
                : stage.thenApply(ignored -> this.supplier.get());
        }

        if (this.consumer != null) {
            return stage.thenApply(value -> {
                this.consumer.accept(value);
                return value;
            });
        }

        if (this.runnable != null) {
            return stage.thenApply(value -> {
                this.runnable.run();
                return value;
            });
        }

        return stage;
    }

    public <T, W> ChainFlow then(Function<T, W> function) {
        this.next = new ChainFlow(function, null, null, null, this, null, null, false);
        return this.next;
    }

    public <T, W> ChainFlow thenAsync(Function<T, W> function) {
        this.next = new ChainFlow(function, null, null, null, this, null, null, true);
        return this.next;
    }

    public <T> ChainFlow then(Consumer<T> consumer) {
        this.next = new ChainFlow(null, consumer, null, null, this, null, null, false);
        return this.next;
    }

    public <T> ChainFlow then(Supplier<T> supplier) {
        this.next = new ChainFlow(null, null, supplier, null, this, null, null, false);
        return this.next;
    }

    public <T> ChainFlow thenAsync(Supplier<T> supplier) {
        this.next = new ChainFlow(null, null, supplier, null, this, null, null, true);
        return this.next;
    }

    public ChainFlow then(Runnable runnable) {
        this.next = new ChainFlow(null, null, null, runnable, this, null, null, false);
        return this.next;
    }

    public static ChainFlow when(Object value) {
        return new ChainFlow(null, null, null, null, null, null, value, false);
    }

    public static ChainFlow when(CompletionStage stage) {
        return new ChainFlow(null, null, null, null, null, null, stage, true);
    }
}
