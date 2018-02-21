import java.io.*;
import java.util.*;

class SaveArray {

public static void main (String[] args) {

    Object x = new Object[] {new Thread(), null};
    try {
        FileOutputStream fos = new FileOutputStream("array.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject( x );

        oos.close();

    } catch (IOException ioException) {
        System.out.println(ioException);
    }

    System.out.println(x instanceof Serializable );
}

}
