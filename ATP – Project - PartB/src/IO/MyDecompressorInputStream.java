package IO;

import java.io.*;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    private String decimalToBinary(byte b){
        int unsignedByte = Byte.toUnsignedInt(b);
        return Integer.toString(unsignedByte, 2);
    }

    @Override
    public int read(byte[] b) throws IOException {
        ArrayList<Byte> input = new ArrayList<>();

        try {
            while (in.available() > 0)
                input.add((byte) in.read());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;
        while(index < 12) {
            b[index] = input.get(index);
            index++;
        }

        int bIndex = 12;
        while(index < input.size() - 1){
            String binary = decimalToBinary(input.get(index));
            for(int i = binary.length(); i < 8 ; i++)
                binary = "0" + binary;

            for(int i = 0; i < binary.length(); i++) {
                b[bIndex] = Byte.parseByte(String.valueOf(binary.charAt(i)));
                bIndex++;
            }
            index++;
        }

        // For the last one
        String binary = decimalToBinary(input.get(index));

        int size = (b.length - 12) % 8;
        if(size == 0)
            size = 8;

        for (int i = binary.length(); i < size; i++)
            binary = "0" + binary;

        for (int i = 0; i < binary.length(); i++) {
            b[bIndex] = Byte.parseByte(String.valueOf(binary.charAt(i)));
            bIndex++;
        }

        return 0;
    }


    /*
    @Override
    public int read(byte[] b) throws IOException {
        ArrayList<Byte> input = new ArrayList<>();

        try {
            while (in.available() > 0)
                input.add((byte) in.read());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;
        while(index < 12) {
            b[index] = input.get(index);
            index++;
        }

        int indexB = index;
        byte currentValue = 0;

        while(index < input.size()){
            int value = input.get(index);
            for(int i = 0; i < value; i++) {
                b[indexB] = currentValue;
                indexB++;
            }

            index++;

            if(currentValue == 0)
                currentValue = 1;
            else
                currentValue = 0;
        }

        return 0;
    }*/
}
