package smart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class RandomPredicateGenerator {
	
	private int[] nExpressions;
	private int[] nVariables;
	private int nCases;
	private String uuid;
	private Scanner scanner;
	
	public RandomPredicateGenerator(int[] nExpressions, int[] nVariables, int nCases){
		this.uuid = UUID.randomUUID().toString();
		this.nExpressions = nExpressions;
		this.nVariables = nVariables;
		this.nCases = nCases;
		prepareCases();
		try {
			this.scanner = new Scanner(new File("randomPreds" + uuid + ".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public RandomPredicateGenerator(String uuid) throws FileNotFoundException{
		this.uuid = uuid;
		this.scanner = new Scanner(new File("randomPreds" + uuid + ".txt"));
	}
	
	public ThreeSATPredicate read(){
		return new ThreeSATPredicate(scanner.nextLine());
	}
	
	private void prepareCases() {
		File f = new File("randomPreds" + uuid+ ".txt");
		PrintStream ps;
		try {
			ps = new PrintStream(f);
		} catch (FileNotFoundException e) {
			System.err.println(e.getStackTrace());
			System.exit(1);
			return;
		}
		int i = 0;
		int total = nVariables.length * nExpressions.length * nCases;
		System.out.println(total);
		for(int v = 0; v < nVariables.length; v++){
			int variables = nVariables[v];
			for(int e = 0; e < nExpressions.length; e++){
				int expressions = nExpressions[e];
				for(int c = 0; c < nCases; c++){
					ps.println(generateString(variables, expressions));
					if (i++ % 100 == 0){
						System.out.println(i * 100 / total + "% Done Generating Cases");
					}
				}
			}
		}
		ps.close();
		System.out.println("UUID: " + uuid);
	}
	
	public static ThreeSATPredicate generateRandomThreeSat(int nExpressions, int nVariables){
		String result = generateString(nExpressions, nVariables);
		if (ThreeSATPredicate.PRINT) System.out.println("RANDOM PREDICATE: " + result);;
		return new ThreeSATPredicate(result);
	}
	
	private static String generateString(int nExpressions, int nVariables){
		String result = "";
		for(int expr = 0; expr < nExpressions; expr++){
			Set<Integer> vars = new HashSet<Integer>();
			while (vars.size() < 3){
				vars.add((int) (Math.random() * nVariables));
			}
			String subExpression = "(";
			for (int i : vars){
				if (Math.random() < .5){
					subExpression += "!";
				}
				subExpression += i + " v ";
			}
			subExpression = subExpression.substring(0, subExpression.length() - 2) + ")";
			result += subExpression + " ^ ";
		}
		result = result.substring(0, result.length() - 2).trim();
		return result;
	}
}
