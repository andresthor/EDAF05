package pairsOnAPlane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pairsOnAPlane {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: pairsOnAPlane <inputFile1> <inputFile2> ... <inputFileN>");
		}
		
		/* Try to create a set of problems from the input files */
		ArrayList<PairProblem> problems = readFiles(args);
		
//		int i = 0;
//		for (PairProblem p : problems) {
//			System.out.printf("%d: ", ++i);
//			System.out.printf(	"%s\nminDist = %f\n\n",
//									p.name.equals("") ? p.file : p.name,
//									p.solveNaiveDist());
//		}
		
		for (PairProblem p : problems) {
			System.out.printf(	"%s: %d %s\n",
									p.file,
									p.size(),
									fmt(p.solveDivideConquerDist()));
		}
	}
	
	public static String fmt(double d)
	{
		DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

		return df.format(d);
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
		Pattern numb 	= Pattern.compile("[+-]?(\\w+)\\s*([+-]?\\d+\\.?"
										+ "\\d*[eE]?[+-]?\\d+)\\s*([+-]?"
										+ "\\d+\\.?\\d*[eE]?[+-]?\\d+)");
		Pattern simp    = Pattern.compile("(\\w+)\\s*(\\d+)\\s*(\\d+)");
		
		Matcher matcher;
		
		boolean header = checkHeader(br);						// should we parse a header?
		while (true) {
			line = br.readLine();
			if (line == null) break;
			
			if (header) {
				if (line.equals("NODE_COORD_SECTION")) {// last line of headers has been hit
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
				boolean matched = false;
				
				matcher = numb.matcher(line);
				matched = matcher.find();
				if (!matched) {		// found match directly
					matcher = simp.matcher(line);
					matched = matcher.find();
				}
				
				if (matched) {		// still might be a 'close-pairs-*-in file
					double x = Double.parseDouble(matcher.group(2));
					double y = Double.parseDouble(matcher.group(3));
					p.addEntry(matcher.group(1), x, y);
				}
			}
		}
		//System.out.println(p);
		//System.out.println(p.print());
		return p;
	}
	
	private static boolean checkHeader(BufferedReader br) throws IOException {
		/* We only check the first 15 lines to see if we find
		 * a line that splits headers from data. If it's not there,
		 * then most likely this file has no header
		 */
		br.mark(256);
		for (int i = 0; i < 15; i++) {
			String line = br.readLine();
			if (line != null && line.equals("NODE_COORD_SECTION")) {
				br.reset();
				return true;
			}
		}
		br.reset();
		return false;
	}
}
