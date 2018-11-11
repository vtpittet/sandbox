import java.util.function.*;

/**
 * 
 * <p>
 * Note on generic typing
 * <ul>
 * <li> Comparable is contravariant, which is used in Comparable type bound definition: {@code Comparable<? super T>}
 * <li> With type param T extending contravariant type {@code Comparable<? super T>}, {@code Comparable<Object>} is the subclass of any {@code Comparable<? super T>} type 
 * </ul>
 * @author valerian
 *
 * @param <T> Comparable type to enhance with InfiniteComparable
 */
public interface InfiniteComparable<T extends Comparable<? super T>> extends Comparable<InfiniteComparable<T>> {

	static <V extends Comparable<V>> InfiniteComparable<V> nullToPlusInfinite(V value) {
		return value == null ? PlusInfinite.get() : new Value<>(value);
	}

	static <V extends Comparable<V>> InfiniteComparable<V> nullToMinusInfinite(V value) {
		return value == null ? MinusInfinite.get() : new Value<>(value);
	}

	<R> R match(Supplier<R> onMinusInfinite, Supplier<R> onPlusInfinite, Function<? super T, R> onValue);
		
	final class PlusInfinite implements InfiniteComparable<Comparable<Object>> {

		private static final PlusInfinite INSTANCE = new PlusInfinite();

		@SuppressWarnings("unchecked")
		public static <T extends Comparable<? super T>> InfiniteComparable<T> get() {
			return (InfiniteComparable<T>) INSTANCE;
		}
		
		@Override
		public <R> R match(Supplier<R> onMinusInfinite, Supplier<R> onPlusInfinite, Function<? super Comparable<Object>, R> onValue) {
			return onPlusInfinite.get();
		}

		@Override
		public int compareTo(InfiniteComparable<Comparable<Object>> that) {
			return that.match(
				() -> 1,
				() -> 0,
				(value) -> 1
				);
		}

	}

	final class MinusInfinite implements InfiniteComparable<Comparable<Object>> {

		private static final MinusInfinite INSTANCE = new MinusInfinite();

		@SuppressWarnings("unchecked")
		public static <T extends Comparable<? super T>> InfiniteComparable<T> get() {
			return (InfiniteComparable<T>) INSTANCE;
		}

		@Override
		public <R> R match(Supplier<R> onMinusInfinite, Supplier<R> onPlusInfinite, Function<? super Comparable<Object>, R> onValue) {
			return onMinusInfinite.get();
		}

		@Override
		public int compareTo(InfiniteComparable<Comparable<Object>> that) {
			return that.match(
				() -> 0,
				() -> -1,
				(value) -> -1
				);
		}
	}

	final class Value<T extends Comparable<T>> implements InfiniteComparable<T> {

		private final T value;

		public Value(T value) {
			this.value = value;
		}

		@Override
		public <R> R match(Supplier<R> onMinusInfinite, Supplier<R> onPlusInfinite, Function<? super T, R> onValue) {
			return onValue.apply(value);
		}

		@Override
		public int compareTo(InfiniteComparable<T> that) {
			return that.match(
				() -> 1,
				() -> -1,
				(value) -> this.value.compareTo(value)
				);
		}
	}
}
