import java.util.HashMap;
import java.util.Map;

/**
 * http://www.datagenetics.com/blog/july22012/index.html
 * Gives the best explanation I've seen by far of this problem
 * 
 * @author porkchop
 *
 */
public class EggDrop {
	private final static Map<String, Integer> memo = new HashMap<String, Integer>();
	
	private static String createKey(int numEggs, int numFloors) {
		return numEggs + "-" + numFloors;
	}
	
	private static Integer getMemoizedSolution(int numEggs, int numFloors) {
		String key = createKey(numEggs, numFloors);
		return memo.get(key);
	}
	
	private static void memoizeSolution(int numEggs, int numFloors, Integer maxDrops) {
		String key = createKey(numEggs, numFloors);
		memo.put(key, maxDrops);
	}
	
	private final int numEggs;
	private final int numFloors;
	
	private Integer worstCaseDrops;

	public EggDrop(int numEggs, int numFloors) {
		this.numEggs = numEggs;
		this.numFloors = numFloors;
	}
	
	public Integer getWorstCaseDrops() {
		if(worstCaseDrops == null) solve();
		return worstCaseDrops;
	}
	
	public void solve() {
		worstCaseDrops = getMemoizedSolution(numEggs, numFloors);
		if(worstCaseDrops != null) return;	 // already memoized a solution for this pair
		
		if(numEggs == 1 || numFloors == 0) worstCaseDrops = numFloors;
		else {
			for(int floor = 1; floor <= numFloors; floor++) {
				EggDrop eggBroke = new EggDrop(numEggs - 1, floor - 1);
				int eggBrokeDrops = 1 + eggBroke.getWorstCaseDrops();
				EggDrop eggIntact = new EggDrop(numEggs, numFloors - floor);
				int eggIntactDrops = 1 + eggIntact.getWorstCaseDrops();
				int maxDrops = Math.max(eggBrokeDrops, eggIntactDrops);
				worstCaseDrops = worstCaseDrops == null ? maxDrops : Math.min(maxDrops, worstCaseDrops);
			}
		}
		
		memoizeSolution(numEggs, numFloors, worstCaseDrops);
	}
	
	public void displayResults() {
		System.out.println("Egg drop\n\tNum eggs: " + numEggs + "\n\tNum floors: " + numFloors + "\n\tWorst case drops: " + getWorstCaseDrops() + "\n");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numFloors = 100;
		int maxEggs = 10;
		
		for(int numEggs = 1; numEggs <= maxEggs; numEggs++) {
			EggDrop eggDrop = new EggDrop(numEggs, numFloors);
			eggDrop.displayResults();
		}
	}

}
