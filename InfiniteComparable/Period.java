import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Period {

	private final Integer from;
	private final Integer to;
	public Period(Integer from, Integer to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Integer getFrom() {
		return from;
	}
	public Integer getTo() {
		return to;
	}

	@Override
	public String toString() {
		return "Period [from=" + from + ", to=" + to + "]";
	}

	public boolean hasIntersection(Period that) {
		return Period.hasIntersection(Arrays.asList(this, that));
	}

	public static boolean hasIntersection(List<Period> periods) {
		if (periods.isEmpty()) {
			return false;
		} else {
			return periods.stream()
				.map(Period::getFrom)
				.map(InfiniteComparable::nullToMinusInfinite)
				.max(Comparator.naturalOrder())
				.get().compareTo(periods.stream()
					.map(Period::getTo)
					.map(InfiniteComparable::nullToPlusInfinite)
					.min(Comparator.naturalOrder())
					.get()) <= 0;
		}
	}
}
