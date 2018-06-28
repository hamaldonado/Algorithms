package pe.maldonado.algorithms.compress;

import java.io.*;
import pe.maldonado.algorithms.bitmanipulation.BitIO;

public class LZ77 {
	
	final static int SB_SIZE = 1024; // 10 bits
	final static int LAB_SIZE = 64;  // 6 bits
	
	final static int TOKEN_SIZE = 3;  // 24 bits 
	
	BitIO bitIO;
	
	private static class Token {
		int offset;		// 10 bits
		int length;		// 6 bits
		byte c;			// 8 bits
		
		Token() {
			offset = 0;
			length = 0;
			c = 0;
		}

	}
	
	public LZ77() {
		bitIO = new BitIO();
	}
	
	private Token longestMatch(byte[] a, int start, int end, int lab) {
		
		Token t = new Token();
		
		int i, j = lab;
		int s = lab - 1;
		
		t.offset = 0;
		t.length = 0;
		t.c = a[lab];

		while (s >= start) {
						
			// 1. From right to left, locate the 1st byte in SB that matches with the 1st byte in LAB. 
			//    If no match is found, return an empty token
			while (a[s] != a[j]) {
				
				if (s == start) {
					return t;
				}
				
				s--;
			}
			
			i = s;
			
			// 2. If a match is found, keep comparing the 2nd, 3rd,.. an so on bytes of SB and LAB until we find a mismatch  
			while (a[i] == a[j]) {
				
				if (j == end) {
					break;
				}
				
				i++; j++;
				
			}		
			
			// 3. Once a mismatch is found, keep track of the longest match found and repeat from step 1.
			if (j - lab > t.length) {
				t.offset = lab - s;
				t.length = j - lab;
				t.c = a[j];
			}
			
			s--;
			j = lab;
					
		}
		
		return t;
	}
	
	public int compressFile(String fileName) {
		
		File file = new File(fileName);
		FileInputStream inStream;
		
		byte[] buffer = new byte[8192];
		int bytesRead = 0, bytesWritten = 0;
		
		try {

			// open source file
            inStream = new FileInputStream(file);
            
            // open destination file
            if (bitIO.openFile(fileName + ".lz77", 1) != 0) {
            	inStream.close();
            	throw new Exception ("Cannot open destination file");
            }
            
            // perform compression
            while ((bytesRead = inStream.read(buffer)) > 0) {
            	bytesWritten += compress(buffer, bytesRead);
            }
            
            // close source and destination
            inStream.close();
            bitIO.close();
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
		
		return bytesWritten;
		
	}
	
	public int close() {
		
		return 0;
		
	}
	
	public int compress(byte[] a, int length) {
		
		// Pointers:
		//    start - points to the first byte of the sliding window (which is also the first byte of the Search Buffer)
		//    lab   - points to the first byte of the Look-Ahead Buffer
		//	  end   - points to the last byte of the sliding window (which is also the last byte of the Look-Ahead Buffer)
		
		// Initial State:
		//	  The Look-Ahead Buffer(LAB) is filled with the first 'LAB_SIZE' bytes: 'lab' points to position 0 while 'end' points to LAB_SIZE - 1
		//    Search Buffer(SB) is empty: there is nothing in between start and lab, so we make start = lab = 0
		  
		int start = 0;
		int lab = 0; 
		int end = (length < LAB_SIZE) ? length - 1 : LAB_SIZE - 1 ;
		
		int tokenCount = 0;
		
		while (lab < end) {
		
			// Get the next token
			Token t = longestMatch(a, start, end, lab);
			
			// Output Token (24 bits)
			bitIO.writeBits(t.offset, 10);
			bitIO.writeBits(t.length, 6);
			bitIO.writeBits(t.c, 8);
			
			//System.out.print("(" + t.offset + "," + t.length + "," + (char)t.c + ")");
			//System.out.println("(s:" + start + ", e:" + end + ", l:" + lab + ")");
			
			// Slide the window to the right (update 'start', 'end' and 'lab' values) by the length of the last match + 1 
			lab += t.length + 1; 
			
			if (lab > SB_SIZE) {
				start = lab - SB_SIZE;
			}
			
			end += t.length + 1;
			
			if (end >= length) {
				end = length - 1;
			}
			
			tokenCount++;
		
		}
		
		return tokenCount * TOKEN_SIZE;
	}
	
	public static void main(String[] args) {
		
		LZ77 lz77 = new LZ77();
		int bw = lz77.compressFile("d:\\dev\\test.html");
		
		System.out.println("\n#Bytes Written     : " + bw);
		
		
		
		//                    1         2         3         4         5
		//          012345678901234567890123456789012345678901234567890
		//String a = "en un juncal de junqueira juncos juntaba julian juntose juana a juntarlos y juntos juntaron juncos";
		//String a = "abababababababbaabbababababababbabbabbaaaaaaababbbbabbababababbabbbabbbababababababababababbabababbabab";
		//String a = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		//String a = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";
				
		//byte[] b = a.getBytes(Charset.forName("UTF-8"));
		//int inputBytes = a.length();
		
		/*int outputBytes = lz77.compress(b, inputBytes);
		double ratio = 100 * (1 - ((double)outputBytes / (double)inputBytes));
		
		System.out.println("\n#Input Bytes     : " + inputBytes);
		System.out.println("#Output Bytes    : " + outputBytes);
		System.out.println("Compression Ratio: " + ratio + "%");*/
		
	}

}
