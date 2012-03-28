package applogger;

import models.*;
import java.util.*;
import java.text.*;
import play.libs.*;
import java.io.*;

public class AppLogger {

    private String user;
    private String modul;

    public AppLogger(User user) {
      if(user != null) {
        this.user = user.parafa;
        this.modul = user.modul.nazev;
      }
    }

    public List<String> get(String filename) {
      List<String> lines = null;

      try {
        File file = new File("logs/" + filename + ".txt");
        lines = IO.readLines(file);
      }
      catch (Exception e) {
      }

      return lines;
    }

    public void add(String model, String action, Long id) {

      DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = dtf.format(new Date());
      DateFormat df = new SimpleDateFormat("yyyy-MM");
      String filename = df.format(new Date());

      try
      { //ulozeni do logu
        FileWriter out = new FileWriter("logs/" + filename + ".txt", true);
        BufferedWriter writer = new BufferedWriter(out);
        writer.write(time + ";" + modul + ";" + user + ";" + model + ";" + id.toString() + ";" + action + "\r\n");
        writer.close();

      }
      catch(Exception e) {
        
      }

    }
}