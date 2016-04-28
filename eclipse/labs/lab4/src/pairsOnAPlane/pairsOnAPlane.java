package pairsOnAPlane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pairsOnAPlane {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: pairsOnAPlane <inputFile1> <inputFile2> ... <inputFileN>");
		}
		
		/* Try to create a set of problems from the input files */
		ArrayList<PairProblem> problems = readFiles(args);
		
//		for (PairProblem p : problems) {
//			p.solveNaive();
//		}
	}

	
	private static ArrayList<PairProblem> readFiles(String[] fileNames) {
		ArrayList<PairProblem> problems = new ArrayList<PairProblem>();
		BufferedReader br;
		
		for (String file : fileNames) {
			/* Try to open the file */
			br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				System.out.println("File not found: ");
				System.out.printf("%s\n", file);
				System.exit(1);
			}
			
			/* Try to make a PairProblem from the file */
			try {
				PairProblem p = createProblem(br, file);
				problems.add(p);
			} catch (IOException e1) {
				System.out.printf("Failed to parse file into PairProblem: %s\n", file);
				e1.printStackTrace();
				System.exit(1);
			}			
					
			/* Try to close the file */
			try {
				br.close();
			} catch (IOException e) {
				System.out.printf("Error closing BufferedReader for file: \n%s\n", file);
				e.printStackTrace();
			}
		}
		
		return problems;
	}
	
	private static PairProblem createProblem(BufferedReader br, String fileName) throws IOException {
		PairProblem p = new PairProblem(fileName);
		String line;
		
		/* Information we want to extract */
		Pattern name	= Pattern.compile("NAME\\s*:\\s*([\\w\\s]+)");
		Pattern comm 	= Pattern.compile("COMMENT\\s*:\\s*([\\w\\s]+)");
		Pattern dims	= Pattern.compile("DIMENSION\\s*:\\s*([\\d+]+)");
		Pattern numb 	= Pattern.compile("[+-]?(\\d+)\\s*([+-]?\\d+\\.?\\d*[eE]?[+-]?\\d+)\\s*([+-]?\\d+\\.?\\d*[eE]?[+-]?\\d+)");

		
		Matcher matcher;
		
		boolean header = true;
		while (true) {
			line = br.readLine();
			if (line == null) break;
			
			if (header) {
				if (line.equals("NODE_COORD_SECTION")) {		// last line of headers has been hit
					header = false;
					continue;
				}
				
				/* Extract information from headers */
				matcher = name.matcher(line);
				if (matcher.find()) p.name = matcher.group(1);

				matcher = dims.matcher(line);
				if (matcher.find()) p.dim = Integer.parseInt(matcher.group(1));
				
				matcher = comm.matcher(line);
				if (matcher.find()) p.appendComment(matcher.group(1));
				
			} else {
				/* 
				 * We should be in the numbers part of the file.
				 * We expect lines in the format of:
				 * 
				 * <vertex> <x> <y>
				 * 
				 * where <vertex> is usually a number, but can be a string and
				 * <x,y> are numbers, which will be stored as doubles.
				 */
				matcher = numb.matcher(line);
				if (matcher.find()) {
					String tmp = matcher.group(0);
					double x = Double.parseDouble(matcher.group(2));
					double y = Double.parseDouble(matcher.group(3));
					p.addEntry(matcher.group(1), x, y);
				}
			}
			
			
		}
		System.out.println(p);
		System.out.println(p.print());
		return p;
	}
}
