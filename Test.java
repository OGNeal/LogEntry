import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;

/**
 * Write a description of class Test here.
 * 
 * @author GivenName FamilyName
 * @version 1.0 yyyy-mm-dd
 */
public class Test
{
   private static final String FILE_NAME = "logfile.text";

   /**
    * Write a description of method main here.
    *
    * @param argument not used
    */
   public static void main(String[] argument) throws IOException
   {
      LineNumberReader rln = new LineNumberReader(new FileReader(FILE_NAME));
      System.out.println(rln.getLineNumber());
   } // end of main(String[] argument)

} // end of class Test