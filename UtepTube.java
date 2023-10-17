import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;

class UtepTube {
  public static void main(String[] args) throws FileNotFoundException{
    Scanner scanner = new Scanner(System.in);
    String menu = "Welcome to UtepTube! Please select an option below to continue: \n\t1. List videos in corpus \n\t2. Add video to playlist \n\t3. View playlist \n\t4. Clear playlist \n\t5. Close UtepTube \n>";
    System.out.print(menu);
    int option = scanner.nextInt();
    int minutes = 0;
    int seconds = 0;
    int hours = 0;
    String playlist = ""; 
    int playlistNum = 0;
    File corpusFile = new File("/Users/bolad/Desktop/cs1/lab6/corpus.csv");

    if (option == 5) {
      System.out.println("Thank you for using UtepTube. Goodbye!");
    }

    while (option>5 || option<1) {
      System.out.print(menu);
      option = scanner.nextInt();
    }

    while (option>=1 && option<=4) {

      if (option==1) {
        Scanner file = new Scanner(corpusFile);
        String header = "+-----------------------------------------------------------------------------------------------+\n|                                        UtepTube corpus                                        |";
        String line = "+-------------+---------------------------------------------------+---------------------+-------+";

        System.out.println(header);
        System.out.println(line);
        for (int j=0; j<6; j++) {
          for(int i=0; i<3; i++){
            file.useDelimiter(",");
            System.out.print("| " + file.next() + " "); 
          }
        System.out.println("| " + file.next() + ":" + file.next() + " |");
        System.out.println(line);
        file.nextLine();
        }
      } 
      else if (option==2) {
        System.out.println("Type in the ID of the video you wish to add to your playlist.");
        String id = scanner.next();
        Scanner file2 = new Scanner(corpusFile); 
        int sk = 0;
        String minsPlay;
        String secPlay;
        boolean exists = false;
        boolean preroll = false;
        boolean midroll = false;
        boolean postroll = false;

        file2.useDelimiter(",|\\n");
        while (file2.hasNext()) {
          if (id.equals(file2.next())) {
            System.out.println("Excellent choice!");
            System.out.println(file2.next());
            String creator = file2.next();
            creator = creator.trim();
            System.out.println(creator);
            int mins = file2.nextInt();
            minutes += mins;
            int secs = file2.nextInt();
            seconds += secs;
            if (mins<10) {
              minsPlay = "0" + mins;
            } else {
              minsPlay = "" + mins;
            }
            if (secs<10) {
              secPlay = "0" + secs;
            } else {
              secPlay = "" + secs;
            }
            preroll = file2.nextBoolean();
            midroll = file2.nextBoolean();
            postroll = file2.nextBoolean();
            playlist = playlist + " " + id + " | " + minsPlay + ":" + secPlay;
            exists = true;
            playlistNum += 1;
          }     
          file2.nextLine();
        } 
        if (exists == false) {
          System.out.println("Video ID not found.");
        } else {
          if (preroll == true) {
            seconds += 30;
            playlist = playlist + "p";
          } else {
            playlist = playlist + "0";
          }
          if (midroll==true) {
            while (sk<=0) {
              System.out.println("This video includes a midroll ad. Would you like to skip it after 10 seconds?");
              String skip = scanner.next();   
              if (skip.equals("true")) {
                seconds += 10;
                System.out.println("Okay!");
                sk = 1;
                playlist = playlist + "s";
              } else if (skip.equals("false")) {
                minutes += 2;
                System.out.println("Okay!");
                sk = 1;
                playlist = playlist + "m";
              } 
            }
          } else {
            playlist = playlist + "0";
          }
          if (postroll == true) {
            seconds += 5;
            playlist = playlist + "t";
          } else {
            playlist = playlist + "0";
          }
        }
      } else if (option==3) {
          System.out.println("------------- YOUR PLAYLIST ------------");
          String ads = "no ads";
          int m = 1;
          int v = 20;
          int s = 21;
          int p = 22;
          int b = 23;

          while (seconds>=60) {
            minutes += 1;
            seconds -= 60;
          }
          while (minutes>=60) {
            hours +=1;
            minutes -= 60;
          }
          if (playlistNum>0){
            for (int i=1; i<=playlistNum; i++) {
              if (i>10) {
                System.out.print(i + ". ");
              } else {
                System.out.print(" " + i +". ");
              }
              if (playlist.substring(v, s).equals("p")) {
                ads = "+30s preroll";
              } else if (playlist.substring(v,s).equals("0")) {
                ads = "";
              }
              if (playlist.substring(s, p).equals("s")) {
                ads = ads + " +10s midroll";
              } else if (playlist.substring(s, p).equals("m")) {
                ads = ads + " +2m midroll";
              } 
              if (playlist.substring(p, b).equals("t")) {
                ads = ads + " +5s postroll";
              }
              if (playlist.substring(v,b).equals("000")) {
                ads = "no ads";
              }
              System.out.println("https://youtu.be/" + playlist.substring(m, v) + " ( " + ads + " )");
              m += 23;
              s += 23;
              v += 23;
              p += 23;
              b += 23;
            }
            System.out.print("Total play time: " + hours + ":");
            if (minutes<10) {
              System.out.print("0"+ minutes + ":");
            } else {
              System.out.print(minutes + ":");
            }
            if (seconds<10) {
              System.out.println("0"+ seconds);
            } else {
              System.out.println(seconds);
            }
          } else {
            System.out.println("Total play time: 0:00:00");

          }

      } 
      else if (option==4) {
        int d = 0;
        while (d<=0) {
          System.out.println("Are you sure you'd like to DELETE your playlist?");
          String delete = scanner.next();
          if (delete.equals("true")) {
            playlistNum = 0;
            playlist = "";
            d = 1;
          } else if (delete.equals("false")) {
            d = 1;
          }
        }
      } 

    System.out.print(menu);
    option = scanner.nextInt();

    if (option == 5) {
      System.out.println("Thank you for using UtepTube. Goodbye!");
    }

    while (option>5 || option<1) {
      System.out.print(menu);
      option = scanner.nextInt();
    }
  }
  }
}

