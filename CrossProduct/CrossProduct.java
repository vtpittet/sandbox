import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrossProduct {

	/**
	 * Computes cross products of all inner streams contained in outer stream
	 * <p>
	 * For example self cross of ((a, aa, aaa), (b, bb), (c)) returns ((a, b, c), (a, bb, c), (aa, b, c), (aa, bb, c), (aaa, b, c), (aaa, bb, c)) 
	 * 
	 * @param input stream of streams used to compute the cross products 
	 * @return resulting cross product, Optional.empty if input is one empty stream.
	 */
	public static <T> Optional<Stream<List<T>>> selfCross(Stream<Stream<T>> input) {
		return input.map(inner -> inner.map(value -> Collections.singletonList(value)))
				.reduce((innerLeft, innerRight) -> {
					List<List<T>> collectedRight = innerRight.collect(Collectors.toList());
					return innerLeft.flatMap(accLeft -> collectedRight.stream().map(accRight -> Stream
							.concat(accLeft.stream(), accRight.stream()).collect(Collectors.toList())));
				});
	}

	public static void main(String[] args) {
		final Stream<Stream<String>> testInput = Stream.of(
				Stream.of("a1", "a2", "a3"),
				Stream.of("b1", "b2", "b3"),
				Stream.of("c1", "c2", "c3"));
		System.out.println(CrossProduct.selfCross(testInput).get().collect(Collectors.toList()));
		final Stream<Stream<String>> testOneEmpty = Stream.of(
				Stream.of("a1", "a2", "a3"),
				Stream.of(),
				Stream.of("c1", "c2", "c3"));
		System.out.println(CrossProduct.selfCross(testOneEmpty).get().collect(Collectors.toList()));
		final Stream<Stream<String>> testNoInput = Stream.of();
		System.out.println(CrossProduct.selfCross(testNoInput));
	}

}
