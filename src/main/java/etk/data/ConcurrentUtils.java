package etk.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 *
 * @author Pablo JS dos Santos
 */
public class ConcurrentUtils {

    public static <T> CompletionStage<Void> join(List<CompletionStage<T>> multipleStages) {
        List<CompletableFuture> multipleFutures = multipleStages.stream()
            .map(CompletionStage::toCompletableFuture)
            .collect(Collectors.toList());

        CompletableFuture[] array = multipleFutures.toArray(new CompletableFuture[multipleFutures.size()]);

        return CompletableFuture.allOf(array);
    }
}
