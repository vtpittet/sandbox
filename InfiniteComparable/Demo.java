import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

	public static void main(String[] args) {
		List<Integer> times = Arrays.asList(null, 0, 1, 2, 3);
		
		List<Period> periods = new ArrayList<>();
		for (Integer t1 : times) {
			for (Integer t2 : times) {
				periods.add(new Period(t1, t2));
			}
		}
		for (int i = 0; i < periods.size(); i++) {
			for (int j = i; j < periods.size(); j++) {
				Period a = periods.get(i);
				Period b = periods.get(j);
				System.out.println(a + " n " + b + ": " + a.hasIntersection(b));
			}
		}
	}
}
