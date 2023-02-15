import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivB( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		int[] coinValues = new int[g.getNodeList().size()];
		for (int i = 0; i < g.getNodeList().size(); i++) {
			coinValues[i] = Integer.parseInt(g.getNodeList().get(i).getVal());
		}

		System.out.print("Coin Values");
		output.print("Coin Values");
		for (int i = 0; i < coinValues.length; i++) {
			System.out.print("\t" + coinValues[i]);
			output.print("\t" + coinValues[i]);
		}
		System.out.println("\tTotal coins\nChange Needed");
		output.println("\tTotal coins\nChange Needed");

		for (int i = 1; i <= 100; i++) {
			int[] coins = greedyChange(coinValues, i);
			int totalCoins = 0;
			for (int j = 0; j < coins.length; j++) {
				totalCoins += coins[j];
			}
			System.out.print("\t\t" + i + "\t");
			output.print("\t" + i + "\t");
			for (int j = 0; j < coins.length; j++) {
				System.out.print(coins[j] + "\t");
				output.print(coins[j] + "\t");
			}
			System.out.println("\t" + totalCoins);
			output.println("\t" + totalCoins);
		}
		for (int i = 1; i <= 100; i++) {
			int[] coins =  bottomUpDynamicChange(coinValues, i);
			int totalCoins = 0;
			for (int j = 0; j < coins.length; j++) {
				totalCoins += coins[j];
			}
			System.out.print("\t\t" + i + "\t");
			output.print("\t" + i + "\t");
			for (int j = 0; j < coins.length; j++) {
				System.out.print(coins[j] + "\t");
				output.print(coins[j] + "\t");
			}
			System.out.println("\t" + totalCoins);
			output.println("\t" + totalCoins);
		}
		output.close();
	}

	public int[] greedyChange(int[] coinValues, int changeValue) {
		int[] change = new int[coinValues.length];
		for (int i = 0; i < coinValues.length; i++) {
			while (changeValue >= coinValues[i]) {
				change[i]++;
				changeValue -= coinValues[i];
			}
		}
		return change;
	}
	public int[] bottomUpDynamicChange(int[] coinValues, int changeValue) {
		int[] change = new int[coinValues.length];
		int[] minCoins = new int[changeValue + 1];
		int[] coinTrack = new int[changeValue + 1];
		Arrays.fill(minCoins, Integer.MAX_VALUE);
		minCoins[0] = 0;
		for (int i = 1; i <= changeValue; i++) {
			for (int j = 0; j < coinValues.length; j++) {
				if (coinValues[j] <= i) {
					int sub_res = minCoins[i - coinValues[j]];
					if (sub_res != Integer.MAX_VALUE && sub_res + 1 < minCoins[i]) {
						minCoins[i] = sub_res + 1;
						coinTrack[i] = j;
					}
				}
			}
		}
		int start = changeValue;
		while (start != 0) {
			int j = coinTrack[start];
			change[j]++;
			start = start - coinValues[j];
		}
		return change;
	}
}


