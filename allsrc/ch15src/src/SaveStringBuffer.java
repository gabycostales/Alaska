import java.io.*;
import java.util.*;

class SaveStringBuffer {

public static void main (String[] args) {

    StringBuffer sb = new StringBuffer("Beam me up!");

    try {
        FileOutputStream fos = new FileOutputStream("sb.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(sb);

        //Set bag = new HashSet();
        //bag.add(new Object());

        //oos.writeObject(bag);

        oos.close();

    } catch (IOException ioException) {
        System.out.println(ioException);
    }
}

}
