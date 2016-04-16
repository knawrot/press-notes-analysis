package pl.edu.agh.notes.utility;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileSplitter {
	public static final String SPLIT_FILE_PREFIX = "split";
	private static final long FILE_SIZE_IN_KB = 20;

	public static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) 
			throws IOException {
	    byte[] buf = new byte[(int) numBytes];
	    int val = raf.read(buf);
	    if(val != -1) {
	        bw.write(buf);
	    }
	}
	
	public static void splitFile(String path) throws IOException {
		String directory = (path.indexOf("/") >= 0) ? path.substring(0, path.lastIndexOf("/"))
													: ".";
		RandomAccessFile raf = new RandomAccessFile(path, "r");
		
		long sourceSize = raf.length();
		long bytesPerSplit = FILE_SIZE_IN_KB*1024;
		long numSplits = (sourceSize/bytesPerSplit)+1;
		long remainingBytes = sourceSize % numSplits;
		
		int maxReadBufferSize = 8 * 1024;
		for (int destIx = 1; destIx <= numSplits; destIx++) {
			BufferedOutputStream bw = new BufferedOutputStream(
							new FileOutputStream(directory + "/" + SPLIT_FILE_PREFIX + "." + destIx));
			if(bytesPerSplit > maxReadBufferSize) {
				long numReads = bytesPerSplit/maxReadBufferSize;
				long numRemainingRead = bytesPerSplit % maxReadBufferSize;
				for(int i=0; i<numReads; i++) {
					readWrite(raf, bw, maxReadBufferSize);
				}
				if(numRemainingRead > 0) {
					readWrite(raf, bw, numRemainingRead);
				}
			} else {
				readWrite(raf, bw, bytesPerSplit);
			}
			bw.close();
		}
		if (remainingBytes > 0) {
			BufferedOutputStream bw = new BufferedOutputStream(
										new FileOutputStream(directory + "/"+ SPLIT_FILE_PREFIX 
															+ "." + (numSplits+1)));
			readWrite(raf, bw, remainingBytes);
			bw.close();
		}
		
		raf.close();
	}
}
