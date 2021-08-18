package IO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

    }

    public OutputStream getOut() {
        return out;
    }

    private byte binaryToDecimal(ArrayList<Byte> B){
        int value = 0;
        int index = 0;
        for(int i = B.size() - 1; i >-1 ; i--){
            value = value + (int)Math.pow(2,index) * B.get(i);
            index ++;
        }
        return (byte)value;
    }

    @Override
    public void write(byte[] b) throws IOException {
        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
        int index = 0;

        while (index < 12) {
            byteArrayList.add(b[index]);
            index++;
        }

        while (index < b.length) {
            int counter = 0;
            ArrayList<Byte> byteTemp = new ArrayList<Byte>();
            while (counter < 8 && index < b.length) {
                byteTemp.add(b[index]);
                index++;
                counter++;
            }
            byteArrayList.add(binaryToDecimal(byteTemp));
        }

        try {
            for (int i = 0; i < byteArrayList.size(); i++)
                out.write(byteArrayList.get(i));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
