package pe.maldonado.algorithms.bitmanipulation;

import java.io.*;

public class BitIO {

    private final int BUF_SIZE = 16;

    private short buffer;  // 16-bit buffer
    private byte bufferSpace;

    private int openMode;   // 0 = read, 1 = write
    private File file;

    FileInputStream inStream;
    FileOutputStream outStream;

    public BitIO() {
        file = null;
        buffer = 0;
        bufferSpace = BUF_SIZE;
        openMode = 0;
    }

    public int getBitCount(int value) {

        return (int)(Math.log(value) / Math.log(2)) + 1;

    }

    private byte[] shortToByteArray(short n) {

        byte[] ret = new byte[2];

        // Store in big-endian order
        ret[0] = (byte)((n >>> 8) & 0b11111111);
        ret[1] = (byte)(n & 0b11111111);

        return ret;
    }

    private short byteArrayToShort(byte[] n) {

        short ret = 0;

        // Read in big-endian order
        ret = (short) ((n[0] << 8) | (n[1] & 0b11111111));
        
        return ret;
           
    }

    public int writeBits (int value) {

        return writeBits(value, getBitCount(value));

    }

    public int writeBits(int value, int bitCount) {

        if (file == null || openMode == 0) {
            return -1;
        }

        // The value fits into the remaining buffer?
        if (bufferSpace >= bitCount) {

            value <<= (bufferSpace - bitCount);
            buffer |= value;
            bufferSpace -= bitCount;

        }
        else {

            // 1. Put in the buffer just only the most significant bits that fit
            int overflow = bitCount - bufferSpace;

            buffer |= value >>> overflow;
            bufferSpace -= bitCount - overflow;

            // 2. Flush / reset buffer
            writeBuffer();

            // 3. Put the remaining bits into the empty buffer
            value <<= (bufferSpace - overflow);
            buffer |= value;
            bufferSpace -= overflow;
        }

        // if the buffer is full, flush / reset it
        if (bufferSpace == 0) {
            writeBuffer();
        }

        return 0;
    }

    private int writeBuffer() {

        try {
            // write buffer to file
            outStream.write(shortToByteArray(buffer));

            // empty buffer
            buffer = 0;
            bufferSpace = BUF_SIZE;

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return 0;
    }

    public int readBits(int count) {

        int ret, remain;
        short temp, mask;
        
    	if (file == null || openMode == 1) {
            return -1;
        }
    	
    	if (count <= 0) {
    		return 0;
    	}

    	// If the buffer is empty, fill it with more data
        if (bufferSpace == BUF_SIZE) {
            if (loadBuffer() == -1) {
            	return -1;
            }
        }
        
        // The requested number of bits can be read entirely from the remaining data in the buffer?
        if (count <= BUF_SIZE - bufferSpace) {
        
        	temp = (short) (buffer >>> (BUF_SIZE - count));
        	mask = (short) ((1 << count) - 1);
        	
        	ret = temp & mask;
            buffer <<= count;
            bufferSpace += count;
        	
        }
        else {
        	
        	// 1. Read all the remaining bits in the current buffer
        	temp = (short) (buffer >>> bufferSpace);
        	mask = (short) ((1 << (BUF_SIZE - bufferSpace)) - 1);
        	
        	ret = temp & mask;
        	
        	// 2. Calculate the number of requested bits that has not been read yet
        	remain = count - (BUF_SIZE - bufferSpace);   
        	
        	// 3. Refill the buffer
        	loadBuffer();
        	
        	// 4. Read the remaining requested bits
        	ret <<= remain;
        	ret |= (short) (buffer >>> (BUF_SIZE - remain));
        	
        	bufferSpace += remain;
        }

        return ret;
    }


    private int loadBuffer() {
        
    	byte[] b = new byte[2];
    	int n = 0;
    	
    	try {
    	
    		// read buffer from file
            n = inStream.read(b);
            
            if (n < 0) {
            	return -1;
            }
            
            /*System.out.println(n);
            System.out.println((short)b[0]);
            System.out.println((short)b[1]);*/
            
            // fill the buffer
            buffer = byteArrayToShort(b);
            bufferSpace = 0;

    	}
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return n;

    }

    public int openFile(String fileName, int mode) {

        try {

            file = new File(fileName);

            switch (mode) {

                case 0:
                    openMode = 0;
                    inStream = new FileInputStream(file);
                    break;

                case 1:
                    openMode = 1;
                    outStream = new FileOutputStream(file);
                    break;

                default:
                    throw new Exception("Invalid Open Mode");

            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return 0;
    }

    public int close() {

        try {
        	
        	if (openMode == 0) {
        		inStream.close(); 
        	}
        	else {
        		outStream.close();
        	}
        	
            file = null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return 0;
    }

    public static void main(String[] args) {
    	
        BitIO bitIO = new BitIO();
        
        
        /*int r = bitIO.openFile("D:\\Dev\\test.txt", 1);

        bitIO.writeBits(2, 4); // 4 bit
        bitIO.writeBits(1, 2); // 2 bit
        bitIO.writeBits(3, 2); // 2 bit
        bitIO.writeBits(5, 3); // 3 bit
        bitIO.writeBits(7, 3); // 3 bit
        bitIO.writeBits(1, 2); // 2 bit
        
        //00100111 10111101
        */
        
        int r = bitIO.openFile("D:\\Dev\\test.txt", 0);
        
        System.out.println(bitIO.readBits(4));
        System.out.println(bitIO.readBits(2));
        System.out.println(bitIO.readBits(2));
        System.out.println(bitIO.readBits(3));
        System.out.println(bitIO.readBits(3));
        System.out.println(bitIO.readBits(2));
        System.out.println(bitIO.readBits(1));
        
        
        //System.out.println(Short.toUnsignedInt(c));

        bitIO.close();


    }

}
