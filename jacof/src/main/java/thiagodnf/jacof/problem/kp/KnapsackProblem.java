package thiagodnf.jacof.problem.kp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.util.io.InstanceReader;

/**
 * Class to create an instance of the Knapsack Problem (01 Knapsack Problem)
 * 
 * @author Thiago N. Ferreira
 * @version 1.0.0
 * @see https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html
 */
public class KnapsackProblem extends Problem {

	protected int numberOfItems;
	
	protected double[] profits;
	
	protected double[] weights;
	
	protected double capacity;
	
	/** Sum of the profits */
	protected double maxPossibleProfit;
	
	/** Sum of the weights */
	protected double maxPossibleWeight;
	
	public KnapsackProblem(String filename) throws IOException{
		
		InstanceReader reader = new InstanceReader(new File(filename));
		
		this.numberOfItems = reader.readIntegerValue();
		this.weights = reader.readDoubleArray();
		this.profits = reader.readDoubleArray();
		this.capacity = reader.readDoubleValue();
		
		this.maxPossibleProfit = Arrays.stream(profits).reduce(Double::sum).getAsDouble();
		this.maxPossibleWeight = Arrays.stream(weights).reduce(Double::sum).getAsDouble();
	}

	@Override
	public double evaluate(int[] solution) {
		return Arrays.stream(solution).mapToDouble(i -> profits[i]).sum();
	}
	
	@Override
	public boolean better(double s1, double best) {
		return s1 > best;
	}

	@Override
	public int getNumberOfNodes() {
		return this.numberOfItems;
	}

	@Override
	public double getCnn() {
		return this.maxPossibleProfit;
	}

	// trail level indicating how proficient it has been in the past to make that particular move
	@Override
	public double getDeltaTau(double tourLength, int i, int j) {
		return tourLength / maxPossibleProfit;
	}

	// the attractiveness of the move,
	@Override
	public double getNij(int i, int j) {
		return (profits[j] / weights[j]);
	}

	@Override
	public List<Integer> initNodesToVisit(int startingNode) {
		var list = IntStream.range(0, getNumberOfNodes())
				.mapToObj(x -> Map.entry(x, weights[x]))
				.filter(x -> x.getValue() < capacity)
				.filter(x -> x.getKey() != startingNode)
				.map(x -> x.getKey())
				.toList();

		return new ArrayList<>(list);
	}
	
	@Override
	public List<Integer> updateNodesToVisit(List<Integer> tour, List<Integer> nodesToVisit) {
		var currentCost = tour.stream().mapToDouble(i -> weights[i]).sum();
		var list =  nodesToVisit.stream()
				.filter(i -> currentCost + weights[i] <= capacity)
				.toList();

		return new ArrayList<>(list);
	}

	@Override
	public String toString() {
		return KnapsackProblem.class.getSimpleName();
	}
}
