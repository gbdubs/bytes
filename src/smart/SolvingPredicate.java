package smart;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



public class SolvingPredicate {

	private static int ID = 1;
	
	private BigInteger state;
	private int[] alphabet;
	private Set<Integer> variableSet;
	private int id;
	
	public static void main(String[] args){
		
	}
	
	public SolvingPredicate(int[] alpha, boolean[] state){
		this.state = calculateState(state);
		this.alphabet = alpha;
		this.id = ID++;
	}
	
	public SolvingPredicate(int[] alpha, BigInteger state){
		this.state = state;
		this.alphabet = alpha;
		this.id = ID++;
	}
	
	public static SolvingPredicate conjoin(SolvingPredicate sp1, SolvingPredicate sp2){
		if (ThreeSATPredicate.PRINT) System.out.println("\n=== COMBINING ========== "+sp1.id+" AND "+sp2.id+" ========");
		int[] alpha1 = sp1.alphabet;
		int[] alpha2 = sp2.alphabet;
		int[] targetAlphabet = getTargetAlphabet(alpha1, alpha2);
		if (ThreeSATPredicate.PRINT) System.out.println("=== TARGET ALPHABET ========== " + Arrays.toString(targetAlphabet));
		BigInteger value1 = transposeAlphabet(alpha1, sp1.state, targetAlphabet);
		BigInteger value2 = transposeAlphabet(alpha2, sp2.state, targetAlphabet);
		SolvingPredicate result = new SolvingPredicate(targetAlphabet, value1.and(value2));
		if (ThreeSATPredicate.PRINT) System.out.println("=== RESULTS ======================= " + result.id + " ===");
		if (ThreeSATPredicate.PRINT) System.out.println(result.toString());
		if (ThreeSATPredicate.PRINT) System.out.println("=== FINISHED COMBINING "+sp1.id+" AND "+sp2.id+" INTO "+ result.id +" ===\n");
		return result;
	}
	
	private static BigInteger transposeAlphabet(int[] originalAlphabet, BigInteger originalState, int[] targetAlphabet){
		if (originalAlphabet.length == targetAlphabet.length){
			return originalState;
		}
		int[] subTargetAlphabet = new int[originalAlphabet.length + 1];
		boolean changed = false;
		for(int i = 0; i <= originalAlphabet.length; i++){
			if (changed){
				subTargetAlphabet[i] = originalAlphabet[i - 1];
			} else {
				if (i == originalAlphabet.length){
					subTargetAlphabet[i] = targetAlphabet[i];
				} else if (originalAlphabet[i] != targetAlphabet[i]){
					changed = true;
					subTargetAlphabet[i] = targetAlphabet[i];
				} else {
					subTargetAlphabet[i] = originalAlphabet[i];
				}
			}
		}
		BigInteger transposedOnce = transposeAlphabetOneVariable(originalAlphabet, originalState, subTargetAlphabet);
		return transposeAlphabet(subTargetAlphabet, transposedOnce, targetAlphabet);
	}
	
	public static BigInteger transposeAlphabetOneVariable(int[] originalAlphabet, BigInteger originalState, int[] newAlphabet){
		int i = 0; 
		while (i < originalAlphabet.length && originalAlphabet[i] == newAlphabet[i]){
			i++;
		}
		
		int bitStringLength = twoPow(originalAlphabet.length);
		int repetitionLength = twoPow(i);
		
		byte[] origin = makeNBytesLong(originalState.toByteArray(), bitStringLength / 8);
		byte[] result = new byte[origin.length * 2];
		
		if (originalAlphabet.length < 3){
			result = new byte[1];
			result[0] = transposeAlphabetOneVariablePartialState(i, originalState.byteValue());
		}
		
		if (i == originalAlphabet.length){
			if (ThreeSATPredicate.PRINT) System.out.println(" = DUPLICATE CASE");
			result = duplicateCase(origin);
		} else if (repetitionLength % 8 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = EIGHT CASE");
			result = repeatEightCase(origin, repetitionLength, bitStringLength);
		} else if (repetitionLength % 4 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = FOUR CASE");
			result = repeatFourCase(origin);
		} else if (repetitionLength % 2 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = TWO CASE");
			result = repeatTwoCase(origin);
		} else if (repetitionLength % 1 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = ONE CASE");
			result = repeatOneCase(origin);
		} else {
			System.err.println("SHOULD HAVE HAD A VALID REPETITION LENGTH");
		}
		
		if (ThreeSATPredicate.PRINT) System.out.println("   - ORIGIN: " + printByteArray(origin, false) + " " + Arrays.toString(originalAlphabet));
		if (ThreeSATPredicate.PRINT) System.out.println("   - RESULT: " + printByteArray(result, false) + " " + Arrays.toString(newAlphabet));
		BigInteger toReturn = new BigInteger(1, result);
		return toReturn;
	}
	
	public static byte transposeAlphabetOneVariablePartialState(int index, byte b) {
		/*if (numVars == 1){
			if (index == 0){
				return (byte) (((b & 0x02) << 2) | ((b & 0x02) << 1) | ((b & 0x01) << 1) | ((b & 0x01) << 0));
			} else if (index == 1){
				
			}
		} else if (numVars == 2){*/
			if (index == 0){
				byte firstHalf = (byte) (((b & 0x08) << 4) | ((b & 0x08) << 3) | ((b & 0x04) << 3) | ((b & 0x04) << 2));
				byte secondHalf = (byte) (((b & 0x02) << 2) | ((b & 0x02) << 1) | ((b & 0x01) << 1) | ((b & 0x01) << 0));
				return (byte) (firstHalf | secondHalf);
			} else if (index == 1) {
				return (byte) (((b & 0x0C) << 4) | ((b & 0x0C) << 2) | ((b & 0x03) << 2) | ((b & 0x03) << 0));
			} else if (index == 2){
				return (byte) (((b & 0x0F) << 4) | (b & 0x0F));
			}
	//	}
		System.out.println("ERROR! CODE SHOULD NOT BE REACHED!");
		return 0;
	}

	public static byte[] duplicateCase(byte[] origin){
		byte[] result = new byte[origin.length * 2];
		for(int k = 0; k < origin.length; k++){
			result[k] = origin[k];
			result[k+origin.length] = origin[k];
		}
		return result;
	}
	
	public static byte[] repeatEightCase(byte[] origin, int repetitionLength, int bitStringLength){
		byte[] result = new byte[origin.length * 2];
		int numberOfBytesRepeated = repetitionLength / 8;
		int numberOfRepetitions = bitStringLength / repetitionLength;
		for(int repetition = 0; repetition < numberOfRepetitions; repetition++){
			for(int byteTransfer = 0; byteTransfer < numberOfBytesRepeated; byteTransfer++){
				int originIndex = repetition * numberOfBytesRepeated + byteTransfer;
				int resultIndex = repetition * 2 * numberOfBytesRepeated + byteTransfer;
				int resultTwoIndex = repetition * 2 * numberOfBytesRepeated + numberOfBytesRepeated + byteTransfer;
				byte temp = origin[originIndex];
				result[resultIndex] = temp;
				result[resultTwoIndex] = temp;
			}
		}
		return result;
	}
	
	public static byte[] repeatFourCase(byte[] origin){
		byte[] result = new byte[origin.length * 2];
		int resultIndex = 0;
		for (byte b : origin){
			byte front = (byte) (b & 0xf0);
			front = (byte) (front | ((front & 0xff) >> 4));
			
			byte back = (byte) (b & 0x0f);
			back = (byte) (back | ((back & 0xff) << 4));

			result[resultIndex++] = front;
			result[resultIndex++] = back;
		}
		return result;
	}
	
	public static byte[] repeatTwoCase(byte[] origin){
		byte[] result = new byte[origin.length * 2];
		int resultIndex = 0;
		for (byte o : origin){
			byte a = (byte) (o & 0xc0);
			byte temp = (byte) ((a & 0xff) >>> 2);
			a = (byte) (a | temp);
			byte b = (byte) (o & 0x30);
			b = (byte) (((b & 0xff) >> 2) | ((b & 0xff) >> 4));
			result[resultIndex++] = (byte) (a | b);
			byte c = (byte) (o & 0x0c);
			c = (byte) (((c & 0xff) << 4) | ((c & 0xff) << 2));
			byte d = (byte) (o & 0x03);
			d = (byte) ((d & 0xff) | ((d & 0xff) << 2));
			result[resultIndex++] = (byte) (c | d);
		}
		return result;
	}
	
	public static byte[] repeatOneCase(byte[] origin){
		byte[] result = new byte[origin.length * 2];
		int resultIndex = 0;
		for (byte o : origin){
			byte a = (byte) (o & 0x80);
			a = (byte) (a | ((a  & 0xff) >> 1));
			byte b = (byte) (o & 0x40);
			b = (byte) (((b & 0xff) >> 1) | ((b & 0xff) >> 2));
			byte c = (byte) (o & 0x20);
			c = (byte) (((c & 0xff) >> 2) | ((c & 0xff) >> 3));
			byte d = (byte) (o & 0x10);
			d = (byte) (((d & 0xff) >> 3) | ((d & 0xff) >> 4));
			result[resultIndex++] = (byte) (a | b | c | d);
			byte e = (byte) (o & 0x08);
			e = (byte) (((e & 0xff) << 4) | ((e & 0xff) << 3));
			byte f = (byte) (o & 0x04);
			f = (byte) (((f & 0xff) << 3) | ((f & 0xff) << 2));
			byte g = (byte) (o & 0x02);
			g = (byte) (((g & 0xff) << 2) | ((g & 0xff) << 1));
			byte h = (byte) (o & 0x01);
			h = (byte) (((h << 1) & 0xff) | h);
			result[resultIndex++] = (byte) (e | f | g | h);
		}
		return result;
	}
	
	public SolvingPredicate collapseOnVariable(int var){
		int index = -1;
		for(int i = 0; i < alphabet.length; i++){
			if (alphabet[i] == var){
				index = i;
			}
		}
		if (index == -1){
			throw new RuntimeException("The Variable that was asked to be collapsed on was not present.");
		}
		int collapseLength = (int) Math.pow(2, index);
		byte[] origin = makeNBytesLong(state.toByteArray(), (int) (Math.pow(2,alphabet.length)/8));
		byte[] result = new byte[origin.length / 2];
		
		if (collapseLength % 8 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = COLLAPSE EIGHT OR MORE CASE ("+ collapseLength/8+")");
			result = collapseMultipleCase(origin, collapseLength / 8);
		} else if (collapseLength % 4 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = COLLAPSE FOUR CASE");
			result = collapseFourCase(origin);
		} else if (collapseLength % 2 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = COLLAPSE TWO CASE");
			result = collapseTwoCase(origin);
		} else if (collapseLength % 1 == 0){
			if (ThreeSATPredicate.PRINT) System.out.println(" = COLLAPSE ONE CASE");
			result = collapseOneCase(origin);
		}
		
		int[] newAlphabet = new int[alphabet.length - 1];
		for(int i = 0; i < newAlphabet.length; i++){
			newAlphabet[i] = alphabet[i + (i >= index ? 1 : 0)];
		}
		
		if (ThreeSATPredicate.PRINT) System.out.println("   - ORIGIN: " + printByteArray(origin, false) + " " + Arrays.toString(alphabet));
		if (ThreeSATPredicate.PRINT) System.out.println("   - RESULT: " + printByteArray(result, false) + " " + Arrays.toString(newAlphabet));
	
		BigInteger newState = new BigInteger(1, result);
		SolvingPredicate newSP = new SolvingPredicate(newAlphabet, newState);
		
		return newSP;
	}
	
	public static byte[] collapseMultipleCase(byte[] origin, int numBytes){
		byte[] result = new byte[origin.length/2];
		int resultIndex = 0;
		int skipAt = numBytes;
		for(int i = 0; i < origin.length; i++){
			if (i == skipAt){
				i += numBytes;
				skipAt += 2 * numBytes;
			}
			if (i < origin.length){
				byte a = origin[i];
				byte b = origin[i+numBytes];
				result[resultIndex++] = (byte) (a | b);
			}
		}
		return result;
	}
	
	public static byte[] collapseEightCase(byte[] origin){
		byte[] result = new byte[origin.length/2];
		int resultIndex = 0;
		for(int i = 0; i < origin.length; i+=2){
			byte a = origin[i];
			byte b = origin[i+1];
			result[resultIndex++] = (byte) (a | b);
		}
		return result;
	}
	
	public static byte[] collapseFourCase(byte[] origin){
		if (origin.length == 1){
			byte[] result = new byte[1];
			byte b = origin[0];
			result[0] = (byte) (b & 0x0f | ((b & 0xf0) >> 4));
			return result;
		} else {
			byte[] result = new byte[origin.length/2];
			int resultIndex = 0;
			for(int i = 0; i < origin.length; i+=2){
				byte a = origin[i];
				a = (byte) (a & 0xf0 | ((a & 0x0f) << 4));
				byte b = origin[i+1];
				b = (byte) (b & 0x0f | ((b & 0xf0) >> 4));
				result[resultIndex++] = (byte) (a | b);
			}
			return result;
		}
	}
	
	public static byte[] collapseTwoCase(byte[] origin){
		if (origin.length == 1){
			byte[] result = new byte[1];
			byte b = origin[0];
			byte e = (byte) (((b & 0xC0) >> 4) | ((b & 0x30) >> 2));
			byte f = (byte) (((b & 0x0C) >> 2) | (b & 0x03));
			result[0] = (byte) (e | f);
			return result;
		} else {
			byte[] result = new byte[origin.length/2];
			int resultIndex = 0;
			for(int i = 0; i < origin.length; i+=2){
				byte a = origin[i];
				byte b = origin[i+1];
	
				byte c = (byte) ((a & 0xC0) | ((a & 0x30) << 2));
				byte d = (byte) (((a & 0x0C) << 2) | ((a & 0x03) << 4));
				
				byte e = (byte) (((b & 0xC0) >> 4) | ((b & 0x30) >> 2));
				byte f = (byte) (((b & 0x0C) >> 2) | (b & 0x03));
				
				result[resultIndex++] = (byte) (c | d | e | f);
			}
			return result;
		}
	}
	
	public static byte[] collapseOneCase(byte[] origin){
		if (origin.length == 1){
			byte b = origin[0];
			byte g = (byte) ((b & 0x80) >> 4 | ((b & 0x40) >> 3));
			byte h = (byte) ((b & 0x20) >> 3 | ((b & 0x10) >> 2));
			byte i = (byte) ((b & 0x08) >> 2 | ((b & 0x04) >> 1));
			byte j = (byte) ((b & 0x02) >> 1 | ((b & 0x01) >> 0));
			byte[] result = new byte[1];
			result[0] = (byte) (g | h | i | j);
			return result;
		} else {
			byte[] result = new byte[origin.length/2];
			int resultIndex = 0;
			for(int p = 0; p < origin.length; p+=2){
				byte a = origin[p];
				byte b = origin[p+1];
	
				byte c = (byte) ((a & 0x80) << 0 | ((a & 0x40) << 1));
				byte d = (byte) ((a & 0x20) << 1 | ((a & 0x10) << 2));
				byte e = (byte) ((a & 0x08) << 2 | ((a & 0x04) << 3));
				byte f = (byte) ((a & 0x02) << 3 | ((a & 0x01) << 4));
				
				byte g = (byte) ((b & 0x80) >> 4 | ((b & 0x40) >> 3));
				byte h = (byte) ((b & 0x20) >> 3 | ((b & 0x10) >> 2));
				byte i = (byte) ((b & 0x08) >> 2 | ((b & 0x04) >> 1));
				byte j = (byte) ((b & 0x02) >> 1 | ((b & 0x01) >> 0));
				
				result[resultIndex++] = (byte) (c | d | e | f | g | h | i | j);
			}
			return result;
		}
	}
	
	private static int[] getTargetAlphabet(int[] alpha1, int[] alpha2){
		int[] targetAlphabet = new int[alpha1.length + alpha2.length];
		int i = 0;
		int j = 0;
		int k = 0;
		boolean done = false;
		while(!done){
			if (alpha1[i] > alpha2[j]){
				targetAlphabet[k++] = alpha2[j++];
			} else if (alpha1[i] < alpha2[j]){
				targetAlphabet[k++] = alpha1[i++];
			} else {
				targetAlphabet[k++] = alpha1[i++];
				j++;
			}
			if (i == alpha1.length){
				while (j < alpha2.length){
					targetAlphabet[k++] = alpha2[j++];
				}
				done = true;
			} else if (j == alpha2.length){
				while (i < alpha1.length){
					targetAlphabet[k++] = alpha1[i++];
				}
				done = true;
			}
		}
		int[] result = new int[k];
		for(i = 0; i < k; i++){
			result[i] = targetAlphabet[i];
		}
		return result;
	}

	public static BigInteger calculateState(boolean[] a){
		BigInteger state = BigInteger.ZERO;
		for (int i = 0; i < a.length; i++){
			BigInteger component = calculateSingleVariableState(i, a.length, a[i]);
			state = state.or(component);
		}
		return state;
	}
	
	private static BigInteger calculateSingleVariableState(int i, int v, boolean affirmative){
		BigInteger total = BigInteger.ZERO;
		for(int j = 0; j < v; j++){
			if (j == i){
				total = twoPowPow(i).subtract(BigInteger.ONE);
			} else {
				total = total.multiply(twoPowPow(j).add(BigInteger.ONE));
			}
		}
		if (!affirmative){
			total = twoPowPow(v).subtract(total.add(BigInteger.ONE));
		}
		return total;
	}
	
	public String toString(){
		String result = id + " - ALPHABET: " + Arrays.toString(alphabet) + "\n";
		result += id + " - STATE : " + printByteArray(state.toByteArray(), false);
		return result;
	}

	public boolean satisfiedBy(boolean[] vars){
		boolean[] importantVars = new boolean[alphabet.length];
		for (int i = 0; i < alphabet.length; i++){
			int var = alphabet[i];
			importantVars[i] = vars[var];
		}
		BigInteger specificState = twoPowPow(importantVars.length + 1).subtract(BigInteger.ONE);
		for (int i = 0; i < importantVars.length; i++){
			BigInteger temp = calculateSingleVariableState(i, importantVars.length, importantVars[i]);
			specificState = specificState.and(temp);
		}
		return ! specificState.and(state).equals(BigInteger.ZERO);
	}

	public static int twoPow(int i){
		return 1 << i;
	}
	
	public static BigInteger twoPowPow(int i){
		return BigInteger.valueOf(1L).shiftLeft(1 << i);
	}

	public static byte[] makeNBytesLong(byte[] byteArray, int n) {
		byte[] result = new byte[n];
		int k = n;
		for(int i = byteArray.length - 1; i >= 0 && k > 0; i--){
			k--;
			result[k] = byteArray[i];
		}
		return result;
	}

	private static String printByteArray(byte[] a, boolean print){
		String result = "[";
		for (byte b : a){
			String byteString = Integer.toBinaryString(b).replace(' ', '0');
			while (byteString.length() < 8){
				byteString = "0" + byteString;
			}
			byteString = byteString.substring(byteString.length() - 8);
			result = result + byteString + ", ";
		}
		if (result.length() > 2){
			result = result.substring(0, result.length() - 2) + "]";
		} else {
			result = "[]";
		}
		if (print){
			if (ThreeSATPredicate.PRINT) System.out.println(result);
		}
		return result;
	}

	public Set<Integer> getVariableSet() {
		if (variableSet == null){
			Set<Integer> set = new HashSet<Integer>();
			for (int c : alphabet){
				set.add(c);
			}
			variableSet = set;
		}
		return variableSet;
	}

	public BigInteger getState() {
		return state;
	}

	public int getId() {
		return id;
	}

	public int[] getAlphabet() {
		return alphabet;
	}
}




